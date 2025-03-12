package com.example.module.chat.base.database.select;

import com.example.module.chat.base.database.BaseResponse;

import java.util.List;
public class HistoryResponse extends BaseResponse<List<DataItem>> {
    // 删除 code、msg、data 字段的定义
    // 直接复用父类 BaseResponse<List<DataItem>> 的字段

    @Override
    public String toString() {
        return "HistoryResponse{" +
                "code=" + getCode() +
                ", msg='" + getMsg() + '\'' +
                ", data=" + getData() +
                '}';
    }
}
//public class HistoryResponse extends BaseResponse<List<DataItem>> {
//    private List<DataItem> data;
//
//
//    public List<DataItem> getData() {
//        return data;
//    }
//
//    public void setData(List<DataItem> data) {
//        this.data = data;
//    }
//
////    private int code;
////    private String msg;
//
//    //    public int getCode() {
////        return code;
////    }
////
////    public String getMsg() {
////        return msg;
////    }
////    public void setCode(int code) {
////        this.code = code;
////    }
////
////    public void setMsg(String msg) {
////        this.msg = msg;
////    }
//}