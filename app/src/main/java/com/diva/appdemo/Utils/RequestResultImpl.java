package com.diva.appdemo.Utils;

import com.diva.appdemo.Entity.HospitalBean;

import rx.Observer;

/**
 * Created by 刘迪 on 2018/9/21 15:47.
 * 邮箱：divaliu1408@qq.com
 */

public class RequestResultImpl implements RequestResultModule {
    @Override
    public void getHospitals(String longitude, String latitude, String username,final  OnCallBackListener onCallBackListener) {
        Networks.getHospitalList(longitude, latitude, username, new Observer<HospitalBean>() {
            @Override
            public void onCompleted() {
                onCallBackListener.onCompleted();
            }

            @Override
            public void onError(Throwable e) {
                onCallBackListener.onError(e);
            }

            @Override
            public void onNext(HospitalBean hospitalBean) {
                onCallBackListener.onSuccess(hospitalBean);
            }
        });

    }
}
