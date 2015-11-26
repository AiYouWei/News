package org.androidx.frames.entity;

/**
 * 每个选项卡对象
 *
 * @author slioe shu
 */
public class TabItemType implements BaseType {
    private int index; //item的序号，从1开始
    private String text; //item的文字
    private int textSize; //item文字大小
    private int textColor; //item文字颜色
    private String uri; //item对应的UI
    private int redNum; //item右上角的红点(-1:不消失红点，0:显示红点，大于0:在红点中显示数字)
    private int image; // item的图片

    public int getImage() {
        return image;
    }

    public void setImage(int image) {
        this.image = image;
    }

    public int getRedNum() {
        return redNum;
    }

    public void setRedNum(int redNum) {
        this.redNum = redNum;
    }

    public String getUri() {
        return uri;
    }

    public void setUri(String uri) {
        this.uri = uri;
    }

    public int getTextColor() {
        return textColor;
    }

    public void setTextColor(int textColor) {
        this.textColor = textColor;
    }

    public int getTextSize() {
        return textSize;
    }

    public void setTextSize(int textSize) {
        this.textSize = textSize;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public int getIndex() {
        return index;
    }

    public void setIndex(int index) {
        this.index = index;
    }
}
