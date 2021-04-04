package com.gy.rural_convenience_platform.controller;

import com.gy.rural_convenience_platform.entity.GoodsEvaluate;
import com.gy.rural_convenience_platform.entity.User;
import com.gy.rural_convenience_platform.service.EvaluateService;
import com.gy.rural_convenience_platform.utils.CurrentUser;
import com.gy.rural_convenience_platform.utils.RedisUtil;
import com.gy.rural_convenience_platform.utils.ResponseCode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Map;

@RestController
@CrossOrigin
public class EvaluateController {

    @Autowired
    private EvaluateService evaluateService;
    @Autowired
    private RedisUtil re;
    @Autowired
    private CurrentUser currentUser;

    @RequestMapping("/comment/user/{goodsId}")
    public Map<String,Object> subComment(@PathVariable Integer goodsId, String userComment, HttpServletRequest request){
        User user = currentUser.currentUser(request);
        if (user != null) {
            boolean result = evaluateService.subComment(user, goodsId, userComment);
            return ResponseCode.ok(result);
        }
        return ResponseCode.ok(false);
    }

    @RequestMapping("/comments/user/{goodsId}")
    public Map<String,Object> getComments(@PathVariable Integer goodsId,HttpServletRequest request){
        User user = currentUser.currentUser(request);
        if (user != null) {
            List<GoodsEvaluate> list = evaluateService.getComments(goodsId);
            return ResponseCode.ok(list);
        }
        return ResponseCode.ok(false);
    }

}
