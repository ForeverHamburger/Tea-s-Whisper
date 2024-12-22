package com.example.libnetwork.request;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

//用于管理网络请求中的参数
public class RequestParams {

    // 这个map用于存放类似查询参数或者表单中的普通文本参数等，方便后续构建请求时使用
    public ConcurrentHashMap<String, String> urlParams = new ConcurrentHashMap<>();

    //空参构造
    public RequestParams() {
    }


    public RequestParams(Map<String, String> source) {
        if (source != null) {
            // 遍历传入的source map中的每一组键值对
            for (Map.Entry<String, String> entry : source.entrySet()) {
                // 将键值对添加到urlParams中，调用下面定义的put方法进行添加
                put(entry.getKey(), entry.getValue());
            }
        }
    }

    public RequestParams(final String key, final String value) {
        HashMap<String, String> temp = new HashMap<String, String>();
        temp.put(key, value);
        new RequestParams(temp);
//        this(new HashMap<String, String>() {
//            {
//                put(key, value);
//            }
//        }
    }

    public void put(String key, String value) {
        if (key != null && value != null) {
            urlParams.put(key, value);
        }
    }

    //判断当前RequestParams对象中是否包含了有效的请求参数，
    public boolean hasParams() {
        return urlParams.size() > 0;
    }
}