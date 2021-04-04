package com.gy.rural_convenience_platform.mapper;

import com.gy.rural_convenience_platform.entity.User;
import org.springframework.stereotype.Repository;
import tk.mybatis.MyMapper;

@Repository
public interface UserMapper extends MyMapper<User> {
    User login(User user);
}