package com.example.libnetwork;



import com.example.libnetwork.listener.MyDataHandle;
import com.example.libnetwork.request.MyRequest;
import com.example.libnetwork.request.RequestParams;
import com.example.libnetwork.response.JsonCallback;

import java.security.SecureRandom;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;
import java.util.concurrent.TimeUnit;

import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSession;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;

import okhttp3.Call;
import okhttp3.OkHttpClient;
import okhttp3.Request;

public class MyOkHttpClient {

    // 定义网络请求的超时时间，单位为秒，这里设置为30秒，用于控制连接、读取和写入操作在多长时间内未完成则视为超时。
    private static final int TIME_OUT = 30;
    // 静态的OkHttpClient实例，整个应用中通常共享这一个实例来进行网络请求，节省资源并便于统一配置管理。
    private static OkHttpClient mOkHttpClient;

    // 静态代码块，在类加载时执行，用于初始化OkHttpClient实例，并进行一系列的配置操作。
    static {
        // 创建一个OkHttpClient.Builder对象，通过它来构建和配置OkHttpClient实例，方便链式调用各种配置方法。
        OkHttpClient.Builder mOkHttpClientBuilder = new OkHttpClient.Builder();
        // 设置连接超时时间，当与服务器建立连接的操作在指定的TIME_OUT秒内未能完成时，就会触发超时异常。
        mOkHttpClientBuilder.connectTimeout(TIME_OUT, TimeUnit.SECONDS);
        // 设置读取超时时间，在读取服务器响应数据的过程中，如果超过TIME_OUT秒还未读完，就会出现超时情况。
        mOkHttpClientBuilder.readTimeout(TIME_OUT, TimeUnit.SECONDS);
        // 设置写入超时时间，当向服务器发送请求数据时，如果超过TIME_OUT秒还未发送完成，也会超时。
        mOkHttpClientBuilder.writeTimeout(TIME_OUT, TimeUnit.SECONDS);
        // 设置是否支持重定向，这里设置为true，表示当服务器返回重定向响应时，客户端会自动跟随重定向地址继续请求，
        // 若设置为false，则不会自动重定向，需要开发者手动处理重定向相关的逻辑。
        mOkHttpClientBuilder.followRedirects(true);

        mOkHttpClient = mOkHttpClientBuilder.build();
    }
    // 获取配置好的OkHttpClient实例的方法，外部其他类可以通过调用这个方法获取到已经完成各种配置的OkHttpClient对象，
    // 用于进一步的网络请求操作或者其他基于OkHttpClient的扩展功能开发。
    public static OkHttpClient getOkHttpClient() {
        return mOkHttpClient;
    }


    public static void get(String url, RequestParams params, MyDataHandle handle) {
        // 使用配置好的OkHttpClient创建一个Call对象，Call对象代表一个网络请求的调用，可以用于发起同步或异步请求，
        Call call = mOkHttpClient.newCall(MyRequest.GetRequest(url, params));
        // 将请求加入队列进行异步请求，并传入JsonCallback对象来处理请求结果的回调，
        call.enqueue(new JsonCallback(handle));
    }

    public static Call get(Request request, MyDataHandle handle) {
        Call call = mOkHttpClient.newCall(request);
        call.enqueue(new JsonCallback(handle));
        return call;
    }

    public static Call post(Request request, MyDataHandle handle) {
        Call call = mOkHttpClient.newCall(request);
        call.enqueue(new JsonCallback(handle));
        return call;
    }

}