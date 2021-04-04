package com.gy.rural_convenience_platform.entity;

public class OrderInfo {

    /*订单号*/
    private String outTradeNo;
    /*支付金额*/
    private String totalAmount;
    /*付款内容*/
    private String subject;

    public OrderInfo() {
    }

    public OrderInfo(String outTradeNo, String totalAmount, String subject) {
        this.outTradeNo = outTradeNo;
        this.totalAmount = totalAmount;
        this.subject = subject;
    }

    public String getOutTradeNo() {
        return outTradeNo;
    }

    public void setOutTradeNo(String outTradeNo) {
        this.outTradeNo = outTradeNo;
    }

    public String getTotalAmount() {
        return totalAmount;
    }

    public void setTotalAmount(String totalAmount) {
        this.totalAmount = totalAmount;
    }

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }
}
