package org.androidx.frames.entity;

/**
 * 选项卡容器
 *
 * @author slioe shu
 */
public class TabBarType implements BaseType {
    private int hight; //bar的高度(px)
    private String color; //bar的背景颜色
    private int defaultItem; //bar中Item默认选择的序号(从0开始)
    private String lineColor; //bar上方的线条(-1表示不显示)
    private GroupType<TabItemType> items; //bar中item对象

    public int getHight() {
        return hight;
    }

    public void setHight(int hight) {
        this.hight = hight;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public int getDefaultItem() {
        return defaultItem;
    }

    public void setDefaultItem(int defaultItem) {
        this.defaultItem = defaultItem;
    }

    public String getLineColor() {
        return lineColor;
    }

    public void setLineColor(String lineColor) {
        this.lineColor = lineColor;
    }

    public GroupType<TabItemType> getItems() {
        return items;
    }

    public void setItems(GroupType<TabItemType> items) {
        this.items = items;
    }
}
