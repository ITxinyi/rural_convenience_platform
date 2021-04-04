package com.gy.rural_convenience_platform.entity;

import javax.persistence.*;

@Table(name = "worker")
public class Worker {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    /**
     * 姓名
     */
    private String name;

    /**
     * 账号
     */
    private String username;

    /**
     * 密码
     */
    private String password;

    /**
     * 身份证号
     */
    @Column(name = "id_card")
    private String idCard;

    /**
     * 性别
     */
    private String sex;

    /**
     * 年龄
     */
    private Integer age;

    /**
     * 押金
     */
    private String deposit;

    /**
     * 收入
     */
    private String income;

    /**
     * 状态（0：注册未审核，1：注册审核通过，2：注册审核未通过，3：封号）
     */
    private Integer state;

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
     * 获取姓名
     *
     * @return name - 姓名
     */
    public String getName() {
        return name;
    }

    /**
     * 设置姓名
     *
     * @param name 姓名
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * 获取账号
     *
     * @return username - 账号
     */
    public String getUsername() {
        return username;
    }

    /**
     * 设置账号
     *
     * @param username 账号
     */
    public void setUsername(String username) {
        this.username = username;
    }

    /**
     * 获取密码
     *
     * @return password - 密码
     */
    public String getPassword() {
        return password;
    }

    /**
     * 设置密码
     *
     * @param password 密码
     */
    public void setPassword(String password) {
        this.password = password;
    }

    /**
     * 获取身份证号
     *
     * @return id_card - 身份证号
     */
    public String getIdCard() {
        return idCard;
    }

    /**
     * 设置身份证号
     *
     * @param idCard 身份证号
     */
    public void setIdCard(String idCard) {
        this.idCard = idCard;
    }

    /**
     * 获取性别
     *
     * @return sex - 性别
     */
    public String getSex() {
        return sex;
    }

    /**
     * 设置性别
     *
     * @param sex 性别
     */
    public void setSex(String sex) {
        this.sex = sex;
    }

    /**
     * 获取年龄
     *
     * @return age - 年龄
     */
    public Integer getAge() {
        return age;
    }

    /**
     * 设置年龄
     *
     * @param age 年龄
     */
    public void setAge(Integer age) {
        this.age = age;
    }

    /**
     * 获取押金
     *
     * @return deposit - 押金
     */
    public String getDeposit() {
        return deposit;
    }

    /**
     * 设置押金
     *
     * @param deposit 押金
     */
    public void setDeposit(String deposit) {
        this.deposit = deposit;
    }

    /**
     * 获取收入
     *
     * @return income - 收入
     */
    public String getIncome() {
        return income;
    }

    /**
     * 设置收入
     *
     * @param income 收入
     */
    public void setIncome(String income) {
        this.income = income;
    }

    /**
     * 获取状态（0：注册未审核，1：注册审核通过，2：注册审核未通过，3：封号）
     *
     * @return state - 状态（0：注册未审核，1：注册审核通过，2：注册审核未通过，3：封号）
     */
    public Integer getState() {
        return state;
    }

    /**
     * 设置状态（0：注册未审核，1：注册审核通过，2：注册审核未通过，3：封号）
     *
     * @param state 状态（0：注册未审核，1：注册审核通过，2：注册审核未通过，3：封号）
     */
    public void setState(Integer state) {
        this.state = state;
    }
}