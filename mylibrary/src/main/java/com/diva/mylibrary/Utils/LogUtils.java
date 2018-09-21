package com.diva.mylibrary.Utils;

import android.util.Log;

import java.util.logging.Level;

/**
 * Created by 刘迪 on 2018/8/21 11:07.
 * 邮箱：divaliu1408@qq.com
 */

public class LogUtils {

    public static final int VERBOSE = 1;
    public static final int DEBUG = 2;
    public static final int INFO = 3;
    public static final int WARN = 4;
    public static final int ERROR = 5;
    public static final int NOTHING = 6;
    public static int level = VERBOSE;


    public static void d(String TAG, String content){
        if (level<=DEBUG)
        {Log.d(TAG, "TAG  "+ content);}
    }
}
