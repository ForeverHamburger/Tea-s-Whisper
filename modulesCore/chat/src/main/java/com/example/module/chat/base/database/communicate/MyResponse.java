package com.example.module.chat.base.database.communicate;

import com.example.module.chat.base.database.BaseResponse;
public class MyResponse extends BaseResponse<Data> {
    // 删除 code、msg、data 字段的定义
    // 直接复用父类 BaseResponse<Data> 的字段

    @Override
    public String toString() {
        return "MyResponse{" +
                "code=" + getCode() +
                ", msg='" + getMsg() + '\'' +
                ", data=" + getData() +
                '}';
    }
}
//public class MyResponse extends BaseResponse<Data> {
////    private int code;
////    private String msg;
//    private Data data;
//
////    public int getCode() {
////        return code;
////    }
////
////    public String getMsg() {
////        return msg;
////    }
//
//    public Data getData() {
//        return data;
//    }
//
////    @Override
////    public String toString() {
////        return "MyResponse{" +
////                "code=" + code +
////                ", msg='" + msg + '\'' +
////                ", data=" + data +
////                '}';
////    }
//}