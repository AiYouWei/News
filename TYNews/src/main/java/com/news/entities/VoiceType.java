package com.news.entities;

import org.androidx.frames.entity.BaseType;

/**
 * 民声列表对象
 *
 * @author slioe shu
 */
public class VoiceType implements BaseType {
    private String id;
    private String title;
    private String img1;
    private String label;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getImg1() {
        return img1;
    }

    public void setImg1(String img1) {
        this.img1 = img1;
    }

    public String getLabel() {
        return label;
    }

    public void setLabel(String label) {
        this.label = label;
    }
}
