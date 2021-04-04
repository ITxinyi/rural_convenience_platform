package com.gy.rural_convenience_platform.service;

import com.alipay.api.AlipayApiException;
import com.gy.rural_convenience_platform.entity.OrderInfo;
import com.gy.rural_convenience_platform.entity.User;
import com.gy.rural_convenience_platform.entity.UserServerOrder;
import com.gy.rural_convenience_platform.mapper.UserServerOrderMapper;
import com.gy.rural_convenience_platform.utils.AlipayUtils;
import com.gy.rural_convenience_platform.utils.OrderNumberUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tk.mybatis.mapper.util.StringUtil;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

@Service
public class UserServerService {

    @Autowired
    private OrderNumberUtil orderNumberUtil;
    @Autowired
    private UserServerOrderMapper userServerOrderMapper;
    @Autowired
    private AlipayUtils alipayUtils;

    public String subServerOrder(Map dataMap, User user, HttpServletResponse response) {

        //生成订单号
        String orderNumber = orderNumberUtil.proOrderNumber();
        dataMap.put("orderNumber",orderNumber);
        dataMap.put("userId", user.getId());

        //将订单存入数据库
        userServerOrderMapper.saveServerOrder(dataMap);

        //调用支付宝支付
//        String price = (String) dataMap.get("price");
//        String goods = (String) dataMap.get("goods");
//        OrderInfo orderInfo = new OrderInfo(orderNumber,price,goods);
//        alipayUtils.pay(orderInfo,response);

        return orderNumber;
    }

    /*查询服务订单详情信息*/
    public UserServerOrder[] getServerOrderDtl(String orderNum, User user) {
        Map<String, Object> map = new HashMap<>();
        if (!StringUtil.isEmpty(orderNum)) {
            map.put("orderNum",orderNum);
        }
        if (user != null){
            map.put("userId", user.getId());
        }
        UserServerOrder[] serverOrder = userServerOrderMapper.getServerOrderDtl(map);
        return serverOrder;
    }

    /*支付服务订单*/
    public void toServerPay(String orderNum, User user, HttpServletResponse response) throws IOException, AlipayApiException {
        Map<String, Object> map = new HashMap<>();
        map.put("orderNum",orderNum);
        map.put("userId", user.getId());
        /*获取订单信息*/
        UserServerOrder[] orderDtl = userServerOrderMapper.getServerOrderDtl(map);
        UserServerOrder serverOrder = orderDtl[0];
        /*订单还未支付*/
        if(serverOrder.getPayState() == 0){
            String price = serverOrder.getPrice() + "";
            String goods = serverOrder.getGoods();
            OrderInfo orderInfo = new OrderInfo(serverOrder.getOrderNum(),price,goods);
            alipayUtils.pay(orderInfo,response);
        }else{
            /*订单已经付款，跳转至订单列表页面*/
        }
    }

    /*更新服务订单支付状态*/
    public void updServerOrderState(String orderNum) {
        userServerOrderMapper.updServerOrderState(orderNum);
    }
}
