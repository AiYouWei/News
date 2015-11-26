package org.androidx.frames.entity;

/**
 * 多Item布局的Adapter的类型
 *
 * @author slioe shu
 */
public abstract class BaseMultiType implements BaseType {
    private int viewType; // 控件类型

    public BaseMultiType(int viewType) {
        this.viewType = viewType;
    }

    public int getViewType() {
        return viewType;
    }

    public void setViewType(int viewType) {
        this.viewType = viewType;
    }
}
