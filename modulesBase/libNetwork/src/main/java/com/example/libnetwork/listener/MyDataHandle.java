package com.example.libnetwork.listener;
//主要用于封装与网络请求数据处理相关的一些关键信息，
//作为一个数据传递的载体，方便在不同的网络请求处理环节中传递必要的数据，
public class MyDataHandle {

    //用于接收网络请求处理结果的监听器对象，外部类可以定义具体的处理逻辑，
    public MyDataListener mListener = null;

    // 期望将网络请求返回的数据转换为的目标Java类类型，
    public Class<?> mClass = null;

    //表示数据源相关的信息，例如可能是请求的URL地址、数据来源的标识等，
    //用于记录数据是从哪里获取的，
    public String mSource = null;

    //构造函数,用于初始化监听器对象，
    //通常在只需要关注请求处理结果回调（成功或失败回调），而不需要指定数据类型转换和数据源相关信息时使用。
    public MyDataHandle(MyDataListener listener) {
        this.mListener = listener;
    }

    //构造函数，指定期望的数据类型，
    //适用于需要将网络请求返回的数据转换为特定Java对象的场景，

    public MyDataHandle(MyDataListener listener, Class<?> clazz) {
        this.mListener = listener;
        this.mClass = clazz;
    }

    //构造函数，用于设置数据源相关的信息，
    //在需要记录数据来源并且根据数据源进行相应处理的场景中使用。
    public MyDataHandle(MyDataListener listener, String source) {
        this.mListener = listener;
        this.mSource = source;
    }
}