package com.xuptggg.libnetwork;

import javax.net.ssl.*;
import java.security.KeyStore;
import java.security.cert.Certificate;
import java.security.cert.CertificateException;
import java.security.cert.CertificateFactory;
import java.security.cert.X509Certificate;
import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;

public class MixedTrustManager implements X509TrustManager {
    private X509TrustManager defaultTrustManager;
    private X509TrustManager customTrustManager;

    public MixedTrustManager(InputStream[] certificates) throws Exception {
        // 加载系统默认的 TrustManager
        TrustManagerFactory defaultTmf = TrustManagerFactory.getInstance(TrustManagerFactory.getDefaultAlgorithm());
        defaultTmf.init((KeyStore) null);
        defaultTrustManager = (X509TrustManager) defaultTmf.getTrustManagers()[0];
        // 加载自定义的 TrustManager
        if (certificates != null && certificates.length > 0) {
            CertificateFactory cf = CertificateFactory.getInstance("X.509");
            KeyStore keyStore = KeyStore.getInstance(KeyStore.getDefaultType());
            keyStore.load(null, null);
            for (int i = 0; i < certificates.length; i++) {
                Certificate certificate = cf.generateCertificate(certificates[i]);
                keyStore.setCertificateEntry("certificate-" + i, certificate);
                certificates[i].close();
            }
            TrustManagerFactory customTmf = TrustManagerFactory.getInstance(TrustManagerFactory.getDefaultAlgorithm());
            customTmf.init(keyStore);
            customTrustManager = (X509TrustManager) customTmf.getTrustManagers()[0];
        }
    }

    @Override
    public void checkClientTrusted(X509Certificate[] chain, String authType) throws CertificateException {

    }

    @Override
    public void checkServerTrusted(X509Certificate[] chain, String authType) throws CertificateException {
        try {
            // 先尝试使用系统默认的 TrustManager 验证
            defaultTrustManager.checkServerTrusted(chain, authType);
        } catch (CertificateException e) {
            // 如果系统默认的 TrustManager 验证失败，再尝试使用自定义的 TrustManager 验证
            if (customTrustManager != null) {
                customTrustManager.checkServerTrusted(chain, authType);
            } else {
                throw e;
            }
        }
    }

    @Override
    public X509Certificate[] getAcceptedIssuers() {
        return new X509Certificate[0];
    }
}