package com.news.entities;

import org.androidx.frames.entity.BaseType;

import java.util.List;

/**
 * @author slioe shu
 */
public class SingleImageType implements BaseType {
    private String newsid;
    private String newstitle;
    private String newssubtitle;
    private String newsfrom;
    private String author;
    private String newscontent;
    private String shareurl;
    private String shareimg;
    private String createtime;
    private String pageno;
    private String pagename;
    private Link link;
    private List<Recommend> recommends;
    private List<Comment> comtlist;

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

    public String getNewsfrom() {
        return newsfrom;
    }

    public void setNewsfrom(String newsfrom) {
        this.newsfrom = newsfrom;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
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

    public String getCreatetime() {
        return createtime;
    }

    public void setCreatetime(String createtime) {
        this.createtime = createtime;
    }

    public String getPageno() {
        return pageno;
    }

    public void setPageno(String pageno) {
        this.pageno = pageno;
    }

    public String getPagename() {
        return pagename;
    }

    public void setPagename(String pagename) {
        this.pagename = pagename;
    }

    public Link getLink() {
        return link;
    }

    public void setLink(Link link) {
        this.link = link;
    }

    public List<Recommend> getRecommends() {
        return recommends;
    }

    public void setRecommends(List<Recommend> recommends) {
        this.recommends = recommends;
    }

    public List<Comment> getComtlist() {
        return comtlist;
    }

    public void setComtlist(List<Comment> comtlist) {
        this.comtlist = comtlist;
    }

    private class Link {
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

    private class Recommend {
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

    private class Comment {
        private String comtid;
        private String comtusername;
        private String comtuserheadimg;
        private String comttime;
        private String comtpraise;
        private List<ChildCmt> childcomtlist;

        public String getComtid() {
            return comtid;
        }

        public void setComtid(String comtid) {
            this.comtid = comtid;
        }

        public String getComtusername() {
            return comtusername;
        }

        public void setComtusername(String comtusername) {
            this.comtusername = comtusername;
        }

        public String getComtuserheadimg() {
            return comtuserheadimg;
        }

        public void setComtuserheadimg(String comtuserheadimg) {
            this.comtuserheadimg = comtuserheadimg;
        }

        public String getComttime() {
            return comttime;
        }

        public void setComttime(String comttime) {
            this.comttime = comttime;
        }

        public String getComtpraise() {
            return comtpraise;
        }

        public void setComtpraise(String comtpraise) {
            this.comtpraise = comtpraise;
        }

        public List<ChildCmt> getChildcomtlist() {
            return childcomtlist;
        }

        public void setChildcomtlist(List<ChildCmt> childcomtlist) {
            this.childcomtlist = childcomtlist;
        }
    }

    private class ChildCmt {
        private String comtid;
        private String comtuserid;
        private String comtuserheadimg;
        private String comtusername;
        private String comtcontent;
        private String comttime;

        public String getComtid() {
            return comtid;
        }

        public void setComtid(String comtid) {
            this.comtid = comtid;
        }

        public String getComtuserid() {
            return comtuserid;
        }

        public void setComtuserid(String comtuserid) {
            this.comtuserid = comtuserid;
        }

        public String getComtuserheadimg() {
            return comtuserheadimg;
        }

        public void setComtuserheadimg(String comtuserheadimg) {
            this.comtuserheadimg = comtuserheadimg;
        }

        public String getComtusername() {
            return comtusername;
        }

        public void setComtusername(String comtusername) {
            this.comtusername = comtusername;
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
}
