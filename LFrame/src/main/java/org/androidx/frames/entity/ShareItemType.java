package org.androidx.frames.entity;

/**
 * 分享的Item
 *
 * @author slioe shu
 */
public class ShareItemType implements BaseType {
    private int icon;
    private String name;

    public int getIcon() {
        return icon;
    }

    public void setIcon(int icon) {
        this.icon = icon;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
