package com.gy.rural_convenience_platform.mapper;

import com.gy.rural_convenience_platform.entity.UserServerOrder;
import org.springframework.stereotype.Repository;
import tk.mybatis.MyMapper;

import java.util.Map;

@Repository
public interface UserServerOrderMapper extends MyMapper<UserServerOrder> {
    void saveServerOrder(Map dataMap);

    UserServerOrder[] getServerOrderDtl(Map<String, Object> map);

    void updServerOrderState(String orderNum);
}