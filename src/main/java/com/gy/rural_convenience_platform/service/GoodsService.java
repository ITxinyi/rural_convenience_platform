package com.gy.rural_convenience_platform.service;

import com.alibaba.druid.sql.visitor.functions.If;
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

    public PageInfo<Goods> goodsByPage(Integer pageNum, Integer pageSize, String keys,String flag) {
        Map<String, Object> map = new HashMap<>();
        if(!StringUtil.isEmpty(keys)){
            map.put("name", keys);
        }
        if(!StringUtil.isEmpty(flag)){
            map.put("state", flag);
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

    /**
     * 保存商品信息修改
     * @param goodsMap
     * @param imgMap
     * @return
     */
    public boolean saveGoods(Map goodsMap, Map imgMap) {
        Integer result1 = goodsMapper.saveGoods(goodsMap);
        if (result1 > 0 && imgMap != null){
            Integer result2 = goodsMapper.updGoodsImg(imgMap);
            if (result2 < 1) return false;
        }
        if (result1 > 0) return true;
        return false;
    }

    /**
     * 修改商品状态
     * @param id
     * @param state
     * @return
     */
    public Integer updGoodsSta(String id, String state) {
        Map<String, Object> map = new HashMap<>();
        map.put("id", id);
        map.put("state", state);
        Integer result = goodsMapper.saveGoods(map);
        return result;
    }

    /**
     * 添加商品
     * @param
     * @param imgMap
     * @return
     */
    public boolean addGoods(Goods goods, Map<String, Object> imgMap) {

        goodsMapper.addGoods(goods);
        Integer goodsId = goods.getId();
        if (goodsId != null && imgMap != null){
            imgMap.put("goodsId", goodsId);
            Integer result = goodsMapper.addGoodsImg(imgMap);
            if (result < 1) return false;
        }
        return true;
    }
}
