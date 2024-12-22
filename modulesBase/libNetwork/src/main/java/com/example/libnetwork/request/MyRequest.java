package com.example.libnetwork.request;

import java.util.Map;

import okhttp3.FormBody;
import okhttp3.Headers;
import okhttp3.Request;

public class MyRequest {

    //创建一个不包含headers（请求头）的Post请求对象
    public static Request PostRequest(String url, RequestParams params) {
        return PostRequest(url, params, null);
    }

    //创建一个包含headers（请求头）的Post请求对象
    public static Request PostRequest(String url, RequestParams params, RequestParams headers) {
        // 创建一个FormBody.Builder对象，用于构建POST请求的请求体内容
        FormBody.Builder mFormBodyBuilder = new FormBody.Builder();
        if (params!= null) {
            // 遍历params中的键值对，将它们添加到FormBody.Builder中，以此构建请求体
            for (Map.Entry<String, String> entry : params.urlParams.entrySet()) {
                mFormBodyBuilder.add(entry.getKey(), entry.getValue());
            }
        }

        // 创建一个Headers.Builder对象，用于构建请求头内容
        Headers.Builder mHeadersBuilder = new Headers.Builder();
        if (headers!= null) {
            // 遍历headers中的键值对，将它们添加到Headers.Builder中，以此构建请求头
            for (Map.Entry<String, String> entry : headers.urlParams.entrySet()) {
                mHeadersBuilder.add(entry.getKey(), entry.getValue());
            }
        }

        // 通过Request.Builder构建最终的Request对象，设置请求的URL、添加构建好的请求头和请求体，然后生成并返回
        return new Request.Builder()
                .url(url)
                .headers(mHeadersBuilder.build())
                .post(mFormBodyBuilder.build())
                .build();
    }

    // 创建一个不包含header（请求头）的Get请求对象

    public static Request GetRequest(String url, RequestParams params) {
        return GetRequest(url, params, null);
    }

    //创建一个包含headers（请求头）的Get请求对象

    public static Request GetRequest(String url, RequestParams params, RequestParams headers) {
        StringBuilder stringBuilder = new StringBuilder(url).append("?");
        if (params!= null) {
            // 遍历params中的键值对，将它们拼接到stringBuilder中，构建出带查询参数的URL字符串
            for (Map.Entry<String, String> entry : params.urlParams.entrySet()) {
                stringBuilder.append(entry.getKey()).append("=").append(entry.getValue());
            }
        }

        // 创建一个Headers.Builder对象，用于构建请求头内容
        Headers.Builder mHeadersBuilder = new Headers.Builder();
        if (headers!= null) {
            // 遍历headers中的键值对，将它们添加到Headers.Builder中，以此构建请求头
            for (Map.Entry<String, String> entry : headers.urlParams.entrySet()) {
                mHeadersBuilder.add(entry.getKey(), entry.getValue());
            }
        }

        // 通过Request.Builder构建最终的Request对象，设置拼接好的带查询参数的URL、添加构建好的请求头，指定请求方法为GET，然后生成并返回
        return new Request.Builder()
                .url(stringBuilder.toString())
                .headers(mHeadersBuilder.build())
                .get()
                .build();
    }
}