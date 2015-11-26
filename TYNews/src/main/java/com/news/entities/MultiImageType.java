package com.news.entities;

import org.androidx.frames.entity.BaseType;

import java.util.ArrayList;
import java.util.List;

/**
 * @author slioe shu
 */
public class MultiImageType implements BaseType {
    private String newsid;
    private String newstitle;
    private String shareurl;
    private String shareimg;
    private String newssubtitle;
    private List<ImageType> pics;
    private ArrayList<Comment> comtlist;

    public List<ImageType> getPics() {
        return pics;
    }

    public void setPics(List<ImageType> pics) {
        this.pics = pics;
    }

    public ArrayList<Comment> getComtlist() {
        return comtlist;
    }

    public void setComtlist(ArrayList<Comment> comtlist) {
        this.comtlist = comtlist;
    }

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

    public String getNewssubtitle() {
        return newssubtitle;
    }

    public void setNewssubtitle(String newssubtitle) {
        this.newssubtitle = newssubtitle;
    }

    public List<ImageType> getImages() {
        return pics;
    }

    public void setImages(List<ImageType> images) {
        this.pics = images;
    }

    public class ImageType implements BaseType {
        private String pic;
        private String info;

        public String getPic() {
            return pic;
        }

        public void setPic(String pic) {
            this.pic = pic;
        }

        public String getInfo() {
            return info;
        }

        public void setInfo(String info) {
            this.info = info;
        }
    }
}
