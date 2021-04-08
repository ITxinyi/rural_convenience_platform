package com.gy.rural_convenience_platform.service;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.gy.rural_convenience_platform.entity.Notice;
import com.gy.rural_convenience_platform.mapper.NoticeMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tk.mybatis.mapper.util.StringUtil;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class AdminService {

    @Autowired
    private NoticeMapper noticeMapper;

    /**
     * 添加公告
     *
     * @param map
     * @return
     */
    public boolean addNotice(Map map) {

        Integer result = noticeMapper.addNotice(map);
        if (result > 0) return true;
        return false;
    }

    /**
     * 查询公告列表
     * @param pageNum
     * @param pageSize
     * @param key
     * @return
     */
    public PageInfo<Notice> getNoticePage(Integer pageNum, Integer pageSize, String key) {

        Map<String, Object> map = new HashMap<>();
        if(!StringUtil.isEmpty(key)){
            map.put("title", key);
        }
        map.put("startIndex", (pageNum-1)*pageSize);
        map.put("pageSize", pageSize);
        PageHelper.startPage(pageNum, pageSize);

        List<Notice> list = noticeMapper.getNoticePage(map);

        PageInfo<Notice> noticePageInfo = new PageInfo<>(list);
        return noticePageInfo;

    }

    /**
     * 停止公告
     *
     * @param id
     * @return
     */
    public Integer stopNotice(String id) {

        Integer result = noticeMapper.stopNotice(id);
        return result;
    }

    /**
     * 查询公告
     *
     * @param id
     * @return
     */
    public Notice getNotice(String id) {

        Notice notice = noticeMapper.getNotice(id);
        return notice;
    }

    /**
     * 保存编辑信息
     *
     * @param map
     * @return
     */
    public boolean saveNotice(Map map) {
        Integer result = noticeMapper.saveNotice(map);
        if (result > 0) return true;
        return false;
    }
}
