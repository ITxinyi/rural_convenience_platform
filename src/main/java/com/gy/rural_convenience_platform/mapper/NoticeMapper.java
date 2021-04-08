package com.gy.rural_convenience_platform.mapper;

import com.gy.rural_convenience_platform.entity.Notice;
import org.springframework.stereotype.Repository;
import tk.mybatis.MyMapper;

import java.util.List;
import java.util.Map;

@Repository
public interface NoticeMapper extends MyMapper<Notice> {
    Integer addNotice(Map map);

    List<Notice> getNoticePage(Map<String, Object> map);

    Integer stopNotice(String id);

    Notice getNotice(String id);

    Integer saveNotice(Map map);
}