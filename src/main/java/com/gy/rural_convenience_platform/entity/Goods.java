package com.gy.rural_convenience_platform.entity;

import javax.persistence.*;

@Table(name = "goods")
public class Goods {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    /**
     * 商品标题
     */
    @Column(name = "goods_title")
    private String goodsTitle;

    /**
     * 商品描述
     */
    @Column(name = "goods_desc")
    private String goodsDesc;

    /**
     * 商品价格 
     */
    private Double price;

    /**
     * 库存
     */
    private Integer inventory;

    /**
     * 商品状态(0：下架，1：在售)
     */
    private Integer state;

    private GoodsImg goodsImg;

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
     * 获取商品标题
     *
     * @return goods_title - 商品标题
     */
    public String getGoodsTitle() {
        return goodsTitle;
    }

    /**
     * 设置商品标题
     *
     * @param goodsTitle 商品标题
     */
    public void setGoodsTitle(String goodsTitle) {
        this.goodsTitle = goodsTitle;
    }

    /**
     * 获取商品描述
     *
     * @return goods_desc - 商品描述
     */
    public String getGoodsDesc() {
        return goodsDesc;
    }

    /**
     * 设置商品描述
     *
     * @param goodsDesc 商品描述
     */
    public void setGoodsDesc(String goodsDesc) {
        this.goodsDesc = goodsDesc;
    }

    /**
     * 获取商品价格 
     *
     * @return price - 商品价格 
     */
    public Double getPrice() {
        return price;
    }

    /**
     * 设置商品价格 
     *
     * @param price 商品价格 
     */
    public void setPrice(Double price) {
        this.price = price;
    }

    /**
     * 获取库存
     *
     * @return inventory - 库存
     */
    public Integer getInventory() {
        return inventory;
    }

    /**
     * 设置库存
     *
     * @param inventory 库存
     */
    public void setInventory(Integer inventory) {
        this.inventory = inventory;
    }

    /**
     * 获取商品状态(0：下架，1：在售)
     *
     * @return state - 商品状态(0：下架，1：在售)
     */
    public Integer getState() {
        return state;
    }

    /**
     * 设置商品状态(0：下架，1：在售)
     *
     * @param state 商品状态(0：下架，1：在售)
     */
    public void setState(Integer state) {
        this.state = state;
    }

    @Override
    public String toString() {
        return "Goods{" +
                "id=" + id +
                ", goodsTitle='" + goodsTitle + '\'' +
                ", goodsDesc='" + goodsDesc + '\'' +
                ", price=" + price +
                ", inventory=" + inventory +
                ", state=" + state +
                ", goodsImg=" + goodsImg +
                '}';
    }
}