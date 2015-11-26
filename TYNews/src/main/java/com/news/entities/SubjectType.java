package com.news.entities;

import org.androidx.frames.entity.BaseType;

/**
 * 专题列表数据
 *
 * @author slioe shu
 */
public class SubjectType implements BaseType {
    private String specialtype;
    private String specialtitle;
    private String specialimag;
    private String specialid;
    private String speciallink;

    public String getSpecialtype() {
        return specialtype;
    }

    public void setSpecialtype(String specialtype) {
        this.specialtype = specialtype;
    }

    public String getSpecialtitle() {
        return specialtitle;
    }

    public void setSpecialtitle(String specialtitle) {
        this.specialtitle = specialtitle;
    }

    public String getSpecialimag() {
        return specialimag;
    }

    public void setSpecialimag(String specialimag) {
        this.specialimag = specialimag;
    }

    public String getSpecialid() {
        return specialid;
    }

    public void setSpecialid(String specialid) {
        this.specialid = specialid;
    }

    public String getSpeciallink() {
        return speciallink;
    }

    public void setSpeciallink(String speciallink) {
        this.speciallink = speciallink;
    }
}
