package com.school.uurun.entity;


/**
 * 订单实体类
 */
public class Order {
    // 主键ID
    public String id;
    // 订单类型（1快递、2外卖）
    public String orderType;
    // 订单物品名称
    public String orderGoodsName;
    // 下单用户ID
    public String submitUserId;
    // 下单用户昵称
    public String submitNickname;
    // 下单用户手机号
    public String submitPhone;
    // 接单用户ID
    public String receiveUserId;
    //  接单用户昵称
    public String receiveNickname;
    // 接单用户手机号
    public String receivePhone;
    ;
    // 取单地址
    public String deliveryAddress;
    // 配送地址
    public String receiveUserAddress;
    // 订单状态（1待接单、2进行中、3已完结）
    public String status;
    //  打赏金额
    public String money;
    // 订单备注
    public String note;
    // 下单时间
    public String createTime;
    // 接单时间
    public String receiveTime;
    // 订单完成时间
    public String finishedTime;

    public Order() {
    }

    public Order(String id, String orderType, String orderGoodsName, String submitUserId, String submitNickname, String submitPhone, String receiveUserId, String receiveNickname, String receivePhone, String deliveryAddress, String receiveUserAddress, String status, String money, String note, String createTime, String receiveTime, String finishedTime) {
        this.id = id;
        this.orderType = orderType;
        this.orderGoodsName = orderGoodsName;
        this.submitUserId = submitUserId;
        this.submitNickname = submitNickname;
        this.submitPhone = submitPhone;
        this.receiveUserId = receiveUserId;
        this.receiveNickname = receiveNickname;
        this.receivePhone = receivePhone;
        this.deliveryAddress = deliveryAddress;
        this.receiveUserAddress = receiveUserAddress;
        this.status = status;
        this.money = money;
        this.note = note;
        this.createTime = createTime;
        this.receiveTime = receiveTime;
        this.finishedTime = finishedTime;
    }

    @Override
    public String toString() {
        return "Order{" +
                "id='" + id + '\'' +
                ", orderType='" + orderType + '\'' +
                ", orderGoodsName='" + orderGoodsName + '\'' +
                ", submitUserId='" + submitUserId + '\'' +
                ", submitNickname='" + submitNickname + '\'' +
                ", submitPhone='" + submitPhone + '\'' +
                ", receiveUserId='" + receiveUserId + '\'' +
                ", receiveNickname='" + receiveNickname + '\'' +
                ", receivePhone='" + receivePhone + '\'' +
                ", deliveryAddress='" + deliveryAddress + '\'' +
                ", receiveUserAddress='" + receiveUserAddress + '\'' +
                ", status='" + status + '\'' +
                ", money='" + money + '\'' +
                ", note='" + note + '\'' +
                ", createTime='" + createTime + '\'' +
                ", receiveTime='" + receiveTime + '\'' +
                ", finishedTime='" + finishedTime + '\'' +
                '}';
    }
}
