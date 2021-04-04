package com.gy.rural_convenience_platform.service;

import com.gy.rural_convenience_platform.entity.Cart;
import com.gy.rural_convenience_platform.entity.User;
import com.gy.rural_convenience_platform.mapper.CartMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CartService {

    @Autowired
    private CartMapper cartMapper;

    public boolean addToCart(Cart _cart) {
        Cart cart = cartMapper.getByGoodsId(_cart);
        int result = 0;
        if (cart != null) {
            cart.setQuantity(cart.getQuantity() + _cart.getQuantity());
            result = cartMapper.updateByPrimaryKey(cart);
        }else{
            result = cartMapper.insert(_cart);
        }
        if(result > 0) return true;
        return false;
    }

    public boolean deleteCart(Integer id) {
        int result = cartMapper.deleteByPrimaryKey(id);
        if(result > 0) return true;
        return false;
    }

    public List<Cart> getCarts(Integer userId) {
        List<Cart> list = cartMapper.getCarts(userId);
        return list;
    }

    public boolean updateCart(Integer id, Integer quantity) {
        Cart cart = cartMapper.selectByPrimaryKey(id);
        if (cart != null) {
            cart.setQuantity(quantity);
            int result = cartMapper.updateByPrimaryKey(cart);
            if (result > 0) return true;
        }
        return false;
    }

    public int getCartCount(User user) {
        int count = cartMapper.getCartCount(user.getId());
        return count;
    }
}
