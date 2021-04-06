package com.gy.rural_convenience_platform.entity;

import javax.persistence.*;

@Table(name = "user_server_order")
public class UserServerOrder {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    /**
     * 用户id
     */
    @Column(name = "user_id")
    private Integer userId;

    /**
     * 服务类型（买、送、取）
     */
    private String type;

    /**
     * 物品
     */
    private String goods;

    /**
     * 起始地址
     */
    @Column(name = "begin_addr")
    private String beginAddr;

    /**
     * 备注起始地址
     */
    @Column(name = "begin_addr_desc")
    private String beginAddrDesc;

    /**
     * 起始电话
     */
    @Column(name = "begin_phone")
    private String beginPhone;

    /**
     * 终止地址
     */
    @Column(name = "end_addr")
    private String endAddr;

    /**
     * 备注终止地址
     */
    @Column(name = "end_addr_desc")
    private String endAddrDesc;

    /**
     * 终止电话
     */
    @Column(name = "end_phone")
    private String endPhone;

    /**
     * 起始到终止的距离
     */
    private Double distance;

    /**
     * 服务费用
     */
    private Double price;

    /**
     * 订单号
     */
    @Column(name = "order_num")
    private String orderNum;

    /**
     * 备注
     */
    private String remarks;

    /**
     * 订单完成状态（0：未接单，1：已接单，2：已完成）
     */
    @Column(name = "order_state")
    private Integer orderState;

    /**
     * 订单支付状态（0：未支付，1：已支付）
     */
    @Column(name = "pay_state")
    private Integer payState;

    @Column(name = "is_del")
    private String isDel;

    public String getIsDel() {
        return isDel;
    }

    public void setIsDel(String isDel) {
        this.isDel = isDel;
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
     * 获取用户id
     *
     * @return user_id - 用户id
     */
    public Integer getUserId() {
        return userId;
    }

    /**
     * 设置用户id
     *
     * @param userId 用户id
     */
    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    /**
     * 获取服务类型（买、送、取）
     *
     * @return type - 服务类型（买、送、取）
     */
    public String getType() {
        return type;
    }

    /**
     * 设置服务类型（买、送、取）
     *
     * @param type 服务类型（买、送、取）
     */
    public void setType(String type) {
        this.type = type;
    }

    /**
     * 获取物品
     *
     * @return goods - 物品
     */
    public String getGoods() {
        return goods;
    }

    /**
     * 设置物品
     *
     * @param goods 物品
     */
    public void setGoods(String goods) {
        this.goods = goods;
    }

    /**
     * 获取起始地址
     *
     * @return begin_addr - 起始地址
     */
    public String getBeginAddr() {
        return beginAddr;
    }

    /**
     * 设置起始地址
     *
     * @param beginAddr 起始地址
     */
    public void setBeginAddr(String beginAddr) {
        this.beginAddr = beginAddr;
    }

    /**
     * 获取备注起始地址
     *
     * @return begin_addr_desc - 备注起始地址
     */
    public String getBeginAddrDesc() {
        return beginAddrDesc;
    }

    /**
     * 设置备注起始地址
     *
     * @param beginAddrDesc 备注起始地址
     */
    public void setBeginAddrDesc(String beginAddrDesc) {
        this.beginAddrDesc = beginAddrDesc;
    }

    /**
     * 获取起始电话
     *
     * @return begin_phone - 起始电话
     */
    public String getBeginPhone() {
        return beginPhone;
    }

    /**
     * 设置起始电话
     *
     * @param beginPhone 起始电话
     */
    public void setBeginPhone(String beginPhone) {
        this.beginPhone = beginPhone;
    }

    /**
     * 获取终止地址
     *
     * @return end_addr - 终止地址
     */
    public String getEndAddr() {
        return endAddr;
    }

    /**
     * 设置终止地址
     *
     * @param endAddr 终止地址
     */
    public void setEndAddr(String endAddr) {
        this.endAddr = endAddr;
    }

    /**
     * 获取备注终止地址
     *
     * @return end_addr_desc - 备注终止地址
     */
    public String getEndAddrDesc() {
        return endAddrDesc;
    }

    /**
     * 设置备注终止地址
     *
     * @param endAddrDesc 备注终止地址
     */
    public void setEndAddrDesc(String endAddrDesc) {
        this.endAddrDesc = endAddrDesc;
    }

    /**
     * 获取终止电话
     *
     * @return end_phone - 终止电话
     */
    public String getEndPhone() {
        return endPhone;
    }

    /**
     * 设置终止电话
     *
     * @param endPhone 终止电话
     */
    public void setEndPhone(String endPhone) {
        this.endPhone = endPhone;
    }

    /**
     * 获取起始到终止的距离
     *
     * @return distance - 起始到终止的距离
     */
    public Double getDistance() {
        return distance;
    }

    /**
     * 设置起始到终止的距离
     *
     * @param distance 起始到终止的距离
     */
    public void setDistance(Double distance) {
        this.distance = distance;
    }

    /**
     * 获取服务费用
     *
     * @return price - 服务费用
     */
    public Double getPrice() {
        return price;
    }

    /**
     * 设置服务费用
     *
     * @param price 服务费用
     */
    public void setPrice(Double price) {
        this.price = price;
    }

    /**
     * 获取订单号
     *
     * @return order_num - 订单号
     */
    public String getOrderNum() {
        return orderNum;
    }

    /**
     * 设置订单号
     *
     * @param orderNum 订单号
     */
    public void setOrderNum(String orderNum) {
        this.orderNum = orderNum;
    }

    /**
     * 获取备注
     *
     * @return remarks - 备注
     */
    public String getRemarks() {
        return remarks;
    }

    /**
     * 设置备注
     *
     * @param remarks 备注
     */
    public void setRemarks(String remarks) {
        this.remarks = remarks;
    }

    /**
     * 获取订单完成状态（0：未接单，1：已接单，2：已完成）
     *
     * @return order_state - 订单完成状态（0：未接单，1：已接单，2：已完成）
     */
    public Integer getOrderState() {
        return orderState;
    }

    /**
     * 设置订单完成状态（0：未接单，1：已接单，2：已完成）
     *
     * @param orderState 订单完成状态（0：未接单，1：已接单，2：已完成）
     */
    public void setOrderState(Integer orderState) {
        this.orderState = orderState;
    }

    /**
     * 获取订单支付状态（0：未支付，1：已支付）
     *
     * @return pay_state - 订单支付状态（0：未支付，1：已支付）
     */
    public Integer getPayState() {
        return payState;
    }

    /**
     * 设置订单支付状态（0：未支付，1：已支付）
     *
     * @param payState 订单支付状态（0：未支付，1：已支付）
     */
    public void setPayState(Integer payState) {
        this.payState = payState;
    }
}