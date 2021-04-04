package com.gy.rural_convenience_platform.service;

import com.gy.rural_convenience_platform.entity.Address;
import com.gy.rural_convenience_platform.mapper.AddressMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AddressService {

    @Autowired
    private AddressMapper addressMapper;

    public boolean addAddr(Address address) {
        int result = addressMapper.insert(address);
        if (result> 0) return true;
        return false;
    }

    public boolean delAddr(Integer id) {
        Address address = addressMapper.selectByPrimaryKey(id);
        address.setUserId(6);
        addressMapper.updateByPrimaryKey(address);
        return true;
    }

    public List<Address> getAddress(Integer userId) {
        List<Address> list = addressMapper.getAddress(userId);
        return list;
    }

    public boolean editAddr(Address address) {
        int result = addressMapper.updateByPrimaryKey(address);
        if (result> 0) return true;
        return false;
    }

    public Address getAddressById(Integer id) {
        Address address = addressMapper.selectByPrimaryKey(id);
        return address;
    }
}
