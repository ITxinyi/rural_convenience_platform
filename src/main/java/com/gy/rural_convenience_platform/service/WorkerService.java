package com.gy.rural_convenience_platform.service;

import com.alibaba.druid.sql.ast.statement.SQLForeignKeyImpl;
import com.alipay.api.AlipayApiException;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.gy.rural_convenience_platform.entity.OrderInfo;
import com.gy.rural_convenience_platform.entity.UserServerOrder;
import com.gy.rural_convenience_platform.entity.Worker;
import com.gy.rural_convenience_platform.mapper.UserServerOrderMapper;
import com.gy.rural_convenience_platform.mapper.WorkerMapper;
import com.gy.rural_convenience_platform.utils.AlipayUtils;
import com.gy.rural_convenience_platform.utils.OrderNumberUtil;
import com.gy.rural_convenience_platform.utils.RedisUtil;
import com.gy.rural_convenience_platform.utils.ResponseCode;
import com.sun.org.apache.regexp.internal.RE;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tk.mybatis.mapper.util.StringUtil;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.math.BigDecimal;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class WorkerService {

    @Autowired
    private WorkerMapper workerMapper;
    @Autowired
    private RedisUtil re;
    @Autowired
    private UserServerOrderMapper userServerOrderMapper;
    @Autowired
    private AlipayUtils alipayUtils;
    @Autowired
    private OrderNumberUtil orderNumberUtil;

    /**
     * 跑腿注册
     * @param worker
     * @param imgMap
     * @return
     */
    public boolean workerRegister(Worker worker, Map<String, Object> imgMap) {
        /*将信息插入worker表*/
        workerMapper.insertWorker(worker);
        /*插入成功，获取插入的主键*/
        Integer id = worker.getId();
        /*根据主键，将证件图片的路径插入数据库*/
        imgMap.put("id", id);
        Integer result = workerMapper.insertWorkerImg(imgMap);
        /*返回插入结果*/
        return (id != null && result > 0) ? true : false;
    }

    /**
     * 跑腿登录
     *
     * @param dataMap
     * @return
     */
    public Map<String, Object> workerLogin(Map dataMap) {

        if (dataMap.get("username") == null || dataMap.get("password") == null) return null;

        Worker[] workers = workerMapper.queryWorker(dataMap);
        if(workers.length == 1 && workers != null){
            Worker worker = workers[0];

            //审核未通过
            if (worker.getState() != 1){
                return ResponseCode.error("请等待管理员审核");
            }
            /*登录成功，将用户信息存入redis*/
            re.set(dataMap.get("sessionId")+"", worker, 60 * 30);
            return ResponseCode.ok(worker);
        }
        return ResponseCode.error("登录失败");
    }

    public Worker currWorker(String sessionId) {
        Worker worker = (Worker) re.get(sessionId);
        return worker;
    }

    /**
     * 查询所有未完成的服务订单
     *
     * @param pageNum
     * @param pageSize
     * @return
     */
    public PageInfo<UserServerOrder> getIncompleteList(Integer pageNum, Integer pageSize) {

        Map<String, Object> map = new HashMap<>();
        /*未删除*/
        map.put("isDel", 0);
        /*未接单*/
        map.put("orderState", 0);
        /*已支付*/
        map.put("payState", 1);

        PageHelper.startPage(pageNum, pageSize);

        List<UserServerOrder> incompleteList = userServerOrderMapper.getServerOrderDtl(map);
        PageInfo<UserServerOrder> pageInfo = new PageInfo<>(incompleteList);
        return pageInfo;
    }

    /**
     * 查看订单详情
     * @param orderNum
     * @return
     */
    public UserServerOrder getServerDtl(String orderNum) {
        Map<String, Object> map = new HashMap<>();
        if (!StringUtil.isEmpty(orderNum)) {
            map.put("orderNum",orderNum);
        }

        List<UserServerOrder> serverOrders = userServerOrderMapper.getServerOrderDtl(map);
        return serverOrders.get(0);
    }

    /**
     * 接单
     *
     * @param orderNum
     * @param _worker
     * @return
     */
    public boolean takeOrders(String orderNum, Worker _worker) {

        Map<String, Object> tempMap = new HashMap<>();
        tempMap.put("id", _worker.getId());

        Worker worker = workerMapper.getWorkerById(tempMap);
        /*判断是否充值押金*/
        if (Integer.valueOf(worker.getDeposit()) >= 500){
            UserServerOrder serverDtl = this.getServerDtl(orderNum);
            Map<String, Object> map = new HashMap<>();
            map.put("workerId", worker.getId());
            map.put("serverId", serverDtl.getId());
            /*将数据插入接单表*/
            Integer result = workerMapper.takeOrders(map);
            /*修改服务订单表状态*/
            if (result > 0) {
                map.put("orderState", 1);
                Integer result2 =userServerOrderMapper.updServerOrder(map);
                return (result2 > 0) ? true : false;
            }
        }
        return false;
    }

    /**
     * 修改密码
     *
     * @param dataMap
     * @param worker
     * @return
     */
    public boolean rePwd(Map<String, String> dataMap, Worker worker) {

        String password = worker.getPassword();
        String oldPwd = dataMap.get("oldPwd");
        String newPwd = dataMap.get("newPwd");
        String reNewPwd = dataMap.get("reNewPwd");

        if (!StringUtil.isEmpty(oldPwd) && !StringUtil.isEmpty(newPwd) && !StringUtil.isEmpty(reNewPwd)) {
            if (!password.equals(oldPwd) || !newPwd.equals(reNewPwd)) return false;
        }
        dataMap.put("workerId", worker.getId()+"");
        Integer result = workerMapper.updWorker(dataMap);
        return (result > 0) ? true : false;
    }

    /**
     * 押金充值
     * @param _worker
     * @param response
     */
    public void payDeposit(Worker _worker, HttpServletResponse response) throws IOException, AlipayApiException {

        Map<String, Object> map = new HashMap<>();
        map.put("id", _worker.getId());
        Worker[] workers = workerMapper.queryWorker(map);
        Worker worker = workers[0];

        OrderInfo orderInfo = new OrderInfo();
        orderInfo.setTotalAmount("500");
        orderInfo.setSubject("跑腿押金");
        orderInfo.setFlag("1");

        boolean f = !StringUtil.isEmpty(worker.getDepositOrdNum());
        boolean f1 = !StringUtil.isEmpty(worker.getDepositOrdNum());

        /*押金大于500，订单号不为空*/
        if (Integer.valueOf(worker.getDeposit()) >= 500 && (f && f1)) {
            return;
        }
        /*押金等于零，订单号不为空*/
        if ("0".equals(worker.getDeposit()) && (f && f1)) {
            orderInfo.setOutTradeNo(worker.getDepositOrdNum());
            alipayUtils.pay(orderInfo,response);
        }
        if ("0".equals(worker.getDeposit()) && (!f || !f1)) {
            /*生成订单号*/
            String orderNumber = orderNumberUtil.proOrderNumber();
            /*更新数据库*/
            HashMap<String, String> temMap = new HashMap<>();
            temMap.put("workerId", worker.getId()+"");
            temMap.put("depositOrdNum", orderNumber);
            workerMapper.updWorker(temMap);
            /*调用支付*/
            orderInfo.setOutTradeNo(orderNumber);
            alipayUtils.pay(orderInfo,response);
        }
    }

    public Worker queryWorker(Map map){
        Worker[] workers = workerMapper.queryWorker(map);
        return workers[0];
    }

    public void updWorker(Map map) {
        workerMapper.updWorker(map);
    }

    /*查询跑腿接单列表*/
    public PageInfo<UserServerOrder> getCompleteList(Integer pageNum, Integer pageSize, Worker worker) {

        Map<String, Object> map = new HashMap<>();
        map.put("workerId", worker.getId());

        PageHelper.startPage(pageNum, pageSize);

        List<UserServerOrder> completeList = userServerOrderMapper.getCompleteList(map);
        PageInfo<UserServerOrder> pageInfo = new PageInfo<>(completeList);
        return pageInfo;

    }

    /**
     * 修改订单状态
     * @param id 服务订单id
     * @param _worker
     * @return
     */
    public boolean doDelivery(Integer id, Worker _worker) {
        Map<String, Object> map = new HashMap<>();
        map.put("id", id);

        /*根据id查询服务订单*/
        List<UserServerOrder> orderDtl = userServerOrderMapper.getServerOrderDtl(map);
        if (orderDtl != null) {
            /*修改订单状态*/
            map.put("serverId", id);
            map.put("orderState", 2);
            Integer result = userServerOrderMapper.updServerOrder(map);
        }
        /*修改跑腿收入*/
        String workerId = _worker.getId().toString();
        map.put("id", workerId);
        /*查询跑腿信息*/
        Worker worker = workerMapper.getWorkerById(map);
        /*获取订单信息*/
        UserServerOrder serverOrder = orderDtl.get(0);
        /*计算收入*/
        String income = worker.getIncome();
        String price = serverOrder.getPrice()+"";
        BigDecimal bd1 = new BigDecimal(income);
        BigDecimal bd2 = new BigDecimal(price);
        income = bd1.add(bd2).toString();
        /*执行修改*/
        Map<String, String> tempMap = new HashMap<>();
        tempMap.put("workerId", workerId);
        tempMap.put("income", income);
        Integer result = workerMapper.updWorker(tempMap);
        /*返回结果*/
        return (result > 0) ? true : false;
    }
}
