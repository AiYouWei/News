package com.news.entities;

import org.androidx.frames.entity.BaseType;

/**
 * 处理结果
 *
 * @author slioe shu
 */
public class VoiceComtType implements BaseType {
    private String comtname;
    private String comtcontent;
    private String comttime;

    public String getComtname() {
        return comtname;
    }

    public void setComtname(String comtname) {
        this.comtname = comtname;
    }

    public String getComtcontent() {
        return comtcontent;
    }

    public void setComtcontent(String comtcontent) {
        this.comtcontent = comtcontent;
    }

    public String getComttime() {
        return comttime;
    }

    public void setComttime(String comttime) {
        this.comttime = comttime;
    }
}
