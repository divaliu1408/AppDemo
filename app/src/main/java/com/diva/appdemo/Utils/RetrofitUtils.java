package com.diva.appdemo.Utils;

import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by 刘迪 on 2018/9/21 14:56.
 * 邮箱：divaliu1408@qq.com
 */

public class RetrofitUtils {

    private static final String NETWORK_HOST="http://121.42.150.214:8777";

    private static Retrofit mRetrofit;
    private static OkHttpClient mOkHttpClient;

    protected static Retrofit getRetrofit(){
        if (mRetrofit == null){
            if (mOkHttpClient == null) {
                mOkHttpClient = OkHttp3Utils.getmOkHttpClient();
            }
            mRetrofit = new Retrofit.Builder()
                    .baseUrl(NETWORK_HOST)
                    .addConverterFactory(GsonConverterFactory.create())
                    .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                    .client(mOkHttpClient)
                    .build();
        }
        return mRetrofit;
    }
}
