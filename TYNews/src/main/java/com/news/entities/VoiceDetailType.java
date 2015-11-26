package com.news.entities;

import org.androidx.frames.entity.BaseType;

import java.util.List;

/**
 * @author slioe shu
 */
public class VoiceDetailType implements BaseType {
    private String title;
    private String createtime;
    private String content;
    private String src;
    private String place;
    private List<VoiceComtType> comtlist;
    private String img;

    public String getImg() {
        return img;
    }

    public void setImg(String img) {
        this.img = img;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getCreatetime() {
        return createtime;
    }

    public void setCreatetime(String createtime) {
        this.createtime = createtime;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getSrc() {
        return src;
    }

    public void setSrc(String src) {
        this.src = src;
    }

    public String getPlace() {
        return place;
    }

    public void setPlace(String place) {
        this.place = place;
    }

    public List<VoiceComtType> getComtlist() {
        return comtlist;
    }

    public void setComtlist(List<VoiceComtType> comtlist) {
        this.comtlist = comtlist;
    }
}
