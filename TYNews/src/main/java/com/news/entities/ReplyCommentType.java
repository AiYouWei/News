package com.news.entities;

import org.androidx.frames.entity.BaseType;

/**
 * @author slioe shu
 */
public class ReplyCommentType implements BaseType {
    private String nid;
    private String ntype;
    private String comtuserheadimg;
    private String comtusername;
    private String comtcontent;
    private String comttime;
    private String ntitle;

    public String getComttime() {
        return comttime;
    }

    public void setComttime(String comttime) {
        this.comttime = comttime;
    }

    public String getNid() {
        return nid;
    }

    public String getNtitle() {
        return ntitle;
    }

    public void setNtitle(String ntitle) {
        this.ntitle = ntitle;
    }

    public void setNid(String nid) {
        this.nid = nid;
    }

    public String getNtype() {
        return ntype;
    }

    public void setNtype(String ntype) {
        this.ntype = ntype;
    }

    public String getComtuserheadimg() {
        return comtuserheadimg;
    }

    public void setComtuserheadimg(String comtuserheadimg) {
        this.comtuserheadimg = comtuserheadimg;
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
}
