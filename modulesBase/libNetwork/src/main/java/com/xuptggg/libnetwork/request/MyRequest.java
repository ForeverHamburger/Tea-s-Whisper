package com.xuptggg.libnetwork.request;

import android.util.Log;

import java.io.File;
import java.util.Map;

import okhttp3.FormBody;
import okhttp3.Headers;
import okhttp3.HttpUrl;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.Request;
import okhttp3.RequestBody;

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
//        StringBuilder stringBuilder = new StringBuilder(url).append("?");
//        if (params!= null) {
//            // 遍历params中的键值对，将它们拼接到stringBuilder中，构建出带查询参数的URL字符串
//            for (Map.Entry<String, String> entry : params.urlParams.entrySet()) {
//                stringBuilder.append(entry.getKey()).append("=").append(entry.getValue());
//            }
//        }
        // 使用HttpUrl.Builder构建URL，自动处理查询参数和编码
        HttpUrl.Builder urlBuilder = HttpUrl.parse(url).newBuilder();
        if (params != null) {
            for (Map.Entry<String, String> entry : params.urlParams.entrySet()) {
                urlBuilder.addQueryParameter(entry.getKey(), entry.getValue());
            }
        }
        HttpUrl httpUrl = urlBuilder.build();
        Headers.Builder mHeadersBuilder = new Headers.Builder();
        if (headers!= null) {
            // 遍历headers中的键值对，将它们添加到Headers.Builder中，以此构建请求头
            for (Map.Entry<String, String> entry : headers.urlParams.entrySet()) {
                mHeadersBuilder.add(entry.getKey(), entry.getValue());
            }
        }

        // 通过Request.Builder构建最终的Request对象，设置拼接好的带查询参数的URL、添加构建好的请求头，指定请求方法为GET，然后生成并返回
        return new Request.Builder()
                .url(httpUrl)
//                .url(stringBuilder.toString())
                .headers(mHeadersBuilder.build())
                .get()
                .build();
    }
    private static final MediaType FILE_TYPE = MediaType.parse("application/octet-stream");
    public static Request MultiPostRequest(String url,RequestParams params){
        MultipartBody.Builder requestBuilder = new MultipartBody.Builder();
        requestBuilder.setType(MultipartBody.FORM);
        if(params != null){
            for (Map.Entry<String, Object> entry : params.fileParams.entrySet()) {
                if (entry.getValue() instanceof File) {
                    //手动构建 Content-Disposition 头部，但未包含 filename 参数（仅设置 name）。
                    requestBuilder.addPart(Headers.of("Content-Disposition","form-data; name=\"" + entry.getKey() + "\""),
                            RequestBody.create(FILE_TYPE, (File) entry.getValue()));
                }else if (entry.getValue() instanceof String) {
                    requestBuilder.addPart(Headers.of("Content-Disposition", "form-data; name=\"" + entry.getKey() + "\""),
                            RequestBody.create(null, (String) entry.getValue()));
                }
            }
        }
        return new Request.Builder()
                .url(url)
                .post(requestBuilder.build())
                .build();
    }
    //--------------------------------------------

    public static Request TestMultiPostRequest(String url, RequestParams params, RequestParams headers) {
        MultipartBody.Builder bodyBuilder = new MultipartBody.Builder()
                .setType(MultipartBody.FORM);

        if (params != null) {
            processParams(bodyBuilder, params);
        }
        // 创建一个Headers.Builder对象，用于构建请求头内容
        Headers.Builder mHeadersBuilder = new Headers.Builder();
        if (headers!= null) {
            // 遍历headers中的键值对，将它们添加到Headers.Builder中，以此构建请求头
            for (Map.Entry<String, String> entry : headers.urlParams.entrySet()) {
                mHeadersBuilder.add(entry.getKey(), entry.getValue());
            }
        }
        return new Request.Builder()
                .url(url)
                .headers(mHeadersBuilder.build())
                .post(bodyBuilder.build())
                .build();
    }
    private static void processParams(MultipartBody.Builder builder, RequestParams params) {
        for (Map.Entry<String, Object> entry : params.fileParams.entrySet()) {
            String key = entry.getKey();
            Object value = entry.getValue();

            if (value instanceof File) {
                addFilePart(builder, key, (File) value);
            } else if (value instanceof String) {
                addTextPart(builder, key, (String) value);
            }
        }
    }

    private static void addFilePart(MultipartBody.Builder builder, String name, File file) {
        // 安全检查
        if (!file.exists()) {
            throw new IllegalArgumentException("File not exist: " + file.getAbsolutePath());
        }

        // 自动检测MIME类型
        MediaType mediaType = detectMediaType(file);

        builder.addFormDataPart(
                name,
                file.getName(),
                RequestBody.create(file, mediaType)
        );
    }

    private static void addTextPart(MultipartBody.Builder builder, String name, String value) {
        MediaType textType = MediaType.parse("text/plain; charset=utf-8");
        builder.addFormDataPart(
                name,
                null, // 当不需要filename时设为null
                RequestBody.create(value, textType)
        );
    }
    private static MediaType detectMediaType(File file) {
        // 实现示例：根据扩展名判断
        String fileName = file.getName().toLowerCase();
        if (fileName.endsWith(".png")) {
            return MediaType.parse("image/png");
        } else if (fileName.endsWith(".jpg") || fileName.endsWith(".jpeg")) {
            return MediaType.parse("image/jpeg");
        } else if (fileName.endsWith(".pdf")) {
            return MediaType.parse("application/pdf");
        }
        return FILE_TYPE;
    }
//--------------------------------------------------------------
    public static Request FileDownLoadRequest(String url,RequestParams params){
        return GetRequest(url,params);
    }
    public static Request FileDownLoadRequest(String url){
        return GetRequest(url,null);
    }
}