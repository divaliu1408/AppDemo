package com.diva.appdemo.Service;

import android.app.IntentService;
import android.content.Intent;
import android.support.annotation.Nullable;
import android.util.Log;

/**
 * Created by 刘迪 on 2018/8/9 15:04.
 * 邮箱：divaliu1408@qq.com
 */

public class MyIntentService extends IntentService {
 private static final String TAG="MyIntentService";
    public MyIntentService(){
        super("MyIntentService");//调用父类的有参构造函数
    }

    @Override
    protected void onHandleIntent(@Nullable Intent intent) {
        Log.i(TAG, "onHandleIntent: Thread id is "+Thread.currentThread().getId());
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        Log.i(TAG, "onDestroy: executed");
    }

}
