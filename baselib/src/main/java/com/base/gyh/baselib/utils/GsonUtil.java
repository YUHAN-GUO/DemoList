package com.base.gyh.baselib.utils;

import android.util.Log;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonParser;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

public final class GsonUtil {

    private GsonUtil() {
    }

    private static Gson getGsonObject() {
        Gson gson = new GsonBuilder().serializeNulls().create();
        return gson;
    }

    /**
     * 对象转Gson字符串
     *
     * @param object
     * @return
     */
    public static <T extends Object> String toJson(T object) {
        Gson gson = getGsonObject();
        return gson.toJson(object);
    }

    /**
     * Gson字符串转可序列化对象
     *
     * @param object
     * @param clazz
     * @return
     */
    public static <T extends Object> T toDuixiang(String object, Class<T> clazz) {
        Gson gson = getGsonObject();
        T result = null;
        try {
            result = gson.fromJson(object, clazz);
        } catch (Exception e) {
            result = null;
            e.printStackTrace();
        }

        return result;
    }

    /**
     * Gson字符串转可序列化对象
     *
     * @param object
     * @return
     */
    public static <T extends Object> T toDuixiang(String object, Type type) {
        Gson gson = getGsonObject();

        T result = null;
        try {
            result = gson.fromJson(object, type);
        } catch (Exception e) {
            result = null;
            e.printStackTrace();
        }

        return result;
    }
    public static<T> List<T> Json2Array(Class<T> classz, String s){
        JsonParser jsonParser = new JsonParser();
        JsonArray jsonElements = jsonParser.parse(s).getAsJsonArray();//获取JsonArray对象
        List<T> beans = new ArrayList<>();
        for (JsonElement bean : jsonElements) {
            T bean1 = Json2Result(classz,bean);//解析
            beans.add(bean1);
        }
        return beans;
    }
    public static <T extends Object> T deserBequiet(String object, Class<T> clazz) {
        Gson gson = getGsonObject();

        T result;
        try {
            result = gson.fromJson(object, clazz);
        } catch (Exception e) {
            result = null;
        }

        return result;
    }

    public static <T> T Json2Result(Class<T> class1, String s) {

        T result;
        try {
            result = new Gson().fromJson(s, class1);
            Log.d(class1.toString() + "------Json Msg", s);
        } catch (Exception e) {
            result = null;
            Log.e(class1.toString() + "------Json Error", s);
        }

        return result;
    }
    public static <T> T Json2Result(Class<T> class1, JsonElement s) {
        T result;
        try {
            result = new Gson().fromJson(s, class1);
        } catch (Exception e) {
            result = null;
            Log.e(class1.toString() + "------Json Error", e.getMessage());
        }

        return result;
    }


}
