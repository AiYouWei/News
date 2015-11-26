package com.news.entities;

import org.androidx.frames.entity.BaseType;

/**
 * 市局县区
 *
 * @author slioe shu
 */
public class GovernmentType implements BaseType {
    private String tid;
    private String name;
    private String icon;

    public String getTid() {
        return tid;
    }

    public void setTid(String tid) {
        this.tid = tid;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getIcon() {
        return icon;
    }

    public void setIcon(String icon) {
        this.icon = icon;
    }
}
