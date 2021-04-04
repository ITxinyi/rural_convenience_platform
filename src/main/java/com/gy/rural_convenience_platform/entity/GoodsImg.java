package com.gy.rural_convenience_platform.entity;

import javax.persistence.*;

@Table(name = "goods_img")
public class GoodsImg {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    /**
     * 商品id
     */
    @Column(name = "goods_id")
    private Integer goodsId;

    /**
     * 商品介绍图
     */
    private String img1;

    /**
     * 商品介绍图
     */
    private String img2;

    /**
     * 商品介绍图
     */
    private String img3;

    private String img4;

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
     * 获取商品介绍图
     *
     * @return img1 - 商品介绍图
     */
    public String getImg1() {
        return img1;
    }

    /**
     * 设置商品介绍图
     *
     * @param img1 商品介绍图
     */
    public void setImg1(String img1) {
        this.img1 = img1;
    }

    /**
     * 获取商品介绍图
     *
     * @return img2 - 商品介绍图
     */
    public String getImg2() {
        return img2;
    }

    /**
     * 设置商品介绍图
     *
     * @param img2 商品介绍图
     */
    public void setImg2(String img2) {
        this.img2 = img2;
    }

    /**
     * 获取商品介绍图
     *
     * @return img3 - 商品介绍图
     */
    public String getImg3() {
        return img3;
    }

    /**
     * 设置商品介绍图
     *
     * @param img3 商品介绍图
     */
    public void setImg3(String img3) {
        this.img3 = img3;
    }

    /**
     * @return img4
     */
    public String getImg4() {
        return img4;
    }

    /**
     * @param img4
     */
    public void setImg4(String img4) {
        this.img4 = img4;
    }

    @Override
    public String toString() {
        return "GoodsImg{" +
                "id=" + id +
                ", goodsId=" + goodsId +
                ", img1='" + img1 + '\'' +
                ", img2='" + img2 + '\'' +
                ", img3='" + img3 + '\'' +
                ", img4='" + img4 + '\'' +
                '}';
    }
}