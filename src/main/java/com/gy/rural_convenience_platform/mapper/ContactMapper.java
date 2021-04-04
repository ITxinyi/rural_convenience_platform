package com.gy.rural_convenience_platform.mapper;

import com.gy.rural_convenience_platform.entity.Contact;
import org.springframework.stereotype.Repository;
import tk.mybatis.MyMapper;

@Repository
public interface ContactMapper extends MyMapper<Contact> {
}
