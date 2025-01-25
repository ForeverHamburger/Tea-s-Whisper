package com.xuptggg.libnetwork.exception;

//自定义异常类,返回ecode,emsg到业务层
public class MyHttpException extends Exception {
    private static final long serialVersionUID = 1L;
    private int ecode;
    private Object emsg;

    public MyHttpException(int ecode, Object emsg) {
        this.ecode = ecode;
        this.emsg = emsg;
    }

    @Override
    public String toString() {
        return "MyHttpException{" +
                "ecode=" + ecode +
                ", emsg=" + emsg +
                '}';
    }

    public int getEcode() {
        return ecode;
    }

    public Object getEmsg() {
        return emsg;
    }
}