package com.gy.rural_convenience_platform.controller;

import com.gy.rural_convenience_platform.entity.Cart;
import com.gy.rural_convenience_platform.entity.User;
import com.gy.rural_convenience_platform.service.CartService;
import com.gy.rural_convenience_platform.utils.CurrentUser;
import com.gy.rural_convenience_platform.utils.ResponseCode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Map;

@RestController
@CrossOrigin
public class CartController {

    @Autowired
    private CurrentUser currentUser;
    @Autowired
    private CartService cartService;

    @RequestMapping("/cart")
    public Map<String,Object> addToCart(Integer goodsId, Integer quantity, HttpServletRequest request){
        User user = currentUser.currentUser(request);
        if (user != null) {
            Cart cart = new Cart();
            cart.setGoodsId(goodsId);
            cart.setUserId(user.getId());
            cart.setQuantity(quantity);
            boolean result = cartService.addToCart(cart);
            return ResponseCode.ok(result);
        }
        return ResponseCode.ok(false);
    }

    @RequestMapping("/cart/delete/{id}")
    public Map<String,Object> deleteCart(@PathVariable Integer id,HttpServletRequest request){
        User user = currentUser.currentUser(request);
        if (user != null) {
            boolean result = cartService.deleteCart(id);
            return ResponseCode.ok(result);
        }
        return ResponseCode.ok(false);
    }

    @RequestMapping("/cart/update/{id}")
    public Map<String,Object> updateCart(@PathVariable Integer id,Integer quantity,HttpServletRequest request){
        User user = currentUser.currentUser(request);
        if (user != null) {
            boolean result = cartService.updateCart(id,quantity);
            return ResponseCode.ok(result);
        }
        return ResponseCode.ok(false);
    }

    @RequestMapping("/carts")
    public Map<String,Object> getCarts(HttpServletRequest request){
        User user = currentUser.currentUser(request);
        if (user != null) {
            List<Cart> list = cartService.getCarts(user.getId());
            return ResponseCode.ok(list);
        }
        return ResponseCode.ok(false);
    }

    @RequestMapping("/cartCount")
    public Map<String,Object> getCartCount(HttpServletRequest request){
        User user = currentUser.currentUser(request);
        if (user != null) {
            int count = cartService.getCartCount(user);
            return ResponseCode.ok(count);
        }
        return ResponseCode.error(0);
    }

}
