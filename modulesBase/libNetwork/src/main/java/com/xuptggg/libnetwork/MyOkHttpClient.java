package com.xuptggg.libnetwork;


import android.util.Log;

import com.xuptggg.libnetwork.listener.MyDataHandle;
import com.xuptggg.libnetwork.request.MyRequest;
import com.xuptggg.libnetwork.request.RequestParams;
import com.xuptggg.libnetwork.response.NewFileCallBack;
import com.xuptggg.libnetwork.response.JsonCallback;
import com.xuptggg.libnetwork.response.FileCallBack;
import com.xuptggg.libnetwork.ssl.MixedTrustManager;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.util.concurrent.TimeUnit;

import javax.net.ssl.SSLContext;
import javax.net.ssl.TrustManager;

import okhttp3.Call;
import okhttp3.OkHttpClient;
import okhttp3.Request;

public class MyOkHttpClient {

    // 定义网络请求的超时时间，单位为秒，这里设置为30秒，用于控制连接、读取和写入操作在多长时间内未完成则视为超时。
    private static final int TIME_OUT = 30;
    // 静态的OkHttpClient实例，整个应用中通常共享这一个实例来进行网络请求，节省资源并便于统一配置管理。
    private static OkHttpClient mOkHttpClient;

    static {
            try {
                // 自定义证书
                String certificateStr = "-----BEGIN CERTIFICATE-----\n" +
                        "MIIDrzCCApcCFCWKkdltRgvja9jEs2Lh/goV9xm6MA0GCSqGSIb3DQEBCwUAMIGT\n" +
                        "MQswCQYDVQQGEwJDTjEQMA4GA1UECAwHU2hhYW54aTERMA8GA1UEBwwIWGlhbllh\n" +
                        "bmcxEzARBgNVBAoMCk15IENvbXBhbnkxFDASBgNVBAsMC0RldmVsb3BtZW50MRIw\n" +
                        "EAYDVQQDDAlsb2NhbGhvc3QxIDAeBgkqhkiG9w0BCQEWETIzMTIwNTUwMDZAcXEu\n" +
                        "Y29tMB4XDTI1MDEyMjA4MDIxMFoXDTI2MDEyMjA4MDIxMFowgZMxCzAJBgNVBAYT\n" +
                        "AkNOMRAwDgYDVQQIDAdTaGFhbnhpMREwDwYDVQQHDAhYaWFuWWFuZzETMBEGA1UE\n" +
                        "CgwKTXkgQ29tcGFueTEUMBIGA1UECwwLRGV2ZWxvcG1lbnQxEjAQBgNVBAMMCWxv\n" +
                        "Y2FsaG9zdDEgMB4GCSqGSIb3DQEJARYRMjMxMjA1NTAwNkBxcS5jb20wggEiMA0G\n" +
                        "CSqGSIb3DQEBAQUAA4IBDwAwggEKAoIBAQCkUMalu7n48GFrX218CuBbMPotGVok\n" +
                        "wQry1iYBN25+3fSDJrGy5DVYLO4cHFXWv0iEv6LlwybgeKhVjuMF3XBuhf6LBsv0\n" +
                        "rOO5WWJ+N9A9Mmby4XjpyEavYUO3WpPbW6DpwLghE2o4HrCC3dCybiZUpN5jBHKn\n" +
                        "ghJd/hsBR4D8tTckr2yLZ5f3LK0iomhiIQBYe1wDF+qswGGDf3UkxzhPA8ct0nvQ\n" +
                        "aZDXelRwXL/81X1DmP2VYOQSlgAtI1/ELviTizevmKpuzIZt+pnAcWEerpsF0a+U\n" +
                        "NcZxEQQBrJCQozKbbIJsz16yqmMKyjtfCxCyFuZl/FfjTP/xhwOo6DYxAgMBAAEw\n" +
                        "DQYJKoZIhvcNAQELBQADggEBAHAEtkQ7veZrZEeJ+ABYLbL9YNycMvDEFYlmN/fU\n" +
                        "8FxvY1T+Jq2xOApPOFYphRo7kqlNORIXvnS3c2olBWPgSEyIph8ltOn+2j53PZGF\n" +
                        "HBusYTmQxc/SvIE+XncB2l9hBM1BKffoTzm2G1zURICoEc+C+7HAfvW5Lxf2AdDX\n" +
                        "qiGDP3Pu33zFAIuaMQpR3LVOKYpa2LUBMQy38jqSByIbDZ/Di0ywSET7U6x40uaK\n" +
                        "kpb/Gu//7ffPjKzcVPDk4IsGierndSEon/JhtK5t5de/Qudq7VJNhh1T/zmmO31u\n" +
                        "2oEODfz8+nkyPWexdwrH1hSZp4IxvZB0fnl2MonoZ6kyjR4=\n" +
                        "-----END CERTIFICATE-----";
                ByteArrayInputStream certificateStream = new ByteArrayInputStream(certificateStr.getBytes());

                // 创建混合 TrustManager
                MixedTrustManager mixedTrustManager = new MixedTrustManager(new InputStream[]{certificateStream});

                // 创建 SSLContext
                SSLContext sslContext = SSLContext.getInstance("TLS");
                sslContext.init(null, new TrustManager[]{mixedTrustManager}, null);

                // 配置 OkHttpClient
                OkHttpClient.Builder builder = new OkHttpClient.Builder()
                        .connectTimeout(TIME_OUT, TimeUnit.SECONDS)
                        .readTimeout(TIME_OUT, TimeUnit.SECONDS)
                        .writeTimeout(TIME_OUT, TimeUnit.SECONDS)
                        .followRedirects(true)
                        .sslSocketFactory(sslContext.getSocketFactory(), mixedTrustManager)
                        .hostnameVerifier((hostname, session) -> true);

                mOkHttpClient = builder.build();
            } catch (Exception e) {
                throw new RuntimeException("初始化 OkHttpClient 时发生错误", e);
            }

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
        Log.d("TAG", "Final URL: " + request.url().toString());
        Call call = mOkHttpClient.newCall(request);
        call.enqueue(new JsonCallback(handle));
        return call;
    }

    public static Call post(Request request, MyDataHandle handle) {
        Call call = mOkHttpClient.newCall(request);
        call.enqueue(new JsonCallback(handle));
        return call;
    }
    public static Call downloadFile(Request request, MyDataHandle handle) {
        Call call = mOkHttpClient.newCall(request);
        call.enqueue(new FileCallBack(handle));
        return call;
    }
    public static Call TestFile(Request request, MyDataHandle handle) {
        Call call = mOkHttpClient.newCall(request);
        call.enqueue(new NewFileCallBack(handle));
        return call;
    }
}