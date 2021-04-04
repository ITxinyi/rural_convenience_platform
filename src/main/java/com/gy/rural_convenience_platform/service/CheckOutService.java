package com.gy.rural_convenience_platform.service;

import com.alipay.api.AlipayApiException;
import com.gy.rural_convenience_platform.entity.*;
import com.gy.rural_convenience_platform.mapper.CartMapper;
import com.gy.rural_convenience_platform.mapper.OrderGoodsMapper;
import com.gy.rural_convenience_platform.mapper.UserGoodsOrderMapper;
import com.gy.rural_convenience_platform.utils.AlipayUtils;
import com.gy.rural_convenience_platform.utils.DateConverter;
import com.gy.rural_convenience_platform.utils.OrderNumberUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

@Service
public class CheckOutService {

    @Autowired
    private CartMapper cartMapper;
    @Autowired
    private OrderNumberUtil onu;
    @Autowired
    private UserGoodsOrderMapper userGoodsOrderMapper;
    @Autowired
    private OrderGoodsMapper orderGoodsMapper;
    @Autowired
    private AlipayUtils alipayUtils;
    @Autowired
    private DateConverter dateConverter;

    public List<Cart> getCheckOutGoods(String ids) {
        String[] cartIds = ids.split(",");
        List<Cart> list = cartMapper.getCheckOutGoods(cartIds);
        return list;
    }

    public boolean doPay(User user, String ids, Integer addrId, HttpServletResponse response) throws IOException, AlipayApiException {
        if ((ids != null && ids != "") && addrId != null) {
            String orderNum = onu.proOrderNumber();
            String[] cartIds = ids.split(",");
            List<Cart> list = cartMapper.getCheckOutGoods(cartIds);
            //计算付款金额和商品件数
            double payPrice = 0;
            int number = 0;
            for (Cart cart : list) {
                Goods goods = cart.getGoods();
                double curPrice = goods.getPrice() * cart.getQuantity();
                payPrice += curPrice;
                number += 1;
            }
            String orderDate = dateConverter.getDate();
            //保存订单
            UserGoodsOrder userGoodsOrder = new UserGoodsOrder(addrId, user.getId(), orderNum, payPrice, number, 0, 0,orderDate);
            int result = userGoodsOrderMapper.insert(userGoodsOrder);
            String goodsTitle = list.get(0).getGoods().getGoodsTitle() + "...";
            OrderInfo orderInfo = new OrderInfo(orderNum,payPrice+"",goodsTitle);
            //保存订单下的商品
            if (result > 0) {
                for (Cart cart : list) {
                    OrderGoods orderGoods = new OrderGoods();
                    orderGoods.setUgoId(userGoodsOrder.getId());
                    orderGoods.setGoodsId(cart.getGoodsId());
                    orderGoods.setQuantity(cart.getQuantity());
                    result = orderGoodsMapper.insert(orderGoods);
                    cartMapper.deleteByPrimaryKey(cart.getId());
                }
                if (result > 0) {
                    alipayUtils.pay(orderInfo,response);
                }
            }
        }
        return false;
    }

    public boolean rePay(Integer orderId, HttpServletResponse response) throws IOException, AlipayApiException {
        UserGoodsOrder userGoodsOrder = userGoodsOrderMapper.selectByPrimaryKey(orderId);
        if (userGoodsOrder != null) {
            if (userGoodsOrder.getPayState() != 1) {
                Map<String, Object> map = new HashMap<>();
                map.put("id", orderId);
                UserGoodsOrder orderDetail = userGoodsOrderMapper.getOrderDetail(map);
                String goodsTile = orderDetail.getOrderGoods().get(0).getGoods().getGoodsTitle();
                String orderNum = userGoodsOrder.getOrderNum();
                String price = userGoodsOrder.getPayPrice() + "";
                OrderInfo orderInfo = new OrderInfo(orderNum, price, goodsTile);
                alipayUtils.pay(orderInfo,response);
            }
        }
        return false;
    }
}
