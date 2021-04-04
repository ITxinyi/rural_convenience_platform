package com.gy.rural_convenience_platform.entity;

import javax.persistence.*;

@Table(name = "worker_order")
public class WorkerOrder {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "worker_id")
    private Integer workerId;

    @Column(name = "user_server_order_id")
    private Integer userServerOrderId;

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
     * @return user_server_order_id
     */
    public Integer getUserServerOrderId() {
        return userServerOrderId;
    }

    /**
     * @param userServerOrderId
     */
    public void setUserServerOrderId(Integer userServerOrderId) {
        this.userServerOrderId = userServerOrderId;
    }
}