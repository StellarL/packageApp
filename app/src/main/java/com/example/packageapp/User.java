package com.example.packageapp;

/**
 * Created by 李馨 on 2018/6/15.
 */

public class User {
    private int Id;
    private String phone;
    private String password;
    private String relName;
    private String idCard;
    private String idImg;
    private int score;

    public User() {
    }

    public User(int id, String phone, String password, String relName, String idCard, String idImg, int score) {
        Id = id;
        this.phone = phone;
        this.password = password;
        this.relName = relName;
        this.idCard = idCard;
        this.idImg = idImg;
        this.score = score;
    }

    public int getId() {
        return Id;
    }

    public void setId(int id) {
        Id = id;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getRelName() {
        return relName;
    }

    public void setRelName(String relName) {
        this.relName = relName;
    }

    public String getIdCard() {
        return idCard;
    }

    public void setIdCard(String idCard) {
        this.idCard = idCard;
    }

    public String getIdImg() {
        return idImg;
    }

    public void setIdImg(String idImg) {
        this.idImg = idImg;
    }

    public int getScore() {
        return score;
    }

    public void setScore(int score) {
        this.score = score;
    }

    @Override
    public String toString() {
        return "User{" +
                "Id=" + Id +
                ", phone='" + phone + '\'' +
                ", password='" + password + '\'' +
                ", relName='" + relName + '\'' +
                ", idCard='" + idCard + '\'' +
                ", idImg='" + idImg + '\'' +
                ", score=" + score +
                '}';
    }
}
