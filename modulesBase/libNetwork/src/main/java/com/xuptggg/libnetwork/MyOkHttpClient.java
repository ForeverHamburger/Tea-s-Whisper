package com.xuptggg.libnetwork;


import com.xuptggg.libnetwork.listener.MyDataHandle;
import com.xuptggg.libnetwork.request.MyRequest;
import com.xuptggg.libnetwork.request.RequestParams;
import com.xuptggg.libnetwork.response.JsonCallback;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.security.KeyStore;
import java.security.SecureRandom;
import java.security.cert.Certificate;
import java.security.cert.CertificateException;
import java.security.cert.CertificateFactory;
import java.security.cert.X509Certificate;
import java.util.concurrent.TimeUnit;

import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSession;
import javax.net.ssl.SSLSocketFactory;
import javax.net.ssl.TrustManager;
import javax.net.ssl.TrustManagerFactory;
import javax.net.ssl.X509TrustManager;

import okhttp3.Call;
import okhttp3.OkHttpClient;
import okhttp3.Request;

public class MyOkHttpClient {

    // 定义网络请求的超时时间，单位为秒，这里设置为30秒，用于控制连接、读取和写入操作在多长时间内未完成则视为超时。
    private static final int TIME_OUT = 30;
    // 静态的OkHttpClient实例，整个应用中通常共享这一个实例来进行网络请求，节省资源并便于统一配置管理。
    private static OkHttpClient mOkHttpClient;

//    // 静态代码块，在类加载时执行，用于初始化OkHttpClient实例，并进行一系列的配置操作。
//    static {
//        // 创建一个OkHttpClient.Builder对象，通过它来构建和配置OkHttpClient实例，方便链式调用各种配置方法。
//        OkHttpClient.Builder mOkHttpClientBuilder = new OkHttpClient.Builder();
//        // 设置连接超时时间，当与服务器建立连接的操作在指定的TIME_OUT秒内未能完成时，就会触发超时异常。
//        mOkHttpClientBuilder.connectTimeout(TIME_OUT, TimeUnit.SECONDS);
//        // 设置读取超时时间，在读取服务器响应数据的过程中，如果超过TIME_OUT秒还未读完，就会出现超时情况。
//        mOkHttpClientBuilder.readTimeout(TIME_OUT, TimeUnit.SECONDS);
//        // 设置写入超时时间，当向服务器发送请求数据时，如果超过TIME_OUT秒还未发送完成，也会超时。
//        mOkHttpClientBuilder.writeTimeout(TIME_OUT, TimeUnit.SECONDS);
//        // 设置是否支持重定向，这里设置为true，表示当服务器返回重定向响应时，客户端会自动跟随重定向地址继续请求，
//        // 若设置为false，则不会自动重定向，需要开发者手动处理重定向相关的逻辑。
//        mOkHttpClientBuilder.followRedirects(true);
//
//        mOkHttpClient = mOkHttpClientBuilder.build();
//    }
    static {
        //-------------------------------------------------------
        try {
            // 这里假设你已经有一个表示证书的字符串
            // 注意：实际使用时请替换为你真实的证书字符串
            String certificateStr = "-----BEGIN CERTIFICATE-----\n" +
                    "MIIDrzCCApcCFCWKkdltRgvja9jEs2Lh/goV9xm6MA0GCSqGSIb3DQEBCwUAMIGT\n" +
                    "MQswCQYDVQQGEwJDTjEQMA4GA1UECAwHU2hhYW54aTERMA8GA1UEBwwIWGlhbllh\n" +
                    "bmcxEzARBgNVBAoMCk15IENvbXBhbnkxFDASBgNVBAsMC0RldmVsb3BtZW50MRIw\n" +
                    "EAYDVQQDDAlsb2NhbGhvc3QxIDAeBgkqhkiG9w0BCQEWETIzMTIwNTUwMDZAcXEu\n" +
                    "Y29tMB4XDTI1MDEyMjA0MDIxMFoXDTI2MDEyMjA0MDIxMFowgZMxCzAJBgNVBAYT\n" +
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

            // 创建一个 ByteArrayInputStream 来包装字符串的字节数组
            ByteArrayInputStream inputStream = new ByteArrayInputStream(certificateStr.getBytes());

            // 创建一个 CertificateFactory，用于生成证书对象
            CertificateFactory cf = CertificateFactory.getInstance("X.509");

            // 从字节输入流中生成证书对象
            Certificate certificate = cf.generateCertificate(inputStream);

            // 创建一个 KeyStore，用于存储证书
            String keyStoreType = KeyStore.getDefaultType();
            KeyStore keyStore = KeyStore.getInstance(keyStoreType);
            keyStore.load(null, null);
            // 将自签名证书添加到 KeyStore 中
            keyStore.setCertificateEntry("certificate", certificate);

            // 创建一个 TrustManagerFactory，用于生成 TrustManager
            String tmfAlgorithm = TrustManagerFactory.getDefaultAlgorithm();
            TrustManagerFactory tmf = TrustManagerFactory.getInstance(tmfAlgorithm);
            tmf.init(keyStore);

            // 获取 TrustManager 数组
            TrustManager[] trustManagers = tmf.getTrustManagers();

            // 创建 SSLContext，并使用自定义的 TrustManager 初始化
            SSLContext sslContext = SSLContext.getInstance("TLS");
            sslContext.init(null, trustManagers, null);
            SSLSocketFactory sslSocketFactory = sslContext.getSocketFactory();
            //-------------------------------------------------------
            // 创建 OkHttpClient.Builder 对象，通过它来构建和配置 OkHttpClient 实例，方便链式调用各种配置方法。
            OkHttpClient.Builder mOkHttpClientBuilder = new OkHttpClient.Builder();
            // 设置连接超时时间，当与服务器建立连接的操作在指定的 TIME_OUT 秒内未能完成时，就会触发超时异常。
            mOkHttpClientBuilder.connectTimeout(TIME_OUT, TimeUnit.SECONDS);
            // 设置读取超时时间，在读取服务器响应数据的过程中，如果超过 TIME_OUT 秒还未读完，就会出现超时情况。
            mOkHttpClientBuilder.readTimeout(TIME_OUT, TimeUnit.SECONDS);
            // 设置写入超时时间，当向服务器发送请求数据时，如果超过 TIME_OUT 秒还未发送完成，也会超时。
            mOkHttpClientBuilder.writeTimeout(TIME_OUT, TimeUnit.SECONDS);
            // 设置是否支持重定向，这里设置为 true，表示当服务器返回重定向响应时，客户端会自动跟随重定向地址继续请求，
            // 若设置为 false，则不会自动重定向，需要开发者手动处理重定向相关的逻辑。
            mOkHttpClientBuilder.followRedirects(true);
            //-------------------------------------------------------
            // 配置 OkHttpClient 使用自定义的 SSLSocketFactory 和 TrustManager
            mOkHttpClientBuilder.sslSocketFactory(sslSocketFactory, (X509TrustManager) trustManagers[0]);
            mOkHttpClientBuilder.hostnameVerifier((hostname, session) -> true);
            //-------------------------------------------------------
            mOkHttpClient = mOkHttpClientBuilder.build();
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