package com.news.entities;

import org.androidx.frames.entity.BaseType;

import java.util.List;

/**
 * 欢迎页数据类
 *
 * @author slioe shu
 */
public class WelcomeType implements BaseType {
    private String versionName;
    private String appDownUrl;
    private List<Images> ads;

    public String getVersionName() {
        return versionName;
    }

    public void setVersionName(String versionName) {
        this.versionName = versionName;
    }

    public String getAppDownUrl() {
        return appDownUrl;
    }

    public void setAppDownUrl(String appDownUrl) {
        this.appDownUrl = appDownUrl;
    }

    public List<Images> getAds() {
        return ads;
    }

    public void setAds(List<Images> ads) {
        this.ads = ads;
    }

    public class Images implements BaseType {
        private String imgUrl;
        private String clickUrl;

        public String getImgUrl() {
            return imgUrl;
        }

        public void setImgUrl(String imgUrl) {
            this.imgUrl = imgUrl;
        }

        public String getClickUrl() {
            return clickUrl;
        }

        public void setClickUrl(String clickUrl) {
            this.clickUrl = clickUrl;
        }
    }
}
