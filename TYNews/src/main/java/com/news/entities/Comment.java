package com.news.entities;

import org.androidx.frames.entity.BaseType;

import java.util.List;

/**
 * @author slioe shu
 */
public class Comment implements BaseType {
    private String comtid;
    private String comtpraise;
    private String comtusername;
    private String comtcontent;
    private String comttime;
    private String comtuserheadimg;
    private List<Comment> childcomtlist;

    public String getComtid() {
        return comtid;
    }

    public void setComtid(String comtid) {
        this.comtid = comtid;
    }

    public String getComtpraise() {
        return comtpraise;
    }

    public void setComtpraise(String comtpraise) {
        this.comtpraise = comtpraise;
    }

    public String getComtusername() {
        return comtusername;
    }

    public void setComtusername(String comtusername) {
        this.comtusername = comtusername;
    }

    public String getComtcontent() {
        return comtcontent;
    }

    public void setComtcontent(String comtcontent) {
        this.comtcontent = comtcontent;
    }

    public String getComttime() {
        return comttime;
    }

    public void setComttime(String comttime) {
        this.comttime = comttime;
    }

    public String getComtuserheadimg() {
        return comtuserheadimg;
    }

    public void setComtuserheadimg(String comtuserheadimg) {
        this.comtuserheadimg = comtuserheadimg;
    }

    public List<Comment> getChildcomtlist() {
        return childcomtlist;
    }

    public void setChildcomtlist(List<Comment> childcomtlist) {
        this.childcomtlist = childcomtlist;
    }
}
