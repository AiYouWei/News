package org.androidx.libs.share;

import android.graphics.Bitmap;

import java.io.Serializable;

/**
 * 分享的信息
 *
 * @author slioe shu
 */
public class ShareInfoType implements Serializable {
    /**
     * 分享的标题
     */
    private String title;
    /**
     * 分享的内容
     */
    private String content;
    /**
     * 分享的本地图片
     */
    private Bitmap localImage;
    /**
     * 分享的网络图片
     */
    private String netImage;
    /**
     * 分享的信息的网络地址
     */
    private String url;

    /**
     * 分享成功后上报的地址
     */

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

    public Bitmap getLocalImage() {
        return localImage;
    }

    public void setLocalImage(Bitmap localImage) {
        this.localImage = localImage;
    }

    public String getNetImage() {
        return netImage;
    }

    public void setNetImage(String netImage) {
        this.netImage = netImage;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    @Override
    public String toString() {
        return "ShareInfoType{" +
                "title='" + title + '\'' +
                ", content='" + content + '\'' +
                ", localImage=" + localImage +
                ", netImage='" + netImage + '\'' +
                ", url='" + url + '\'' +
                '}';
    }
}
