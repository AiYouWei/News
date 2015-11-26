package com.news.views;

import org.androidx.frames.entity.BaseType;

/**
 * @author slioe shu
 */
public class PagerType implements BaseType {
    private String id;
    private String author;
    private String content;

    public PagerType(String id, String author, String content) {
        this.id = id;
        this.author = author;
        this.content = content;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    @Override
    public String toString() {
        return getId();
    }
}
