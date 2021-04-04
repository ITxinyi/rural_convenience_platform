package com.gy.rural_convenience_platform.entity;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Table(name = "order_goods")
public class OrderGoods {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private Integer ugoId;
    private Integer goodsId;
    private Integer quantity;

    private Goods goods;

    public Goods getGoods() {
        return goods;
    }

    public void setGoods(Goods goods) {
        this.goods = goods;
    }

    public Integer getQuantity() {
        return quantity;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getUgoId() {
        return ugoId;
    }

    public void setUgoId(Integer ugoId) {
        this.ugoId = ugoId;
    }

    public Integer getGoodsId() {
        return goodsId;
    }

    public void setGoodsId(Integer goodsId) {
        this.goodsId = goodsId;
    }

    @Override
    public String toString() {
        return "OrderGoods{" +
                "id=" + id +
                ", ugoId=" + ugoId +
                ", goodsId=" + goodsId +
                '}';
    }
}
