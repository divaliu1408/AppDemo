package com.diva.appdemo.Utils;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.util.Log;

import com.diva.appdemo.Manager.MyApplication;

import java.io.File;
import java.util.HashSet;
import java.util.concurrent.TimeUnit;

import okhttp3.Cache;
import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
/**
 * Created by 刘迪 on 2018/9/21 14:58.
 * 邮箱：divaliu1408@qq.com
 */

public class OkHttp3Utils {

    private static OkHttpClient mOkHttpClient;
    private static HashSet<String> cookies = new HashSet<>();
    //设置缓存目录
//    private static File cacheDirectory = new File(MyApplication.getInstance().getApplicationContext().getCacheDir().getAbsolutePath(),"MyCache");
//    private static Cache cache = new Cache(cacheDirectory,10*1024*1024);

    /**
     * 获取OkHttpClient对象
     *
     * @return
     */
    public static OkHttpClient getmOkHttpClient(){
        if (mOkHttpClient ==null){
            HttpLoggingInterceptor.Level level = HttpLoggingInterceptor.Level.BODY;
            final  HttpLoggingInterceptor loggingInterceptor = new HttpLoggingInterceptor(new HttpLoggingInterceptor.Logger() {
                @Override
                public void log(String message) {
                    Log.d("zcb","OkHttp====Message:"+message);
                }
            });
            loggingInterceptor.setLevel(level);
            mOkHttpClient = new OkHttpClient.Builder()
                    .connectTimeout(10, TimeUnit.SECONDS)
                    .writeTimeout(10,TimeUnit.SECONDS)
                    .readTimeout(10,TimeUnit.SECONDS)
//                    .cache(cache)
                    .addInterceptor(loggingInterceptor)
                    .build();
        }
        return mOkHttpClient;
    }
    /**
     * 判断网络是否可用
     *
     * @param context Context对象
     */
    public static Boolean isNetworkReachable(Context context) {
        ConnectivityManager cm = (ConnectivityManager) context
                .getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo current = cm.getActiveNetworkInfo();
        if (current == null) {
            return false;
        }
        return (current.isAvailable());
    }
}
