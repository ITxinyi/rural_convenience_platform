package com.gy.rural_convenience_platform.service;

import com.gy.rural_convenience_platform.entity.GoodsEvaluate;
import com.gy.rural_convenience_platform.entity.User;
import com.gy.rural_convenience_platform.entity.UserGoodsOrder;
import com.gy.rural_convenience_platform.mapper.GoodsEvaluateMapper;
import com.gy.rural_convenience_platform.mapper.UserGoodsOrderMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class EvaluateService {

    @Autowired
    private GoodsEvaluateMapper goodsEvaluateMapper;
    @Autowired
    private UserGoodsOrderMapper userGoodsOrderMapper;

    public boolean subComment(User user, Integer goodsId, String userComment) {
        Map<String, Object> map = new HashMap<>();
        map.put("userId", user.getId());
        map.put("goodsId", goodsId);
        UserGoodsOrder userGoodsOrder = userGoodsOrderMapper.getOrderByUserIdAndGoodsId(map);
        //用户未购买该商品，将不能发布评价
        if (userGoodsOrder == null) return false;
        GoodsEvaluate evaluate = goodsEvaluateMapper.getEvaByUserId(map);
        if (evaluate == null) {
            GoodsEvaluate goodsEvaluate = new GoodsEvaluate();
            goodsEvaluate.setGoodsId(goodsId);
            goodsEvaluate.setUserId(user.getId());
            goodsEvaluate.setContent(userComment);
            goodsEvaluate.setEvaluateDate(new SimpleDateFormat("yyyy-MM-dd").format(new Date()));
            int result = goodsEvaluateMapper.insert(goodsEvaluate);
            if(result > 0) return true;
        }
        return false;
    }

    public List<GoodsEvaluate> getComments(Integer goodsId) {
        List<GoodsEvaluate> list = goodsEvaluateMapper.getComments(goodsId);
        return list;
    }
}
