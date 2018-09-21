package com.diva.appdemo.Interface;

import com.diva.appdemo.Entity.AppInfo;
import com.diva.appdemo.Entity.StudentInfo;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;

/**
 * Created by 刘迪 on 2018/8/7 17:38.
 * 邮箱：divaliu1408@qq.com
 */

public interface NetworkInterface {

    @GET("appInfo.txt")
    Call<AppInfo> getAppVersion();

    @GET("StudentInfo.txt")
    Call<List<StudentInfo>> getStudentInfo();

}
