package com.xuptggg.module.libbase.eventbus;

public class TokenManager {
    private static volatile TokenManager instance;
    private String token;

    public TokenManager() {}

    public static TokenManager getInstance() {
        if (instance == null) {
            synchronized (TokenManager.class) {
                if (instance == null) {
                    instance = new TokenManager();
                }
            }
        }
        return instance;
    }

    public void setToken(String token) {
        this.token = token;
        // 可选：持久化到DataStore
        // DataStoreUtil.saveString("token", token);
    }

    public String getToken() {
        return token;
        // 可选：内存为空时从DataStore加载
        // return token != null ? token : DataStoreUtil.getString("token", "");
    }
}