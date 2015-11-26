package com.news.entities;

/**
 * 新闻栏目
 *
 * @author slioe shu
 */
public class ColumnsType {
    private String tid;
    private String name;

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

    @Override
    public String toString() {
        return "ColumnsType{" +
                "tid='" + tid + '\'' +
                ", name='" + name + '\'' +
                '}';
    }
}
