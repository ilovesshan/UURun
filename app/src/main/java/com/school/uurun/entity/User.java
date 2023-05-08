package com.school.uurun.entity;


/**
 * 用户实体类
 */
public class User {

    public String id;
    public String account;
    public String password;
    public String nickname;
    public String userType;
    public String phone;

    public User() {
    }

    public User(String id, String account, String password, String nickname, String userType, String phone) {
        this.id = id;
        this.account = account;
        this.password = password;
        this.nickname = nickname;
        this.userType = userType;
        this.phone = phone;
    }

    @Override
    public String toString() {
        return "User{" +
                "id='" + id + '\'' +
                ", account='" + account + '\'' +
                ", password='" + password + '\'' +
                ", nickname='" + nickname + '\'' +
                ", userType='" + userType + '\'' +
                ", phone='" + phone + '\'' +
                '}';
    }
}
