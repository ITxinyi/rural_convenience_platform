package com.gy.rural_convenience_platform.entity;

import javax.persistence.*;
import java.util.List;

@Table(name = "user_goods_order")
public class UserGoodsOrder {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    /**
     * 地址id
     */
    @Column(name = "addr_id")
    private Integer addrId;

    private Integer userId;

    /**
     * 订单编号
     */
    @Column(name = "order_num")
    private String orderNum;

    /**
     * 实付金额
     */
    @Column(name = "pay_price")
    private Double payPrice;

    /**
     * 购买数量
     */
    private Integer number;

    /**
     * 订单状态（0：未接单，1：已接单，2：已完成）
     */
    @Column(name = "order_state")
    private Integer orderState;

    /**
     * 支付状态（0：未支付，1：已支付）
     */
    @Column(name = "pay_state")
    private Integer payState;

    private String orderDate;

    private List<OrderGoods> orderGoods;

    private GoodsImg goodsImg;

    public UserGoodsOrder() {
    }

    public UserGoodsOrder(Integer addrId, Integer userId, String orderNum, Double payPrice, Integer number, Integer orderState, Integer payState,String orderDate) {
        this.addrId = addrId;
        this.userId = userId;
        this.orderNum = orderNum;
        this.payPrice = payPrice;
        this.number = number;
        this.orderState = orderState;
        this.payState = payState;
        this.orderDate = orderDate;
    }

    public String getOrderDate() {
        return orderDate;
    }

    public void setOrderDate(String orderDate) {
        this.orderDate = orderDate;
    }

    public GoodsImg getGoodsImg() {
        return goodsImg;
    }

    public void setGoodsImg(GoodsImg goodsImg) {
        this.goodsImg = goodsImg;
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
     * 获取地址id
     *
     * @return addr_id - 地址id
     */
    public Integer getAddrId() {
        return addrId;
    }

    /**
     * 设置地址id
     *
     * @param addrId 地址id
     */
    public void setAddrId(Integer addrId) {
        this.addrId = addrId;
    }

    /**
     * 获取订单编号
     *
     * @return order_num - 订单编号
     */
    public String getOrderNum() {
        return orderNum;
    }

    /**
     * 设置订单编号
     *
     * @param orderNum 订单编号
     */
    public void setOrderNum(String orderNum) {
        this.orderNum = orderNum;
    }

    /**
     * 获取实付金额
     *
     * @return pay_price - 实付金额
     */
    public Double getPayPrice() {
        return payPrice;
    }

    /**
     * 设置实付金额
     *
     * @param payPrice 实付金额
     */
    public void setPayPrice(Double payPrice) {
        this.payPrice = payPrice;
    }

    /**
     * 获取购买数量
     *
     * @return number - 购买数量
     */
    public Integer getNumber() {
        return number;
    }

    /**
     * 设置购买数量
     *
     * @param number 购买数量
     */
    public void setNumber(Integer number) {
        this.number = number;
    }

    /**
     * 获取订单状态（0：未接单，1：已接单，2：已完成）
     *
     * @return order_state - 订单状态（0：未接单，1：已接单，2：已完成）
     */
    public Integer getOrderState() {
        return orderState;
    }

    /**
     * 设置订单状态（0：未接单，1：已接单，2：已完成）
     *
     * @param orderState 订单状态（0：未接单，1：已接单，2：已完成）
     */
    public void setOrderState(Integer orderState) {
        this.orderState = orderState;
    }

    /**
     * 获取支付状态（0：未支付，1：已支付）
     *
     * @return pay_state - 支付状态（0：未支付，1：已支付）
     */
    public Integer getPayState() {
        return payState;
    }

    /**
     * 设置支付状态（0：未支付，1：已支付）
     *
     * @param payState 支付状态（0：未支付，1：已支付）
     */
    public void setPayState(Integer payState) {
        this.payState = payState;
    }

    public List<OrderGoods> getOrderGoods() {
        return orderGoods;
    }

    public void setOrderGoods(List<OrderGoods> orderGoods) {
        this.orderGoods = orderGoods;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    @Override
    public String toString() {
        return "UserGoodsOrder{" +
                "id=" + id +
                ", addrId=" + addrId +
                ", userId=" + userId +
                ", orderNum='" + orderNum + '\'' +
                ", payPrice=" + payPrice +
                ", number=" + number +
                ", orderState=" + orderState +
                ", payState=" + payState +
                ", orderGoods=" + orderGoods +
                '}';
    }
}