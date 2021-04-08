package com.gy.rural_convenience_platform.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.pagehelper.PageInfo;
import com.gy.rural_convenience_platform.config.CosConfig;
import com.gy.rural_convenience_platform.entity.Goods;
import com.gy.rural_convenience_platform.entity.User;
import com.gy.rural_convenience_platform.service.GoodsService;
import com.gy.rural_convenience_platform.utils.CurrentUser;
import com.gy.rural_convenience_platform.utils.ResponseCode;
import com.gy.rural_convenience_platform.utils.UploadUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@CrossOrigin
public class GoodsController {

    @Autowired
    private GoodsService goodsService;
    @Autowired
    private CurrentUser currentUser;
    @Resource
    private ObjectMapper objectMapper;
    @Autowired
    private CosConfig cosConfig;

    @RequestMapping("/goods/{pageNum}/{pageSize}")
    public Map<String,Object> goodsByPage(
            @PathVariable Integer pageNum,
            @PathVariable Integer pageSize,
            @RequestParam(value = "keys",required = false) String keys,
            @RequestParam(value = "flag",required = false) String flag){
        PageInfo<Goods> pageInfo = goodsService.goodsByPage(pageNum,pageSize,keys,flag);
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

    /**
     * 保存商品信息修改
     * @param oneGoods
     * @param files
     * @param request
     * @return
     * @throws IOException
     * @throws URISyntaxException
     * @throws JsonProcessingException
     */
    @PostMapping("/saveGoods")
    public Map<String, Object> saveGoods(@RequestParam("oneGoods") String oneGoods, @RequestParam(value = "files",required = false) MultipartFile[] files, HttpServletRequest request) throws IOException, URISyntaxException, JsonProcessingException {
        User user = currentUser.currentUser(request);
        if(user == null) return ResponseCode.error("用户未登录");
        String username = user.getUsername();
//        将json转换成map
        Map map = objectMapper.readValue(oneGoods, Map.class);
        map.put("username", username);

        Map<String,Object> imgMap = null;
        if (files != null) {
            imgMap = new HashMap<>();
            imgMap.put("goodsId",map.get("id"));
//        将文件上传至腾讯云cos，并获取访问地址
            for (int i = 0; i < files.length; i++) {
                String url = UploadUtil.uploadToOos(files[i], username, cosConfig);
                imgMap.put("img" + (i+1), url);
            }
        }
//        将数据存入数据库
        boolean result = goodsService.saveGoods(map,imgMap);
        if (result) return ResponseCode.ok("保存成功");
        return ResponseCode.error("保存失败");
    }

    /**
     * 修改商品状态
     * @param
     * @return
     */
    @GetMapping("/updGoodsSta/{id}/{state}")
    public Map<String,Object> updGoodsSta(@PathVariable("id") String id,@PathVariable("state") String state){
        Integer result = goodsService.updGoodsSta(id,state);
        return ResponseCode.ok(result > 0 ? "修改成功" : "修改失败");
    }

    /**
     * 添加商品
     * @param goods
     * @param files
     * @param request
     * @return
     * @throws IOException
     * @throws URISyntaxException
     * @throws JsonProcessingException
     */
    @PostMapping("/addGoods")
    public Map<String, Object> addGoods(
            @RequestParam("goods") String goods,
            @RequestParam(value = "files",required = false) MultipartFile[] files,
            HttpServletRequest request) throws IOException, URISyntaxException, JsonProcessingException {
        User user = currentUser.currentUser(request);
        if(user == null) return ResponseCode.error("用户未登录");
        String username = user.getUsername();
//        将json转换成map
        Goods _goods = objectMapper.readValue(goods, Goods.class);

        Map<String,Object> imgMap = null;
        if (files != null) {
            imgMap = new HashMap<>();
//        将文件上传至腾讯云cos，并获取访问地址
            for (int i = 0; i < files.length; i++) {
                String url = UploadUtil.uploadToOos(files[i], username, cosConfig);
                imgMap.put("img" + (i+1), url);
            }
        }
//        将数据存入数据库
        boolean result = goodsService.addGoods(_goods,imgMap);
        if (result) return ResponseCode.ok("保存成功");
        return ResponseCode.error("保存失败");
    }

}
