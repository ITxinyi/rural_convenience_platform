package com.gy.rural_convenience_platform.mapper;

import com.gy.rural_convenience_platform.entity.GoodsEvaluate;
import org.springframework.stereotype.Repository;
import tk.mybatis.MyMapper;

import java.util.List;
import java.util.Map;

@Repository
public interface GoodsEvaluateMapper extends MyMapper<GoodsEvaluate> {
    GoodsEvaluate getEvaByUserId(Map<String,Object> map);

    List<GoodsEvaluate> getComments(Integer goodsId);
}