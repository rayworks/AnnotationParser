AnnotationParser
----------------

A custom annotation supported based on Gson. It validates the required fields marked with the annotation and does the strict parsing.
Inspired by http://stackoverflow.com/questions/14242236/let-gson-throw-exceptions-on-wrong-types/14245807#14245807

Usage:

* Add the specified annotation "JsonRequired" for required field :

```java
public class TestAnnotationBean {
    @JsonRequired
    public String foo;

    public String bar;
}
```


* Register the deserializer :

```java 
gson = new GsonBuilder()
     .registerTypeHierarchyAdapter(TestAnnotationBean.class,
             new AnnotatedDeserializer<TestAnnotationBean>())
     .create();
```


* Parse String as an object.
