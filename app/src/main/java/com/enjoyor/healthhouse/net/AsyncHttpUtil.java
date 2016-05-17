package com.enjoyor.healthhouse.net;

import android.content.Context;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.BinaryHttpResponseHandler;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.loopj.android.http.RequestParams;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.net.HttpURLConnection;
import java.net.URL;
import java.sql.Date;


/**
 * Created by YuanYuan on 2016/4/25.
 */
public class AsyncHttpUtil {
    private static AsyncHttpClient client = new AsyncHttpClient(); // 实例话对象
    private static Context context;

    static {
        client.setTimeout(10000); // 设置链接超时，如果不设置，默认为10s
    }

    /**
     * get 用一个完整url获取一个string对象
     *
     * @param urlString
     * @param params
     * @param res
     */
    public static void get(String urlString, com.lidroid.xutils.http.RequestParams params, AsyncHttpResponseHandler res) {
        client.get(urlString, res);
    }

    /**
     * get url里面带参数
     *
     * @param urlString
     * @param params
     * @param res
     */
    public static void get(String urlString, RequestParams params,
                           AsyncHttpResponseHandler res) {
        client.get(urlString, params, res);
    }

    /**
     * get 不带参数，获取json对象或者数组
     *
     * @param urlString
     * @param res
     */
    public static void get(String urlString, JsonHttpResponseHandler res) {
        client.get(urlString, res);
    }

    /**
     * get 带参数，获取json对象或者数组
     *
     * @param urlString
     * @param params
     * @param res
     */
    public static void get(String urlString, RequestParams params,
                           JsonHttpResponseHandler res) {
        client.get(urlString, params, res);
    }

    /**
     * put 带返回json对象参数 将请求结果由String转换为JSONObject或JSONArray
     *
     * @param urlString 地址
     * @param params    参数数组
     * @param res       将请求结果由String转换为JSONObject或JSONArray
     */
    public static void put(String urlString, RequestParams params,
                           JsonHttpResponseHandler res) {
        client.put(urlString, params, res);
    }

    /**
     * put 一般请求类型 返回string字符串
     *
     * @param urlString 地址
     * @param params    参数数组
     * @param res       接收请求结果，一般重写onSuccess及onFailure接收请求成功或失败的消息，还有onStart，
     *                  onFinish等消息
     */
    public static void put(String urlString, RequestParams params,
                           AsyncHttpResponseHandler res) {
        client.put(urlString, params, res);
    }

    /**
     * delete 返回json对象
     *
     * @param urlString 请求地址
     * @param params    参数数组
     * @param res       将请求结果由String转换为JSONObject或JSONArray
     */
    public static void delete(String urlString, RequestParams params,
                              JsonHttpResponseHandler res) {
        client.delete(urlString, params, res);
    }

    /**
     * delete 请求 一般类型的返回string字符串
     *
     * @param urlString 地址
     * @param params    参数数组
     * @param res       接收请求结果，一般重写onSuccess及onFailure接收请求成功或失败的消息，还有onStart，
     *                  onFinish等消息
     */
    public static void delete(String urlString, RequestParams params,
                              AsyncHttpResponseHandler res) {
        client.delete(urlString, params, res);
    }

    /**
     * get 下载数据使用，会返回byte数据
     *
     * @param uString
     * @param bHandler
     */
    public static void get(String uString, BinaryHttpResponseHandler bHandler) {
        client.get(uString, bHandler);
    }

    public static AsyncHttpClient getClient() {
        return client;
    }

    /**
     * post 带参数，获取json对象或者数组
     *
     * @param url
     * @param params
     * @param res
     */
    public static void post(String url, RequestParams params,
                            JsonHttpResponseHandler res) {
        client.post(url, params, res);
    }

    /**
     * post url里面带参数
     *
     * @param urlString
     * @param params
     * @param res
     */
    public static void post(String urlString, RequestParams params,
                            AsyncHttpResponseHandler res) {
        client.post(urlString, params, res);
    }

    /**
     * 将对象转为参数化
     *
     * @param model
     * @return
     * @throws NoSuchMethodException
     * @throws IllegalAccessException
     * @throws IllegalArgumentException
     * @throws InvocationTargetException
     */
    public static RequestParams SetModel(Object model) {
        RequestParams params = new RequestParams();
        Field[] field = model.getClass().getDeclaredFields(); // 获取实体类的所有属性，返回Field数组
        for (int j = 0; j < field.length; j++) { // 遍历所有属性
            String name = field[j].getName(); // 获取属性的名字
            name = name.substring(0, 1).toUpperCase() + name.substring(1); // 将属性的首字符大写，方便构造get，set方法
            String type = field[j].getGenericType().toString(); // 获取属性的类型
            try {

                if (type.equals("class java.lang.String")) { // 如果type是类类型，则前面包含"class "，后面跟类名
                    Method m = model.getClass().getMethod("get" + name);
                    String value = (String) m.invoke(model); // 调用getter方法获取属性值
                    if (value != null) {
                        params.put(name, value);

                    }
                    continue;
                }
                if (type.equals("class java.lang.Integer")
                        || type.equals("int")) {
                    Method m = model.getClass().getMethod("get" + name);
                    Integer value = (Integer) m.invoke(model);
                    if (value != null) {
                        params.put(name, value);
                    }
                    continue;
                }

                if (type.equals("class java.lang.Short")) {
                    Method m = model.getClass().getMethod("get" + name);
                    Short value = (Short) m.invoke(model);
                    if (value != null) {
                        params.put(name, value);
                    }
                    continue;
                }
                if (type.equals("class java.lang.Double")) {
                    Method m = model.getClass().getMethod("get" + name);
                    Double value = (Double) m.invoke(model);
                    if (value != null) {
                        params.put(name, value);
                    }
                    continue;
                }
                if (type.equals("class java.lang.Boolean")) {
                    Method m = model.getClass().getMethod("get" + name);
                    Boolean value = (Boolean) m.invoke(model);
                    if (value != null) {
                        params.put(name, value);
                    }
                    continue;
                }
                if (type.equals("class java.util.Date")) {
                    Method m = model.getClass().getMethod("get" + name);
                    Date value = (Date) m.invoke(model);
                    if (value != null) {
                        params.put(name, value);
                    }
                    continue;
                }

                if (type.equals("class java.sql.Timestamp")) {
                    Method m = model.getClass().getMethod("get" + name);
                    if (m.invoke(model) != null) {
                        params.put(name, m.invoke(model));
                    }
                    continue;
                }
            } catch (Exception e) {
                // TODO: handle exception
            }
        }
        return params;
    }

    /**
     * 同步发送get请求
     *
     * @param urlStr
     * @return
     */
    public static String SendGet(String urlStr) {
        String result = null;
        URL url = null;
        HttpURLConnection connection = null;
        InputStreamReader in = null;
        try {
            url = new URL(urlStr);
            connection = (HttpURLConnection) url.openConnection();
            in = new InputStreamReader(connection.getInputStream());
            BufferedReader bufferedReader = new BufferedReader(in);
            StringBuffer strBuffer = new StringBuffer();
            String line = null;
            while ((line = bufferedReader.readLine()) != null) {
                strBuffer.append(line);
            }
            result = strBuffer.toString();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (connection != null) {
                connection.disconnect();
            }
            if (in != null) {
                try {
                    in.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

        }
        return result;
    }
}
