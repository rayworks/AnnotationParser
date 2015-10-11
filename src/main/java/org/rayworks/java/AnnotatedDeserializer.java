package org.rayworks.java;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;

import org.rayworks.java.bean.CustomBean;

import java.lang.reflect.Field;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Created by Shirley on 10/11/15.
 */
public class AnnotatedDeserializer<T> implements JsonDeserializer<T> {
    @Override
    public T deserialize(JsonElement json, Type type, JsonDeserializationContext context) throws JsonParseException {
        T pojo = null;

        if(type.equals(CustomBean.class)){
            CustomBean customBean = new CustomBean();
            JsonObject root = json.getAsJsonObject();

            customBean.foo = root.get("foo").getAsString();
            customBean.bar = root.get("bar").getAsString();

            JsonArray array = root.getAsJsonArray("ids");
            if(array != null){
                List<Integer> ids = new ArrayList<>(array.size());
                for(int i = 0 ; i < array.size(); i++){
                    ids.add(array.get(i).getAsJsonObject().get("id").getAsInt());
                }
                customBean.setIds(ids);
            }

            pojo = (T)customBean;
        }else{
            pojo = new Gson().fromJson(json, type);
        }

        Field[] fields = pojo.getClass().getDeclaredFields();
        for (Field f : fields) {
            if (f.getAnnotation(JsonRequired.class) != null) {
                try {
                    f.setAccessible(true);
                    if (f.get(pojo) == null) {
                        throw new JsonParseException("Missing field in JSON: " + f.getName());
                    }
                } catch (IllegalArgumentException ex) {
                    Logger.getLogger(AnnotatedDeserializer.class.getName()).log(Level.SEVERE, null, ex);
                } catch (IllegalAccessException ex) {
                    Logger.getLogger(AnnotatedDeserializer.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        }
        return pojo;

    }
}
