package com.gy.rural_convenience_platform.entity;

import javax.persistence.*;

@Table(name = "goods_evaluate")
public class GoodsEvaluate {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    /**
     * 商品id
     */
    @Column(name = "goods_id")
    private Integer goodsId;

    @Column(name = "user_id")
    private Integer userId;

    /**
     * 评价日期
     */
    @Column(name = "evaluate_date")
    private String evaluateDate;

    /**
     * 评价内容
     */
    private String content;

    private User user;

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

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
     * 获取商品id
     *
     * @return goods_id - 商品id
     */
    public Integer getGoodsId() {
        return goodsId;
    }

    /**
     * 设置商品id
     *
     * @param goodsId 商品id
     */
    public void setGoodsId(Integer goodsId) {
        this.goodsId = goodsId;
    }

    /**
     * 获取评价日期
     *
     * @return evaluate_date - 评价日期
     */
    public String getEvaluateDate() {
        return evaluateDate;
    }

    /**
     * 设置评价日期
     *
     * @param evaluateDate 评价日期
     */
    public void setEvaluateDate(String evaluateDate) {
        this.evaluateDate = evaluateDate;
    }

    /**
     * 获取评价内容
     *
     * @return content - 评价内容
     */
    public String getContent() {
        return content;
    }

    /**
     * 设置评价内容
     *
     * @param content 评价内容
     */
    public void setContent(String content) {
        this.content = content;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    @Override
    public String toString() {
        return "GoodsEvaluate{" +
                "id=" + id +
                ", goodsId=" + goodsId +
                ", userId=" + userId +
                ", evaluateDate='" + evaluateDate + '\'' +
                ", content='" + content + '\'' +
                ", user=" + user +
                '}';
    }
}