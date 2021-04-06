package com.gy.rural_convenience_platform.controller;

import com.alipay.api.AlipayApiException;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.gy.rural_convenience_platform.entity.User;
import com.gy.rural_convenience_platform.entity.UserServerOrder;
import com.gy.rural_convenience_platform.service.UserServerService;
import com.gy.rural_convenience_platform.utils.CurrentUser;
import com.gy.rural_convenience_platform.utils.ResponseCode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Map;

@RestController
@CrossOrigin
public class UserServerController {

    @Resource
    private ObjectMapper objectMapper;
    @Autowired
    private UserServerService userServerService;
    @Autowired
    private CurrentUser currentUser;

    /*保存用户服务订单*/
    @PostMapping("/subServerOrder")
    public Map<String, Object> subServerOrder(@RequestParam Map<String,Object> map, HttpServletRequest request, HttpServletResponse response) throws IOException, AlipayApiException {
        User user = currentUser.currentUser(request);
        if(user == null) return ResponseCode.error("用户未登录");
        String dataJson = map.get("dataJson").toString();
        Map dataMap = objectMapper.readValue(dataJson, Map.class);
        String orderNum = userServerService.subServerOrder(dataMap,user,response);
        return ResponseCode.ok(orderNum);
    }

//    @GetMapping("/subServerOrder")
//    public void subServerOrder(@RequestParam("jsonStr") String jsonStr, HttpServletRequest request, HttpServletResponse response) throws IOException, AlipayApiException {
//        User user = currentUser.currentUser(request);
//        if(user == null) return;
//        Map dataMap = objectMapper.readValue(jsonStr, Map.class);
//        userServerService.subServerOrder(dataMap,user,response);
//    }

    /*根据服务订单号查询详细信息*/
    @GetMapping("/getServerOrderDtl/{orderNum}")
    public Map<String, Object> getServerOrderDtl(@PathVariable("orderNum") String orderNum,HttpServletRequest request){
        User user = currentUser.currentUser(request);
        if(user == null) return ResponseCode.error("用户未登录");
        UserServerOrder[] serverOrder = userServerService.getServerOrderDtl(orderNum,user);
        return ResponseCode.ok(serverOrder);
    }

    /*支付服务订单*/
    @GetMapping("/toServerPay/{orderNum}")
    public void toServerPay(@PathVariable("orderNum") String orderNum,HttpServletRequest request,HttpServletResponse response) throws IOException, AlipayApiException {
        User user = currentUser.currentUser(request);
        if(user == null) return;
        userServerService.toServerPay(orderNum,user,response);
    }

    @GetMapping("/getServerOrders")
    public Map<String, Object> getServerOrders(HttpServletRequest request){
        User user = currentUser.currentUser(request);
        if(user == null) return ResponseCode.error("用户未登录");
        UserServerOrder[] serverOrders = userServerService.getServerOrderDtl(null, user);
        return ResponseCode.ok(serverOrders);
    }

}
