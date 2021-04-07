package com.gy.rural_convenience_platform.controller;

import com.gy.rural_convenience_platform.entity.User;
import com.gy.rural_convenience_platform.service.UserService;
import com.gy.rural_convenience_platform.utils.CurrentUser;
import com.gy.rural_convenience_platform.utils.RedisUtil;
import com.gy.rural_convenience_platform.utils.ResponseCode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;

@RestController
@CrossOrigin
public class UserController {

    @Autowired
    private UserService userService;

    @Autowired
    private RedisUtil re;

    @Autowired
    private CurrentUser currentUser;

    /*用户注册*/
    @RequestMapping("/register")
    public Map<String, Object> register(String nickname, String username, String password, String verCode) {
        if (verCode != null && verCode.equals(re.get(verCode))) {
            User user = new User();
            user.setNickname(nickname);
            user.setUsername(username);
            user.setPassword(password);
            user.setState(1);
            boolean result = userService.register(user);
            return ResponseCode.ok(result);
        }
        return ResponseCode.error("验证码错误");
    }

    /*用户登录*/
    @RequestMapping(value = "/login", method = RequestMethod.GET)
    public Map<String, Object> login(String username, String password,String loginflag, HttpServletRequest request) {
        User user = new User();
        user.setUsername(username);
        user.setPassword(password);
        Map<String, Object> loginResult = userService.login(user, request.getSession().getId(),loginflag);
        return loginResult;
    }

    /*当前登录用户*/
    @RequestMapping("/currentUser")
    public Map<String, Object> currentUser(HttpServletRequest request) {
        String sessionId = request.getSession().getId();
        System.out.println(sessionId + "---===---current");
        User user = userService.currentUser(sessionId);
        if (user != null) {
            user.setPassword("");
            return ResponseCode.ok(user);
        }
        return ResponseCode.error("用户未登录");
    }

    /*密码修改*/
    @RequestMapping("/updateUser")
    public Map<String, Object> updateUser(String nickName, String oldPwd, String newPwd, HttpServletRequest request) {
        User user = currentUser.currentUser(request);
        if (user != null) {
            boolean result = userService.updateUser(user, nickName, oldPwd, newPwd);
            return ResponseCode.ok(result);
        }
        return ResponseCode.ok(false);
    }

    /*登录注销*/
    @RequestMapping("/logout")
    public Map<String,Object> logout(HttpServletRequest request){
        currentUser.logout(request);
        return ResponseCode.ok("注销成功");
    }

}
