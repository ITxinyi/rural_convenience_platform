package com.gy.rural_convenience_platform.entity;

import javax.persistence.*;

@Table(name = "notice")
public class Notice {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    /**
     * 通知标题
     */
    private String title;

    /**
     * 通知时间
     */
    @Column(name = "notice_date")
    private String noticeDate;

    /**
     * 图片
     */
    private String img;

    /**
     * 通知内容
     */
    private String content;

    /**
     * @return id
     */
    public Integer getId() {
        return id;
    }

    /**
     * @param id
     */
    public void setId(Integer id) {
        this.id = id;
    }

    /**
     * 获取通知标题
     *
     * @return title - 通知标题
     */
    public String getTitle() {
        return title;
    }

    /**
     * 设置通知标题
     *
     * @param title 通知标题
     */
    public void setTitle(String title) {
        this.title = title;
    }

    /**
     * 获取通知时间
     *
     * @return notice_date - 通知时间
     */
    public String getNoticeDate() {
        return noticeDate;
    }

    /**
     * 设置通知时间
     *
     * @param noticeDate 通知时间
     */
    public void setNoticeDate(String noticeDate) {
        this.noticeDate = noticeDate;
    }

    /**
     * 获取图片
     *
     * @return img - 图片
     */
    public String getImg() {
        return img;
    }

    /**
     * 设置图片
     *
     * @param img 图片
     */
    public void setImg(String img) {
        this.img = img;
    }

    /**
     * 获取通知内容
     *
     * @return content - 通知内容
     */
    public String getContent() {
        return content;
    }

    /**
     * 设置通知内容
     *
     * @param content 通知内容
     */
    public void setContent(String content) {
        this.content = content;
    }
}