package com.gy.rural_convenience_platform.entity;

import javax.persistence.*;

@Table(name = "address")
public class Address {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    /**
     * 用户id
     */
    @Column(name = "user_id")
    private Integer userId;

    /**
     * 收货姓名
     */
    private String name;

    /**
     * 收货电话
     */
    private String phone;

    /**
     * 收货地址
     */
    private String address;

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
     * 获取收货姓名
     *
     * @return name - 收货姓名
     */
    public String getName() {
        return name;
    }

    /**
     * 设置收货姓名
     *
     * @param name 收货姓名
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * 获取收货电话
     *
     * @return phone - 收货电话
     */
    public String getPhone() {
        return phone;
    }

    /**
     * 设置收货电话
     *
     * @param phone 收货电话
     */
    public void setPhone(String phone) {
        this.phone = phone;
    }

    /**
     * 获取收货地址
     *
     * @return address - 收货地址
     */
    public String getAddress() {
        return address;
    }

    /**
     * 设置收货地址
     *
     * @param address 收货地址
     */
    public void setAddress(String address) {
        this.address = address;
    }
}