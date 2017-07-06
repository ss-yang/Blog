package com.answer.blog.data;

import android.graphics.Bitmap;

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
    private String cookieId;
    private String avatarPath;
    private Bitmap avatar;
    private boolean login;


    public void setDefult(){
        this.id = "";
        this.nickname = "游客";
        this.intro = "";
        this.qq = "";
        this.email = "";
        this.regTime = "";
        this.cookieId = "";
        this.login = false;
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

    public String getAvatarPath() {
        return avatarPath;
    }

    public void setAvatarPath(String avatarPath) {
        this.avatarPath = avatarPath;
    }

    public Bitmap getAvatar() {
        return avatar;
    }

    public void setAvatar(Bitmap avatar) {
        this.avatar = avatar;
    }

    public String getCookieId() {
        return cookieId;
    }

    public void setCookieId(String cookieId) {
        this.cookieId = cookieId;
    }

    public boolean isLogin() {
        return login;
    }

    public void setLogin(boolean login) {
        this.login = login;
    }
}
