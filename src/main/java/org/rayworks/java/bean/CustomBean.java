package org.rayworks.java.bean;

import org.rayworks.java.JsonRequired;

import java.util.List;

/**
 * Created by Shirley on 10/11/15.
 */
public class CustomBean extends TestAnnotationBean {
    @JsonRequired
    private List<Integer> ids;

    public List<Integer> getIds() {
        return ids;
    }

    public void setIds(List<Integer> ids) {
        this.ids = ids;
    }
}
