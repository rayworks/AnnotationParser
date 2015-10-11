package org.rayworks.java.bean;

import org.rayworks.java.JsonRequired;

/**
 * Created by Shirley on 10/11/15.
 */
public class TestAnnotationBean {
    @JsonRequired
    public String foo;

    public String bar;

    @Override
    public String toString() {
        return "TestAnnotationBean{" +
                "foo='" + foo + '\'' +
                ", bar='" + bar + '\'' +
                '}';
    }
}
