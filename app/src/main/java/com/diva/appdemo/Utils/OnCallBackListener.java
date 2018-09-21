package com.diva.appdemo.Utils;

/**
 * Created by 刘迪 on 2018/9/21 15:51.
 * 邮箱：divaliu1408@qq.com
 */

public interface OnCallBackListener<T> {
    void onSuccess(T t);
    void onError(Throwable e);
    void onCompleted();
}
