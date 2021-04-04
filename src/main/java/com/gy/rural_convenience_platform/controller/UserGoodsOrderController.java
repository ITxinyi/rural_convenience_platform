package com.gy.rural_convenience_platform.controller;

import com.gy.rural_convenience_platform.entity.User;
import com.gy.rural_convenience_platform.entity.UserGoodsOrder;
import com.gy.rural_convenience_platform.service.UserGoodsOrderService;
import com.gy.rural_convenience_platform.utils.CurrentUser;
import com.gy.rural_convenience_platform.utils.ResponseCode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Map;

@RestController
@CrossOrigin
public class UserGoodsOrderController {

    @Autowired
    private CurrentUser currentUser;
    @Autowired
    private UserGoodsOrderService userGoodsOrderService;

    @RequestMapping("/goodsOrders")
    public Map<String,Object> getUserGoodsOrders(HttpServletRequest request){
        User user = currentUser.currentUser(request);
        if (user != null) {
            List<UserGoodsOrder> list = userGoodsOrderService.getUserGoodsOrders(user);
            return ResponseCode.ok(list);
        }
        return ResponseCode.error("获取订单列表失败");
    }

    @RequestMapping("/orderDetail")
    public Map<String,Object> getOrderDetail(Integer id,HttpServletRequest request){
        User user = currentUser.currentUser(request);
        if (user != null) {
            UserGoodsOrder userGoodsOrder = userGoodsOrderService.getOrderDetail(id);
            return ResponseCode.ok(userGoodsOrder);
        }
        return ResponseCode.error("获取订单列表失败");
    }

    @RequestMapping("/delOrder")
    public Map<String,Object> delOrder(Integer orderId,HttpServletRequest request){
        User user = currentUser.currentUser(request);
        if (user != null) {
            boolean result = userGoodsOrderService.delOrder(orderId);
            return ResponseCode.ok(result);
        }
        return ResponseCode.error("订单删除失败");
    }

}
