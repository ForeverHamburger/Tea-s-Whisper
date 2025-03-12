package com.xuptggg.libnetwork;

public class URL {
//    public static final String LOGIN_BASE_URL = "https://221.181.185.242:18080/";
    public static final String LOGIN_BASE_URL = "https://42.193.100.201:8080/";
    //注册时验证码
    public static final String LOGIN_CODE_URL = LOGIN_BASE_URL +"api/v1/code";
    //注册
    public static final String LOGIN_SIGNUP_URL = LOGIN_BASE_URL +"api/v1/signup";
    //普通登录
    public static final String LOGIN_LOGIN_URL = LOGIN_BASE_URL +"api/v1/login";
    //验证码登录
    public static final String LOGIN_VERIFY_URL = LOGIN_BASE_URL +"api/v1/loginwithcode";
    //忘记密码时验证码
    public static final String LOGIN_FORGET_CODE_URL = LOGIN_BASE_URL +"api/v1/userisexit";
    //忘记密码
    public static final String LOGIN_FORGET_URL = LOGIN_BASE_URL +"api/v1/findpassword";

    public static final String TEA_DETAIL_URL = LOGIN_BASE_URL + "api/v1/tea/detail";

    public static final String CHAT_URL = LOGIN_BASE_URL + "api/v1/text";
    public static final String CHAT_HISTORY_URL = LOGIN_BASE_URL + "api/v1/text/history";
    public static final String CHAT_HISTORYS_URL = LOGIN_BASE_URL + "api/v1/text/histories";


}
