package com.gy.rural_convenience_platform.mapper;

import com.gy.rural_convenience_platform.entity.Cart;
import com.gy.rural_convenience_platform.entity.Goods;
import org.springframework.stereotype.Repository;
import tk.mybatis.MyMapper;

import java.util.List;
import java.util.Map;

@Repository
public interface GoodsMapper extends MyMapper<Goods> {
    List<Goods> goodsPage(Map<String, Object> map);

    Goods goodsById(Integer id);

    List<Goods> goodsRandByNum(Integer num);

    Integer saveGoods(Map map);

    Integer updGoodsImg(Map imgMap);

    Integer addGoods(Goods goods);

    Integer addGoodsImg(Map<String, Object> imgMap);
}