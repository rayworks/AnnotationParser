package org.rayworks.java.bean;

import org.rayworks.java.JsonRequired;

/**
 * Created by Shirley on 10/11/15.
 */
public class ChildBean extends TestAnnotationBean {
    @JsonRequired
    private Integer tag;

    public Integer getTag() {
        return tag;
    }

    public void setTag(Integer tag) {
        this.tag = tag;
    }

    @Override
    public String toString() {
        return super.toString() + " | ChildBean{" +
                "tag=" + tag +
                '}';
    }
}
