package com.enjoyor.healthhouse.net;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.TypeReference;

import org.json.JSONArray;
import org.json.JSONException;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Created by YuanYuan on 2016/4/25.
 */
public class JsonHelper {
    /**
     * 用于将JSON格式的数据解析成目标实体，由于有可能是单一实体，也可能是数据表，故而统一用ArrayList进行处理
     *
     * @param jsonStr 需要解析的JSON数据
     * @param which   需要解析成的目标类
     * @return 目标类的实体队列
     */
    public static <E> ArrayList<E> GetModelFromJson(String jsonStr,
                                                    Class<E> which) {
        ArrayList<E> eList = new ArrayList<E>();
        try {
            JSONArray jsonArr = new JSONArray(jsonStr);
            // 查看JSON中有多少项
            for (int i = 0; i < jsonArr.length(); i++) {
                E e = which.newInstance();
                // 获取此类的全部属性
                Field[] fields = e.getClass().getDeclaredFields();
                for (int j = 0; j < fields.length; j++) {
                    Class<?> fieldType = fields[j].getType();
                    String fieldName = fields[j].getName();

                    if (fieldType == String.class) {
                        fields[j].set(e,
                                jsonArr.getJSONObject(i).getString(fieldName));
                    } else if (fieldType == int.class) {
                        fields[j].set(e,
                                jsonArr.getJSONObject(i).getInt(fieldName));
                    } else if (fieldType == boolean.class) {
                        fields[j].set(e,
                                jsonArr.getJSONObject(i).getBoolean(fieldName));
                    } else {
                        fields[j].set(e, null);
                    }
                }

                eList.add(e);
            }
        } catch (JSONException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (IllegalArgumentException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (InstantiationException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

        return eList;
    }

    /**
     * 用fastjson 将json字符串解析为一个 JavaBean
     *
     * @param jsonString
     * @param cls
     * @return
     */
    public static <T> T getJson(String jsonString, Class<T> cls) {
        T t = null;
        try {
            t = JSON.parseObject(jsonString, cls);
        } catch (Exception e) {
            // TODO: handle exception
            e.printStackTrace();
        }
        return t;
    }

    /**
     * 用fastjson 将json字符串 解析成为一个 List<JavaBean> 及 List<String>
     *
     * @param jsonString
     * @param cls
     * @return
     */
    public static <T> List<T> getArrayJson(String jsonString, Class<T> cls) {
        List<T> list = new ArrayList<T>();
        try {
            list = JSON.parseArray(jsonString, cls);
        } catch (Exception e) {
            // TODO: handle exception
            e.printStackTrace();
        }
        return list;
    }

    /**
     * 用fastjson 将json字符串 解析成为一个 List<JavaBean> 及 List<String>
     *
     * @param jsonString
     * @param
     * @return
     */
    @SuppressWarnings("unchecked")
    public static <T> List<T> getArrayJson(String jsonString) {
        List<T> list = new ArrayList<T>();
        try {
            list = (List<T>) JSON.parseArray(jsonString);
        } catch (Exception e) {
            // TODO: handle exception
        }
        return list;
    }

    /**
     * 用fastjson 将jsonString 解析成 List<Map<String,Object>>
     *
     * @param jsonString
     * @return
     */
    public static List<Map<String, Object>> getListMap(String jsonString) {
        List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
        try {
            // 两种写法
            // list = JSON.parseObject(jsonString, new
            // TypeReference<List<Map<String, Object>>>(){}.getType());

            list = JSON.parseObject(jsonString,
                    new TypeReference<List<Map<String, Object>>>() {
                    });
        } catch (Exception e) {
            // TODO: handle exception
        }
        return list;
    }

}
