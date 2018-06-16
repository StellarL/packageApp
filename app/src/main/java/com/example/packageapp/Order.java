package com.example.packageapp;

/**
 * Created by 李馨 on 2018/6/15.
 */

public class Order {
    private int Id;
    private int orderId;//存 user _id
    private String orderName;//存user relname
    private String orderPhone;
    private int receiveId;
    private String receiveName;
    private String receivePhone;
    private String startPlace;
    private String endPlace;
    private int payment;
    private int type;
    private int status;
    private String finish;
    private String info;
    private int score;

    public Order() {
    }

    public Order(int id, int orderId, String orderName, String orderPhone, int receiveId, String receiveName, String receivePhone, String startPlace, String endPlace, int payment, int type, int status, String finish, String info, int score) {
        Id = id;
        this.orderId = orderId;
        this.orderName = orderName;
        this.orderPhone = orderPhone;
        this.receiveId = receiveId;
        this.receiveName = receiveName;
        this.receivePhone = receivePhone;
        this.startPlace = startPlace;
        this.endPlace = endPlace;
        this.payment = payment;
        this.type = type;
        this.status = status;
        this.finish = finish;
        this.info = info;
        this.score = score;
    }

    public int getId() {
        return Id;
    }

    public void setId(int id) {
        Id = id;
    }

    public int getOrderId() {
        return orderId;
    }

    public void setOrderId(int orderId) {
        this.orderId = orderId;
    }

    public String getOrderName() {
        return orderName;
    }

    public void setOrderName(String orderName) {
        this.orderName = orderName;
    }

    public String getOrderPhone() {
        return orderPhone;
    }

    public void setOrderPhone(String orderPhone) {
        this.orderPhone = orderPhone;
    }

    public int getReceiveId() {
        return receiveId;
    }

    public void setReceiveId(int receiveId) {
        this.receiveId = receiveId;
    }

    public String getReceiveName() {
        return receiveName;
    }

    public void setReceiveName(String receiveName) {
        this.receiveName = receiveName;
    }

    public String getReceivePhone() {
        return receivePhone;
    }

    public void setReceivePhone(String receivePhone) {
        this.receivePhone = receivePhone;
    }

    public String getStartPlace() {
        return startPlace;
    }

    public void setStartPlace(String startPlace) {
        this.startPlace = startPlace;
    }

    public String getEndPlace() {
        return endPlace;
    }

    public void setEndPlace(String endPlace) {
        this.endPlace = endPlace;
    }

    public int getPayment() {
        return payment;
    }

    public void setPayment(int payment) {
        this.payment = payment;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getFinish() {
        return finish;
    }

    public void setFinish(String finish) {
        this.finish = finish;
    }

    public String getInfo() {
        return info;
    }

    public void setInfo(String info) {
        this.info = info;
    }

    public int getScore() {
        return score;
    }

    public void setScore(int score) {
        this.score = score;
    }

    @Override
    public String toString() {
        return "Order{" +
                "Id=" + Id +
                ", orderId=" + orderId +
                ", orderName='" + orderName + '\'' +
                ", orderPhone='" + orderPhone + '\'' +
                ", receiveId=" + receiveId +
                ", receiveName='" + receiveName + '\'' +
                ", receivePhone='" + receivePhone + '\'' +
                ", startPlace='" + startPlace + '\'' +
                ", endPlace='" + endPlace + '\'' +
                ", payment=" + payment +
                ", type=" + type +
                ", status=" + status +
                ", finish='" + finish + '\'' +
                ", info='" + info + '\'' +
                ", score=" + score +
                '}';
    }
}
