package com.imooc.pojo.vo;

public class PayMentVO {

    private String order_no;	//订单号
    private String subject;		//商品名称
    private String pay_type;	//支付类型
    private String money;		//订单金额
    private String realmoney;	//实际支付金额
    private String result;		//支付结果
    private String xddpay_order;	//小叮当订单号
    private String app_id;		//接口ID
    private String extra;		//用户自定义数据
    private String sign;		//签名

    public String getOrder_no() {
        return order_no;
    }

    public void setOrder_no(String order_no) {
        this.order_no = order_no;
    }

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public String getPay_type() {
        return pay_type;
    }

    public void setPay_type(String pay_type) {
        this.pay_type = pay_type;
    }

    public String getMoney() {
        return money;
    }

    public void setMoney(String money) {
        this.money = money;
    }

    public String getRealmoney() {
        return realmoney;
    }

    public void setRealmoney(String realmoney) {
        this.realmoney = realmoney;
    }

    public String getResult() {
        return result;
    }

    public void setResult(String result) {
        this.result = result;
    }

    public String getXddpay_order() {
        return xddpay_order;
    }

    public void setXddpay_order(String xddpay_order) {
        this.xddpay_order = xddpay_order;
    }

    public String getApp_id() {
        return app_id;
    }

    public void setApp_id(String app_id) {
        this.app_id = app_id;
    }

    public String getExtra() {
        return extra;
    }

    public void setExtra(String extra) {
        this.extra = extra;
    }

    public String getSign() {
        return sign;
    }

    public void setSign(String sign) {
        this.sign = sign;
    }
}
