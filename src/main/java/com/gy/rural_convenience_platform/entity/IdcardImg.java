package com.gy.rural_convenience_platform.entity;

import javax.persistence.*;

@Table(name = "idcard_img")
public class IdcardImg {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "worker_id")
    private Integer workerId;

    /**
     * 身份证正面照片
     */
    @Column(name = "id_img1")
    private String idImg1;

    /**
     * 身份证反面照片
     */
    @Column(name = "id_img2")
    private String idImg2;

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
     * @return worker_id
     */
    public Integer getWorkerId() {
        return workerId;
    }

    /**
     * @param workerId
     */
    public void setWorkerId(Integer workerId) {
        this.workerId = workerId;
    }

    /**
     * 获取身份证正面照片
     *
     * @return id_img1 - 身份证正面照片
     */
    public String getIdImg1() {
        return idImg1;
    }

    /**
     * 设置身份证正面照片
     *
     * @param idImg1 身份证正面照片
     */
    public void setIdImg1(String idImg1) {
        this.idImg1 = idImg1;
    }

    /**
     * 获取身份证反面照片
     *
     * @return id_img2 - 身份证反面照片
     */
    public String getIdImg2() {
        return idImg2;
    }

    /**
     * 设置身份证反面照片
     *
     * @param idImg2 身份证反面照片
     */
    public void setIdImg2(String idImg2) {
        this.idImg2 = idImg2;
    }
}