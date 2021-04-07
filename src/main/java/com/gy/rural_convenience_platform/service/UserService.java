package com.gy.rural_convenience_platform.service;

import com.gy.rural_convenience_platform.entity.User;
import com.gy.rural_convenience_platform.mapper.UserMapper;
import com.gy.rural_convenience_platform.utils.RedisUtil;
import com.gy.rural_convenience_platform.utils.ResponseCode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.Map;

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

    /**
     *
     * @param _user
     * @param sessionId
     * @param loginflag 登录界面标志：0用户登录界面，1管理员登录界面
     * @return
     */
    public Map<String, Object> login(User _user, String sessionId,String loginflag) {
        System.out.println(_user + "_user");
        User user = userMapper.login(_user);
        System.out.println(user);
        if (user != null) {
            String isManage = user.getIsManage();
            if ("0".equals(loginflag) && "1".equals(isManage)) {
                return ResponseCode.error("用户名或密码错误！");
            }
            if ("1".equals(loginflag) && "0".equals(isManage)) {
                return ResponseCode.error("用户名或密码错误！");
            }
            Integer loginCount = Integer.valueOf(user.getLoginCount());
            /*判断是否管理员账号首次登录*/
            if ("1".equals(isManage) && loginCount < 1) {
                return ResponseCode.ok("首次登录");
            }
            /*登录成功，将用户信息存入redis*/
            re.set(sessionId, user, 60 * 30);
            /*更新登录次数*/
            user.setLoginCount((Integer.valueOf(user.getLoginCount()) + 1) + "");
            userMapper.updateByPrimaryKey(user);
            return ResponseCode.ok("登录成功");
        }
        return ResponseCode.error("用户名或密码错误！");
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
