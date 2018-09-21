package com.diva.appdemo.Manager;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.diva.appdemo.Activity.FriendsActivity;
import com.diva.appdemo.R;
import com.diva.appdemo.Service.MyService;
import com.diva.appdemo.Utils.NetworkUtil;
import com.diva.appdemo.model.ChatMessage;
import com.diva.mylibrary.Utils.LogUtils;


/**
 * Created by 刘迪 on 2018/8/3 17:25.
 * 邮箱：divaliu1408@qq.com
 */

public class BaseActivity extends AppCompatActivity{

    private static final String TAG = "BaseActivity";
    public static  boolean isOnline;
    private NetworkUtil.onlineListener onlineListener = new NetworkUtil.onlineListener() {
        @Override
        public void online() {
            Log.e(TAG, "online: " );
        }

        @Override
        public void offline() {
            Log.e(TAG, "offline: " );
            mentionOffline();
        }
    };


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ActivityManager.addActivity(this);
        Log.d(this.getLocalClassName(), "onCreate: " );
        LogUtils.d(this.getLocalClassName(),"onCreated");
    }

    @Override
    protected void onDestroy() {
        ActivityManager.removeActivity(this);
        super.onDestroy();
    }

    protected Toolbar toolbar;
    protected TextView toolbar_center_title;
    protected boolean isShowToolbar = true;
    protected boolean isSetNavigationIcon = true;
    protected boolean isSetLogo = false;
    protected boolean isShowEmail = true;
    protected String title;
    protected String centertitle;
    protected String subtitle;



    @Override
    protected void onResume() {
        super.onResume();
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar_center_title = (TextView) findViewById(R.id.toolbar_center_title);

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_base, menu);

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        int id = item.getItemId();

        switch (id) {
            case R.id.action_settings:
                break;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        MyService.checkOnline(onlineListener,this);
    }


    public void mentionOffline(){
        AlertDialog.Builder dialog = new AlertDialog.Builder(this);
        dialog.setMessage("您已掉线，请重新登录");
        //设置确定按钮并监听
        dialog.setPositiveButton("确定", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                ActivityManager.finishAllActivity();
            }
        });
        //设置取消按钮监听
        dialog.setNegativeButton("取消", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                //这里什么也不用做
            }
        });
        dialog.show();
    }

}
