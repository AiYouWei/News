package org.androidx.frames.entity;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;

/**
 * BaseType的集合
 *
 * @author slioe shu
 */
public class GroupType<T extends BaseType> extends ArrayList<T> implements BaseType, Serializable {
    private static final long serialVersionUID = 1L;
    private String type;

    public GroupType() {
        super();
    }

    public GroupType(Collection<T> collection) {
        super(collection);
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getType() {
        return type;
    }
}
