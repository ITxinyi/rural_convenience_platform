package com.gy.rural_convenience_platform.service;

import com.gy.rural_convenience_platform.entity.OrderGoods;
import com.gy.rural_convenience_platform.entity.User;
import com.gy.rural_convenience_platform.entity.UserGoodsOrder;
import com.gy.rural_convenience_platform.mapper.OrderGoodsMapper;
import com.gy.rural_convenience_platform.mapper.UserGoodsOrderMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class UserGoodsOrderService {
    @Autowired
    private UserGoodsOrderMapper userGoodsOrderMapper;
    @Autowired
    private OrderGoodsMapper orderGoodsMapper;

    public List<UserGoodsOrder> getUserGoodsOrders(User user) {
        List<UserGoodsOrder> list = userGoodsOrderMapper.getUserGoodsOrders(user.getId());
        return list;
    }

//    public boolean checkOrder(String out_trade_no) {
//        UserGoodsOrder userGoodsOrder = userGoodsOrderMapper.getOrderByOrderNum(out_trade_no);
//        if (userGoodsOrder.getPayState() == 1) return true;
//        return false;
//    }

    public void updatePayState(String out_trade_no) {
        UserGoodsOrder userGoodsOrder = userGoodsOrderMapper.getOrderByOrderNum(out_trade_no);
        if (userGoodsOrder.getPayState() != 1){
            userGoodsOrder.setPayState(1);
            userGoodsOrderMapper.updateByPrimaryKey(userGoodsOrder);
        }
    }

    public UserGoodsOrder getOrderDetail(Integer id) {
        Map<String, Object> map = new HashMap<>();
        map.put("id", id);
        return userGoodsOrderMapper.getOrderDetail(map);
    }

    public boolean delOrder(Integer orderId) {
        Map<String, Object> map = new HashMap<>();
        map.put("id", orderId);
        UserGoodsOrder orderDetail = userGoodsOrderMapper.getOrderDetail(map);
        List<OrderGoods> orderGoods = orderDetail.getOrderGoods();
        for (OrderGoods orderGood : orderGoods) {
            orderGoodsMapper.deleteByPrimaryKey(orderGood.getId());
        }
        int result = userGoodsOrderMapper.deleteByPrimaryKey(orderId);
        if (result > 0) return true;
        return false;
    }

    /*根据订单号查询订单信息*/
    public UserGoodsOrder getOrderByNum(String out_trade_no) {
        Map<String, Object> map = new HashMap<>();
        map.put("orderNum", out_trade_no);
        return userGoodsOrderMapper.getOrderDetail(map);
    }
}
