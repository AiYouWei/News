package com.news.entities;

import org.androidx.frames.entity.BaseType;

/**
 * @author slioe shu
 */
public class CollectionType implements BaseType {
    private String title;
    private String content;
    private String create_time;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getCreate_time() {
        return create_time;
    }

    public void setCreate_time(String create_time) {
        this.create_time = create_time;
    }
}
