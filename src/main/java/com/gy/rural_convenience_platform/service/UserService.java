package com.gy.rural_convenience_platform.service;

import com.gy.rural_convenience_platform.entity.User;
import com.gy.rural_convenience_platform.mapper.UserMapper;
import com.gy.rural_convenience_platform.utils.RedisUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestMapping;

@Service
public class UserService {

    @Autowired
    private UserMapper userMapper;

    @Autowired
    private RedisUtil re;

    public boolean register(User user) {
        int result = userMapper.insert(user);
        if(result > 0) return true;
        return false;
    }

    public boolean login(User _user, String sessionId) {
        System.out.println(_user + "_user");
        User user = userMapper.login(_user);
        System.out.println(user);
        if(user != null){
            re.set(sessionId, user, 60 * 30);
            return true;
        }
        return false;
    }

    public User currentUser(String sessionId) {
        User user = (User) re.get(sessionId);
        return user;
    }

    public boolean updateUser(User user, String nickName, String oldPwd, String newPwd) {
        if (nickName != null) user.setNickname(nickName);
        if ((oldPwd != null && oldPwd != "") && (newPwd != null && newPwd != "")) {
            if(oldPwd.equals(user.getPassword())){
                user.setPassword(newPwd);
            }
        }
        System.out.println(user);
        int result = userMapper.updateByPrimaryKey(user);
        if (result > 0 ) return true;
        return false;
    }
}
