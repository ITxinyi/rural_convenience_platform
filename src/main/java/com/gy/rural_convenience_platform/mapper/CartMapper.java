package com.gy.rural_convenience_platform.mapper;

import com.gy.rural_convenience_platform.entity.Cart;
import org.springframework.stereotype.Repository;
import tk.mybatis.MyMapper;

import java.util.List;

@Repository
public interface CartMapper extends MyMapper<Cart> {
    Cart getByGoodsId(Cart _cart);

    List<Cart> getCarts(Integer userId);

    int getCartCount(Integer userId);

    List<Cart> getCheckOutGoods(String[] cartIds);
}
