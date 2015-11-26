package com.news.entities;

import org.androidx.frames.entity.BaseType;

import java.util.ArrayList;
import java.util.List;

/**
 * @author slioe shu
 */
public class NewsNormalType implements BaseType {
    private String newsid;
    private String newstitle;
    private String newssubtitle;
    private String createtime;
    private String newsfrom;
    private String newscontent;
    private String shareurl;
    private String shareimg;
    private ArrayList<Comment> comtlist;
    private String author;
    private Link link;
    private String pagename;
    private String pageno;
    private List<Recommend> recommends;

    public String getNewsid() {
        return newsid;
    }

    public void setNewsid(String newsid) {
        this.newsid = newsid;
    }

    public String getNewstitle() {
        return newstitle;
    }

    public void setNewstitle(String newstitle) {
        this.newstitle = newstitle;
    }

    public String getNewssubtitle() {
        return newssubtitle;
    }

    public void setNewssubtitle(String newssubtitle) {
        this.newssubtitle = newssubtitle;
    }

    public String getCreatetime() {
        return createtime;
    }

    public void setCreatetime(String createtime) {
        this.createtime = createtime;
    }

    public String getNewsfrom() {
        return newsfrom;
    }

    public void setNewsfrom(String newsfrom) {
        this.newsfrom = newsfrom;
    }

    public String getNewscontent() {
        return newscontent;
    }

    public void setNewscontent(String newscontent) {
        this.newscontent = newscontent;
    }

    public String getShareurl() {
        return shareurl;
    }

    public void setShareurl(String shareurl) {
        this.shareurl = shareurl;
    }

    public String getShareimg() {
        return shareimg;
    }

    public void setShareimg(String shareimg) {
        this.shareimg = shareimg;
    }

    public ArrayList<Comment> getComtlist() {
        return comtlist;
    }

    public void setComtlist(ArrayList<Comment> comtlist) {
        this.comtlist = comtlist;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public Link getLink() {
        return link;
    }

    public void setLink(Link link) {
        this.link = link;
    }

    public String getPagename() {
        return pagename;
    }

    public void setPagename(String pagename) {
        this.pagename = pagename;
    }

    public String getPageno() {
        return pageno;
    }

    public void setPageno(String pageno) {
        this.pageno = pageno;
    }

    public List<Recommend> getRecommends() {
        return recommends;
    }

    public void setRecommends(List<Recommend> recommends) {
        this.recommends = recommends;
    }

    public class Recommend implements BaseType {
        private String recommendid;
        private String recommendtitle;
        private String recommendsubtitle;
        private String recommendicon;

        public String getRecommendid() {
            return recommendid;
        }

        public void setRecommendid(String recommendid) {
            this.recommendid = recommendid;
        }

        public String getRecommendtitle() {
            return recommendtitle;
        }

        public void setRecommendtitle(String recommendtitle) {
            this.recommendtitle = recommendtitle;
        }

        public String getRecommendsubtitle() {
            return recommendsubtitle;
        }

        public void setRecommendsubtitle(String recommendsubtitle) {
            this.recommendsubtitle = recommendsubtitle;
        }

        public String getRecommendicon() {
            return recommendicon;
        }

        public void setRecommendicon(String recommendicon) {
            this.recommendicon = recommendicon;
        }
    }

    public class Link implements BaseType {
        private String linkimg;
        private String linkurl;

        public String getLinkimg() {
            return linkimg;
        }

        public void setLinkimg(String linkimg) {
            this.linkimg = linkimg;
        }

        public String getLinkurl() {
            return linkurl;
        }

        public void setLinkurl(String linkurl) {
            this.linkurl = linkurl;
        }
    }
}
