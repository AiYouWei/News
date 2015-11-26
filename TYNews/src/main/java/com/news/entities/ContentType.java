package com.news.entities;

import org.androidx.frames.entity.BaseType;

import java.util.List;

/**
 * @author slioe shu
 */
public class ContentType implements BaseType {
    private String pageNo;
    private String pageName;
    private List<Pages> articles;

    public String getPageNo() {
        return pageNo;
    }

    public void setPageNo(String pageNo) {
        this.pageNo = pageNo;
    }

    public String getPageName() {
        return pageName;
    }

    public void setPageName(String pageName) {
        this.pageName = pageName;
    }

    public List<Pages> getArticles() {
        return articles;
    }

    public void setArticles(List<Pages> articles) {
        this.articles = articles;
    }

    public class Pages implements BaseType {
        private String id;
        private String title;
        private String ccount;
        private String img;
        private String subtitle;

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

        public String getCcount() {
            return ccount;
        }

        public void setCcount(String ccount) {
            this.ccount = ccount;
        }

        public String getImg() {
            return img;
        }

        public void setImg(String img) {
            this.img = img;
        }

        public String getSubtitle() {
            return subtitle;
        }

        public void setSubtitle(String subtitle) {
            this.subtitle = subtitle;
        }
    }
}

