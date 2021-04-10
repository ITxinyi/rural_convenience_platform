package com.gy.rural_convenience_platform.controller;

import com.alipay.api.AlipayApiException;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.pagehelper.PageInfo;
import com.gy.rural_convenience_platform.config.CosConfig;
import com.gy.rural_convenience_platform.entity.Goods;
import com.gy.rural_convenience_platform.entity.User;
import com.gy.rural_convenience_platform.entity.UserServerOrder;
import com.gy.rural_convenience_platform.entity.Worker;
import com.gy.rural_convenience_platform.service.UserServerService;
import com.gy.rural_convenience_platform.service.WorkerService;
import com.gy.rural_convenience_platform.utils.CurrentUser;
import com.gy.rural_convenience_platform.utils.ResponseCode;
import com.gy.rural_convenience_platform.utils.UploadUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.net.URISyntaxException;
import java.util.HashMap;
import java.util.Map;

@RestController
@CrossOrigin
public class WorkerController {

    @Autowired
    private CurrentUser currentUser;
    @Resource
    private ObjectMapper objectMapper;
    @Autowired
    private CosConfig cosConfig;
    @Autowired
    private WorkerService workerService;
    @Autowired
    private UserServerService userServerService;

    /**
     * 跑腿注册
     * @param workerInfo
     * @param files
     * @return
     * @throws IOException
     * @throws URISyntaxException
     */
    @PostMapping("/workerRegister")
    public Map<String, Object> workerRegister(
            @RequestParam("workerInfo") String workerInfo,
            @RequestParam(value = "files",required = false) MultipartFile[] files) throws IOException, URISyntaxException {
//        将json转换成map
        Worker worker = objectMapper.readValue(workerInfo, Worker.class);
        String username = worker.getUsername();

        Map<String,Object> imgMap = null;
        if (files != null) {
            imgMap = new HashMap<>();
//        将文件上传至腾讯云cos，并获取访问地址
            for (int i = 0; i < files.length; i++) {
                String url = UploadUtil.uploadToOos(files[i], username, cosConfig);
                imgMap.put("img" + (i+1), url);
            }
        }
//        将数据存入数据库
        boolean result = workerService.workerRegister(worker,imgMap);
        return result ? ResponseCode.ok("注册成功") : ResponseCode.error("注册失败");
    }

    /*跑腿登录*/
    @PostMapping("/workerLogin")
    public Map<String, Object> workerLogin(@RequestParam("userInfo") String userInfo, HttpServletRequest request, HttpServletResponse response) throws IOException, AlipayApiException {
        Map dataMap = objectMapper.readValue(userInfo, Map.class);
        dataMap.put("sessionId", request.getSession().getId());
        return workerService.workerLogin(dataMap);
    }

    /**
     * 获取当前登录的用户
     * @param request
     * @return
     */
    @GetMapping("/currWorker")
    public Map<String, Object> currWorker(HttpServletRequest request) {

        String sessionId = request.getSession().getId();
        Worker worker = workerService.currWorker(sessionId);
        return (worker != null) ? ResponseCode.ok(worker) : ResponseCode.error("用户信息校验失败！");
    }

    /**
     * 查询所有未完成的服务订单
     * @param request
     * @return
     */
    @GetMapping("/getIncompleteList/{pageNum}/{pageSize}")
    public Map<String, Object> getIncompleteList(
            @PathVariable("pageNum") Integer pageNum,
            @PathVariable("pageSize") Integer pageSize,
            HttpServletRequest request) {

        Worker worker = currentUser.currUser(request);
        if (worker == null) return ResponseCode.error("用户未登录");

        PageInfo<UserServerOrder> pageInfo = workerService.getIncompleteList(pageNum,pageSize);
        return ResponseCode.ok(pageInfo);
    }

    /*根据服务订单号查询详细信息*/
    @GetMapping("/getServerDtl/{orderNum}")
    public Map<String, Object> getServerDtl(@PathVariable("orderNum") String orderNum,HttpServletRequest request){
        Worker worker = currentUser.currUser(request);
        if (worker == null) return ResponseCode.error("用户未登录");

        UserServerOrder serverOrderDtl = workerService.getServerDtl(orderNum);

        return (serverOrderDtl != null) ? ResponseCode.ok(serverOrderDtl) : ResponseCode.error("查询失败");

    }

    /*接单*/
    @GetMapping("/takeOrders/{orderNum}")
    public Map<String, Object> takeOrders(@PathVariable("orderNum") String orderNum,HttpServletRequest request){

        Worker worker = currentUser.currUser(request);
        if (worker == null) return ResponseCode.error("用户未登录");

        boolean result = workerService.takeOrders(orderNum,worker);

        return result ? ResponseCode.ok("接单成功") : ResponseCode.error("接单失败");
    }


    /*跑腿密码修改*/
    @PostMapping("/rePwd")
    public Map<String, Object> rePwd(@RequestParam("rePwdInfo") String rePwdInfo, HttpServletRequest request) throws IOException, AlipayApiException {

        Worker worker = currentUser.currUser(request);
        if (worker == null) return ResponseCode.error("用户未登录");

        Map<String,String> dataMap = objectMapper.readValue(rePwdInfo, Map.class);
        boolean result = workerService.rePwd(dataMap,worker);
        return result ? ResponseCode.ok("修改成功！") : ResponseCode.error("修改失败！");
    }

    /**
     * 押金充值
     */
    @PostMapping("/payDeposit")
    public void payDeposit(HttpServletRequest request, HttpServletResponse response) throws IOException, AlipayApiException {

        Worker worker = currentUser.currUser(request);
        if (worker == null) return ;

        workerService.payDeposit(worker, response);

    }

    /**
     * 查询已接单列表
     * @param pageNum
     * @param pageSize
     * @param request
     * @return
     */
    @GetMapping("/getCompleteList/{pageNum}/{pageSize}")
    public Map<String, Object> getCompleteList(
            @PathVariable("pageNum") Integer pageNum,
            @PathVariable("pageSize") Integer pageSize,
            HttpServletRequest request) {

        Worker worker = currentUser.currUser(request);
        if (worker == null) return ResponseCode.error("用户未登录");

        PageInfo<UserServerOrder> pageInfo = workerService.getCompleteList(pageNum,pageSize,worker);
        return ResponseCode.ok(pageInfo);
    }

    /*服务订单完成*/
    @GetMapping("/doDelivery/{id}")
    public Map<String, Object> doDelivery(
            @PathVariable("id") Integer id,
            HttpServletRequest request) {

        Worker worker = currentUser.currUser(request);
        if (worker == null) return ResponseCode.error("用户未登录");

        boolean result = workerService.doDelivery(id);
        return result ? ResponseCode.ok("送达成功") : ResponseCode.error("送达失败");
    }

}
