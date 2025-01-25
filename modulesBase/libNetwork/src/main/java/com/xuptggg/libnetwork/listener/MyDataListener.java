package com.xuptggg.libnetwork.listener;


// 业务逻辑层真正处理的地方，包括java层异常和业务层异常

public interface MyDataListener {


    void onSuccess(Object responseObj);

    void onFailure(Object reasonObj);

}