package com.gy.rural_convenience_platform.entity;

import javax.persistence.*;

@Table(name = "server_order_evaluate")
public class ServerOrderEvaluate {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    /**
     * 服务订单id
     */
    @Column(name = "user_server_order_id")
    private Integer userServerOrderId;

    /**
     * 评价等级
     */
    private String grade;

    /**
     * 评价时间
     */
    @Column(name = "evaluate_date")
    private String evaluateDate;

    /**
     * 评价内容
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
     * 获取服务订单id
     *
     * @return user_server_order_id - 服务订单id
     */
    public Integer getUserServerOrderId() {
        return userServerOrderId;
    }

    /**
     * 设置服务订单id
     *
     * @param userServerOrderId 服务订单id
     */
    public void setUserServerOrderId(Integer userServerOrderId) {
        this.userServerOrderId = userServerOrderId;
    }

    /**
     * 获取评价等级
     *
     * @return grade - 评价等级
     */
    public String getGrade() {
        return grade;
    }

    /**
     * 设置评价等级
     *
     * @param grade 评价等级
     */
    public void setGrade(String grade) {
        this.grade = grade;
    }

    /**
     * 获取评价时间
     *
     * @return evaluate_date - 评价时间
     */
    public String getEvaluateDate() {
        return evaluateDate;
    }

    /**
     * 设置评价时间
     *
     * @param evaluateDate 评价时间
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
}