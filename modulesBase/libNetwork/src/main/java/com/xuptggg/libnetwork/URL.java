package com.xuptggg.libnetwork;

public class URL {
    public static final String BASE_URL = "https://42.193.100.201:8080/";
    //注册时验证码
    public static final String LOGIN_CODE_URL = BASE_URL +"api/v1/code";
    //注册
    public static final String LOGIN_SIGNUP_URL = BASE_URL +"api/v1/signup";
    //普通登录
    public static final String LOGIN_LOGIN_URL = BASE_URL +"api/v1/login";
    //验证码登录
    public static final String LOGIN_VERIFY_URL = BASE_URL +"api/v1/loginwithcode";
    //忘记密码
    public static final String LOGIN_FORGET_URL = BASE_URL +"api/v1/findpassword";
    //tea相关信息
    public static final String TEA_DETAIL_URL = BASE_URL + "api/v1/tea/detail";
    //chat聊天
    public static final String CHAT_URL = BASE_URL + "api/v1/text";
    //聊天记录
    public static final String CHAT_HISTORY_URL = BASE_URL + "api/v1/text/history";
    //详细聊天记录
    public static final String CHAT_HISTORYS_URL = BASE_URL + "api/v1/text/histories";
    //帖子广场
    public static final String FORUM_SQUARE_URL = BASE_URL + "api/v1/posts";
    //发布帖子
    public static final String FORUM_PUBLISH_URL = BASE_URL + "api/v1/post";
    //上传图片
    public static final String PICTURE_UPLOAD_URL = BASE_URL + "api/v1/upload";
    //获取主贴信息
    public static final String FORUM_THREAD_URL = BASE_URL + "api/v1/post";
    //获取个人基本信息
    public static final String INDIVIDUAL_GET_URL = BASE_URL + "api/v1/user/info";
    //修改个人信息
    public static final String INDIVIDUAL_EDIT_POST_URL = BASE_URL + "api/v1/user/info";
    //获取用户帖子接口
    public static final String INDIVIDUAL_TABITEM_GETMY_URL = BASE_URL + "api/v1/user/post";
    //获取用户收藏帖子接口
    public static final String INDIVIDUAL_TABITEM_GETCOLLECT_URL = BASE_URL + "api/v1/user/post/collection";
    //获取用户点赞帖子接口
    public static final String INDIVIDUAL_TABITEM_GETVOTE_URL = BASE_URL + "api/v1/user/post/vote";
    //获取子贴信息
    public static final String SEARCH_URL = BASE_URL + "api/v1/search";

}