package br.com.rodrigocardoso.utils;

import com.google.gson.Gson;
import spark.ResponseTransformer;

/**
 * Created by rodri on 20/05/2018.
 */
public class JsonUtils {

    public static String toJson(Object obj) {
        Gson gson = new Gson();
        return gson.toJson(obj);
    }

    public static <T> T fromJson(String jsonString, Class<T> clazz) {
        Gson gson = new Gson();
        return clazz.cast(gson.fromJson(jsonString, clazz));
    }

    public static ResponseTransformer json() {
        return JsonUtils::toJson;
    }

}
