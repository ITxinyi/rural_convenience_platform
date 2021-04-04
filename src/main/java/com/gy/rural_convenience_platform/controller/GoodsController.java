package com.gy.rural_convenience_platform.controller;

import com.github.pagehelper.PageInfo;
import com.gy.rural_convenience_platform.entity.Goods;
import com.gy.rural_convenience_platform.service.GoodsService;
import com.gy.rural_convenience_platform.utils.ResponseCode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@CrossOrigin
public class GoodsController {

    @Autowired
    private GoodsService goodsService;

    @RequestMapping("/goods/{pageNum}/{pageSize}")
    public Map<String,Object> goodsByPage(@PathVariable Integer pageNum,@PathVariable Integer pageSize, String keys){
        PageInfo<Goods> pageInfo = goodsService.goodsByPage(pageNum,pageSize,keys);
        return ResponseCode.ok(pageInfo);
    }

    @RequestMapping("/goods/{id}")
    public Map<String,Object> goodsById(@PathVariable Integer id){
        Goods goods = goodsService.goodsById(id);
        return ResponseCode.ok(goods);
    }
    @RequestMapping("/rand/{num}")
    public Map<String,Object> goodsRandByNum(@PathVariable Integer num){
        List<Goods> list = goodsService.goodsRandByNum(num);
        return ResponseCode.ok(list);
    }

}
