package com.yhb.tired.iim.pojo;

import java.util.Date;

public class BlockFriends {
    private String id;

    private String blockid;

    private String friendsid;

    private Date createtime;

    private String createid;

    private Date updatetime;

    private String updateid;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id == null ? null : id.trim();
    }

    public String getBlockid() {
        return blockid;
    }

    public void setBlockid(String blockid) {
        this.blockid = blockid == null ? null : blockid.trim();
    }

    public String getFriendsid() {
        return friendsid;
    }

    public void setFriendsid(String friendsid) {
        this.friendsid = friendsid == null ? null : friendsid.trim();
    }

    public Date getCreatetime() {
        return createtime;
    }

    public void setCreatetime(Date createtime) {
        this.createtime = createtime;
    }

    public String getCreateid() {
        return createid;
    }

    public void setCreateid(String createid) {
        this.createid = createid == null ? null : createid.trim();
    }

    public Date getUpdatetime() {
        return updatetime;
    }

    public void setUpdatetime(Date updatetime) {
        this.updatetime = updatetime;
    }

    public String getUpdateid() {
        return updateid;
    }

    public void setUpdateid(String updateid) {
        this.updateid = updateid == null ? null : updateid.trim();
    }
}