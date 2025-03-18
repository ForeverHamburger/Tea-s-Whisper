package com.xuptggg.module.login.base;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

public class VerificationRequestManager {
    private static VerificationRequestManager instance;
    private final Map<String, Long> requestMap = new HashMap<>(); // key: 邮箱/手机号, value: 请求时间戳

    public static synchronized VerificationRequestManager getInstance() {
        if (instance == null) {
            instance = new VerificationRequestManager();
        }
        return instance;
    }

    public void markRequested(String key) {
        requestMap.put(key, System.currentTimeMillis() + 5 * 60 * 1000); // 5分钟有效期
    }
    public Boolean addRequested(String key,String data) {
        if(data.equals("验证码发送成功")){
            if (key != null) {
                markRequested(key);
            }
            return true;
        }
        return false;
    }
    public boolean isRequestValid(String key) {
        Long expireTime = requestMap.get(key);
        return expireTime != null && System.currentTimeMillis() < expireTime;
    }

    public void clearExpiredRequests() {
        long currentTime = System.currentTimeMillis();
        requestMap.entrySet().removeIf(entry -> currentTime > entry.getValue());
    }
}