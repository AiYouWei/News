package org.androidx.frames.entity;

/**
 * 分享的信息
 *
 * @author slioe shu
 */
public class ShareInfoType implements BaseType {
    /**
     * 分享的标题
     */
    private String title;
    /**
     * 分享的内容
     */
    private String content;
    /**
     * 分享的图片的本地地址
     */
    private String imagePath;
    /**
     * 分享的图片的网络地址
     */
    private String iamgeUrl;
    /**
     * 分享的图片的资源ID
     */
    private int imageResID;
    /**
     * 分享的信息的网络地址
     */
    private String url;
    /**
     * 分享成功后上报的地址
     */
    private String reportUrl;

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

    public String getImagePath() {
        return imagePath;
    }

    public void setImagePath(String imagePath) {
        this.imagePath = imagePath;
    }

    public String getIamgeUrl() {
        return iamgeUrl;
    }

    public void setIamgeUrl(String iamgeUrl) {
        this.iamgeUrl = iamgeUrl;
    }

    public int getImageResID() {
        return imageResID;
    }

    public void setImageResID(int imageResID) {
        this.imageResID = imageResID;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getReportUrl() {
        return reportUrl;
    }

    public void setReportUrl(String reportUrl) {
        this.reportUrl = reportUrl;
    }
}
