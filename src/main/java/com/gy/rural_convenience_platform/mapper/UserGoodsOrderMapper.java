package com.gy.rural_convenience_platform.mapper;

import com.gy.rural_convenience_platform.entity.UserGoodsOrder;
import org.springframework.stereotype.Repository;
import tk.mybatis.MyMapper;

import java.util.List;
import java.util.Map;

@Repository
public interface UserGoodsOrderMapper extends MyMapper<UserGoodsOrder> {
    UserGoodsOrder getOrderByUserIdAndGoodsId(Map<String, Object> map);

    List<UserGoodsOrder> getUserGoodsOrders(Integer userId);

    UserGoodsOrder getOrderByOrderNum(String out_trade_no);

    UserGoodsOrder getOrderDetail(Map<String, Object> map);

    int delOrder(Integer orderId);
}