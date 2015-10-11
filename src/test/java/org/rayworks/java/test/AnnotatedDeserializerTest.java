package org.rayworks.java.test;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonParseException;

import junit.framework.TestCase;

import org.rayworks.java.AnnotatedDeserializer;
import org.rayworks.java.bean.ChildBean;
import org.rayworks.java.bean.CustomBean;
import org.rayworks.java.bean.TestAnnotationBean;

/**
 * Created by Shirley on 10/11/15.
 * Inspired by http://stackoverflow.com/questions/14242236/let-gson-throw-exceptions-on-wrong-types/14245807#14245807
 */
public class AnnotatedDeserializerTest extends TestCase {

    private Gson gson;

    @Override
    protected void setUp() throws Exception {
        super.setUp();

        gson = new GsonBuilder()
                .registerTypeHierarchyAdapter(TestAnnotationBean.class,
                        new AnnotatedDeserializer<TestAnnotationBean>())
                .create();
    }

    public void testDeserialize() throws Exception {
        String json = "{\"foo\":\"This is foo\",\"bar\":\"this is bar\"}";
        TestAnnotationBean tab = gson.fromJson(json, TestAnnotationBean.class);
        System.out.println(tab.foo);
        System.out.println(tab.bar);

        json = "{\"foo\":\"This is foo\"}";
        tab = gson.fromJson(json, TestAnnotationBean.class);
        System.out.println(tab.foo);
        System.out.println(tab.bar);

        assertTrue(tab.foo != null);

        try {
            json = "{\"bar\":\"This is bar\"}";
            tab = gson.fromJson(json, TestAnnotationBean.class);
            System.out.println(tab.foo);
            System.out.println(tab.bar);

            fail("a parsing exception is expected");
        } catch (JsonParseException e) {
            e.printStackTrace();
            assertTrue(true);
        }

        json = "{\"foo\":\"This is foo\",\"bar\":\"this is bar\", \"tag\":119}";
        tab = gson.fromJson(json, ChildBean.class);
        System.out.println(tab);

        assertTrue(tab != null);

    }

    public void testCustomSerializerFieldMissing() {
        String js = "{\n" +
                "  \"foo\": \"This is foo\",\n" +
                "  \"bar\": \"this is bar\",\n" +
                "  \"tag\": 777\n" +
                "}";

        TestAnnotationBean bean = null;
        try {
            bean = gson.fromJson(js, CustomBean.class);
            fail("Parsing failure expected");
        } catch (JsonParseException e) {
            e.printStackTrace();
        }
        assertTrue(bean == null);
    }

    public void testCustomSerializer() {
        String js = "{\n" +
                "  \"foo\": \"This is foo\",\n" +
                "  \"bar\": \"this is bar\",\n" +
                "  \"ids\": [\n" +
                "    {\n" +
                "      \"id\": 1\n" +
                "    },\n" +
                "    {\n" +
                "      \"id\": 3\n" +
                "    },\n" +
                "    {\n" +
                "      \"id\": 2\n" +
                "    },\n" +
                "    {\n" +
                "      \"id\": 4\n" +
                "    },\n" +
                "    {\n" +
                "      \"id\": 7\n" +
                "    }\n" +
                "  ]\n" +
                "}";
        try {

            CustomBean bean = gson.fromJson(js, CustomBean.class);
            assertTrue(bean != null);
            assertTrue(bean.getIds() != null && bean.getIds().size() == 5);
        } catch (JsonParseException e) {
            e.printStackTrace();
            fail("fail parsing custom bean");
        }
    }
}