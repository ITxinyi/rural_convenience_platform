package com.gy.rural_convenience_platform.service;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.gy.rural_convenience_platform.entity.Goods;
import com.gy.rural_convenience_platform.mapper.GoodsMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tk.mybatis.mapper.entity.Example;
import tk.mybatis.mapper.util.StringUtil;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class GoodsService {

    @Autowired
    private GoodsMapper goodsMapper;

    public PageInfo<Goods> goodsByPage(Integer pageNum, Integer pageSize, String keys) {
        Map<String, Object> map = new HashMap<>();
        if(!StringUtil.isEmpty(keys)){
            map.put("name", keys);
        }
        map.put("startIndex", (pageNum-1)*pageSize);
        map.put("pageSize", pageSize);
        PageHelper.startPage(pageNum, pageSize);
        List<Goods> list = goodsMapper.goodsPage(map);
        PageInfo<Goods> pageInfo = new PageInfo<>(list);
        System.out.println(pageInfo.getSize());
        System.out.println(pageInfo.getTotal());
        return pageInfo;
    }

    public Goods goodsById(Integer id) {
        return goodsMapper.goodsById(id);
    }

    public List<Goods> goodsRandByNum(Integer num) {
        List<Goods> list = goodsMapper.goodsRandByNum(num);
        return list;
    }
}
