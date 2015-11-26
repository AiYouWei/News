package com.news.entities;

import org.androidx.frames.entity.BaseMultiType;

import java.util.List;

/**
 * 新闻列表
 *
 * @author slioe shu
 */
public class NewsListType extends BaseMultiType {
    private String newstype;
    private String newstitle;
    private String newsid;
    private String newsicon;
    private String newssubtitle;
    private String newslabel;
    private String comment_count;
    private String newslink;
    private List<NewsImage> newspics;
    private String specialtype;
    private String specialtitle;
    private String specialimag;
    private String specialid;
    private String speciallink;

    public NewsListType(int viewType) {
        super(viewType);
    }

    public String getNewstype() {
        return newstype;
    }

    public void setNewstype(String newstype) {
        this.newstype = newstype;
    }

    public String getNewstitle() {
        return newstitle;
    }

    public void setNewstitle(String newstitle) {
        this.newstitle = newstitle;
    }

    public String getNewsid() {
        return newsid;
    }

    public void setNewsid(String newsid) {
        this.newsid = newsid;
    }

    public String getNewsicon() {
        return newsicon;
    }

    public void setNewsicon(String newsicon) {
        this.newsicon = newsicon;
    }

    public String getNewssubtitle() {
        return newssubtitle;
    }

    public void setNewssubtitle(String newssubtitle) {
        this.newssubtitle = newssubtitle;
    }

    public String getNewslabel() {
        return newslabel;
    }

    public void setNewslabel(String newslabel) {
        this.newslabel = newslabel;
    }

    public String getComment_count() {
        return comment_count;
    }

    public void setComment_count(String comment_count) {
        this.comment_count = comment_count;
    }

    public String getNewslink() {
        return newslink;
    }

    public void setNewslink(String newslink) {
        this.newslink = newslink;
    }

    public List<NewsImage> getNewspics() {
        return newspics;
    }

    public void setNewspics(List<NewsImage> newspics) {
        this.newspics = newspics;
    }

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

    public class NewsImage {
        private String pic;

        public String getPic() {
            return pic;
        }

        public void setPic(String pic) {
            this.pic = pic;
        }
    }
}
