package com.diva.appdemo.Utils;

import com.diva.appdemo.Entity.HospitalBean;

import retrofit2.http.GET;
import retrofit2.http.Query;
import rx.Observable;
import rx.Observer;
import rx.Scheduler;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by 刘迪 on 2018/9/21 14:55.
 * 邮箱：divaliu1408@qq.com
 */

public class Networks extends RetrofitUtils {

    private static final String TAG = "apiTest";

    public static NetService service = getRetrofit().create(NetService.class);

    public interface NetService{

        //获取医院列表
        @GET("carController/lujingGuiHua")
        Observable<HospitalBean> getHospitalList(@Query("dis_longitude") String longitude,
                                                 @Query("dis_latitude") String latitude,
                                                 @Query("userName") String username );
    }

    public static void getHospitalList(String longitude,String latitude,String username,Observer<HospitalBean> observer){
        setSubscribe(service.getHospitalList(longitude,latitude,username),observer);
    }

    public static <T> void setSubscribe(Observable<T> observable, Observer<T> observer){
       observable.subscribeOn(Schedulers.io())
               .subscribeOn(Schedulers.newThread()) //子线程访问网络
               .observeOn(AndroidSchedulers.mainThread()) //回调到主线程
               .subscribe(observer);
    }
}
