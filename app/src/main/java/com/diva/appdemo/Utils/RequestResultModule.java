package com.diva.appdemo.Utils;

import com.diva.appdemo.Entity.HospitalBean;

import rx.Observer;

/**
 * Created by 刘迪 on 2018/9/21 15:48.
 * 邮箱：divaliu1408@qq.com
 */

public interface RequestResultModule<T> {
    void getHospitals(String longitude, String latitude, String username, OnCallBackListener<T> onCallBackListener);
}
