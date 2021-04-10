package com.gy.rural_convenience_platform.utils;

import com.gy.rural_convenience_platform.entity.User;
import com.gy.rural_convenience_platform.entity.Worker;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;

@Component
public class CurrentUser {

    @Autowired
    private RedisUtil re;

    public User currentUser(HttpServletRequest request){
        String sessionId = request.getSession().getId();
        User user = (User) re.get(sessionId);
        return user;
    }

    public Worker currUser(HttpServletRequest request){
        String sessionId = request.getSession().getId();
        Worker worker = (Worker)re.get(sessionId);
        return worker;
    }

    public void logout(HttpServletRequest request){
        String sessionId = request.getSession().getId();
        re.del(sessionId);
    }

}
