package com.answer.blog.data;

/**
 * Created by Answer on 2017/5/16.
 * 当前登录用户的用户信息
 */

public class User {
    private String id;
    private String nickname;
    private String intro;
    private String qq;
    private String email;
    private String regTime;
    private String sessionID;
    private boolean isLogin;

    public User(){
        this.id = "";
        this.nickname = "游客";
        this.intro = "";
        this.qq = "";
        this.email = "";
        this.regTime = "";
        this.sessionID = "";
        this.isLogin = false;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public String getIntro() {
        return intro;
    }

    public void setIntro(String intro) {
        this.intro = intro;
    }

    public String getQq() {
        return qq;
    }

    public void setQq(String qq) {
        this.qq = qq;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getRegTime() {
        return regTime;
    }

    public void setRegTime(String regTime) {
        this.regTime = regTime;
    }

    public String getSessionID() {
        return sessionID;
    }

    public void setSessionID(String sessionID) {
        this.sessionID = sessionID;
    }

    public boolean isLogin() {
        return isLogin;
    }

    public void setLogin(boolean login) {
        isLogin = login;
    }
}
