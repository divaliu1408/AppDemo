package com.diva.appdemo.Manager;

import android.app.Application;
import android.util.DisplayMetrics;
import android.util.Log;

import com.diva.appdemo.zxing.DisplayUtil;

import org.xutils.DbManager;
import org.xutils.db.table.TableEntity;
import org.xutils.x;

/**
 * Created by 刘迪 on 2018/8/8 10:37.
 * 邮箱：divaliu1408@qq.com
 */

public class MyApplication extends Application{

   private static MyApplication instance;
    static DbManager db;
    @Override
    public void onCreate() {
        super.onCreate();
        x.Ext.init(this);
        initDBManeger();
      initDisplayOpinion();
    }

    private void initDBManeger() {
        DbManager.DaoConfig daoConfig = new DbManager.DaoConfig()
                .setDbName("appDemo.db")
                .setTableCreateListener(new DbManager.TableCreateListener() {
                    @Override
                    public void onTableCreated(DbManager db, TableEntity<?> table) {
                        Log.i(getPackageName() ,"onTableCreated: "+table.getName());
                    }
                })
                .setAllowTransaction(true)
                .setDbVersion(1)
                .setDbOpenListener(new DbManager.DbOpenListener() {
                    @Override
                    public void onDbOpened(DbManager db) {
                        db.getDatabase().enableWriteAheadLogging();
                    }
                })
                .setDbUpgradeListener(new DbManager.DbUpgradeListener() {
                    @Override
                    public void onUpgrade(DbManager db, int oldVersion, int newVersion) {
                        Log.i(getPackageName(), "onUpgrade: "+oldVersion+"to"+newVersion);
                    }
                });
        db= x.getDb(daoConfig);
    }

    public static DbManager getDb() {
        return db;
    }

    public void setDb(DbManager db) {
        this.db = db;
    }



    private void initDisplayOpinion() {
        DisplayMetrics dm = getResources().getDisplayMetrics();
        DisplayUtil.density = dm.density;
        DisplayUtil.densityDPI = dm.densityDpi;
        DisplayUtil.screenWidthPx = dm.widthPixels;
        DisplayUtil.screenhightPx = dm.heightPixels;
        DisplayUtil.screenWidthDip = DisplayUtil.px2dip(getApplicationContext(), dm.widthPixels);
        DisplayUtil.screenHightDip = DisplayUtil.px2dip(getApplicationContext(), dm.heightPixels);
    }
    public static MyApplication getInstance(){
        return instance;
    }
}