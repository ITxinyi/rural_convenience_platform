package com.gy.rural_convenience_platform.controller;

import com.gy.rural_convenience_platform.entity.Address;
import com.gy.rural_convenience_platform.entity.User;
import com.gy.rural_convenience_platform.service.AddressService;
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
public class AddressController {

    @Autowired
    private AddressService addressService;

    @Autowired
    private CurrentUser currentUser;

    @RequestMapping("/addAddr")
    public Map<String, Object> addAddr(String name, String phone, String addr, HttpServletRequest request){
        User user = currentUser.currentUser(request);
        if (user != null) {
            Address address = new Address();
            address.setName(name);
            address.setPhone(phone);
            address.setAddress(addr);
            address.setUserId(user.getId());
            boolean result = addressService.addAddr(address);
            return ResponseCode.ok(result);
        }
        return ResponseCode.error(false);
    }

    @RequestMapping("/editAddr")
    public Map<String, Object> editAddr(Integer id,String name, String phone, String addr, HttpServletRequest request){
        User user = currentUser.currentUser(request);
        if (user != null) {
            Address address = new Address();
            address.setId(id);
            address.setName(name);
            address.setPhone(phone);
            address.setAddress(addr);
            address.setUserId(user.getId());
            boolean result = addressService.editAddr(address);
            return ResponseCode.ok(result);
        }
        return ResponseCode.error(false);
    }

    @RequestMapping("/delAddr")
    public Map<String, Object> delAddr(Integer id,HttpServletRequest request){
        User user = currentUser.currentUser(request);
        if (user != null) {
            boolean result = addressService.delAddr(id);
            return ResponseCode.ok(result);
        }
        return ResponseCode.error(false);
    }

    @RequestMapping("/addrs")
    public Map<String, Object> getAddress(HttpServletRequest request){
        User user = currentUser.currentUser(request);
        if (user != null) {
            List<Address> list = addressService.getAddress(user.getId());
            return ResponseCode.ok(list);
        }
        return ResponseCode.error(false);
    }

    @RequestMapping("/addr")
    public Map<String, Object> getAddressById(Integer id, HttpServletRequest request){
        User user = currentUser.currentUser(request);
        if (user != null) {
            Address address = addressService.getAddressById(id);
            return ResponseCode.ok(address);
        }
        return ResponseCode.error(false);
    }

}
