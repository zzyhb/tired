package com.yhb.tired.iim;

/**
 * @Auther: Administrator
 * @Date: 2018/11/11 14:55
 * @Description: iim mine信息的封装
 */
public class Mine {
    private String id;
    private String username;
    private String status;
    private String sign;
    private String avatar;

    public Mine() {
    }

    public Mine(String id, String username, String status, String sign, String avatar) {
        this.id = id;
        this.username = username;
        this.status = status;
        this.sign = sign;
        this.avatar = avatar;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getSign() {
        return sign;
    }

    public void setSign(String sign) {
        this.sign = sign;
    }

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

    @Override
    public String toString() {
        return "Mine{" +
                "id='" + id + '\'' +
                ", username='" + username + '\'' +
                ", status='" + status + '\'' +
                ", sign='" + sign + '\'' +
                ", avatar='" + avatar + '\'' +
                '}';
    }
}
