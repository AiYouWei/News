package com.news.entities;

import org.androidx.frames.entity.BaseType;

/**
 * 搜索类型
 *
 * @author slioe shu
 */
public class SearchType implements BaseType {
    private String title;
    private String nid;
    private String type;
    private String createtime;

    public SearchType() {
    }

    public SearchType(String title) {
        this.title = title;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getNid() {
        return nid;
    }

    public void setNid(String nid) {
        this.nid = nid;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getCreatetime() {
        return createtime;
    }

    public void setCreatetime(String createtime) {
        this.createtime = createtime;
    }
}
