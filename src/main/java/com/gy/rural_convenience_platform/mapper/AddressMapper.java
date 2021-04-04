package com.gy.rural_convenience_platform.mapper;

import com.gy.rural_convenience_platform.entity.Address;
import org.springframework.stereotype.Repository;
import tk.mybatis.MyMapper;

import java.util.List;

@Repository
public interface AddressMapper extends MyMapper<Address> {
    List<Address> getAddress(Integer userId);

    void deleteById(Integer id);
}