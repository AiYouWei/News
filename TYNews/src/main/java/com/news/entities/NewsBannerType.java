package com.news.entities;

import org.androidx.frames.entity.BaseType;

/**
 * 新闻头部banner
 *
 * @author slioe shu
 */
public class NewsBannerType implements BaseType {
    private String toptype;
    private String toptitle;
    private String topimag;
    private String topid;
    private String toplink;

    public String getToptype() {
        return toptype;
    }

    public void setToptype(String toptype) {
        this.toptype = toptype;
    }

    public String getToptitle() {
        return toptitle;
    }

    public void setToptitle(String toptitle) {
        this.toptitle = toptitle;
    }

    public String getTopimag() {
        return topimag;
    }

    public void setTopimag(String topimag) {
        this.topimag = topimag;
    }

    public String getTopid() {
        return topid;
    }

    public void setTopid(String topid) {
        this.topid = topid;
    }

    public String getToplink() {
        return toplink;
    }

    public void setToplink(String toplink) {
        this.toplink = toplink;
    }

    @Override
    public String toString() {
        return "{toptype=" + toptype + ", toptitle=" + toptitle + ", topimag=" + topimag + ", topid=" + topid + ", toplink=" + toplink + '}';
    }
}
