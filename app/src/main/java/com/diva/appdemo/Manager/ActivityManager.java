package com.diva.appdemo.Manager;

import android.app.Activity;
import android.util.Log;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * Created by 刘迪 on 2018/8/3 17:26.
 * 邮箱：divaliu1408@qq.com
 */

/**
 * Activity界面管理工具
 */
public final class ActivityManager {
    private static List<Activity> activities = new ArrayList<Activity>();// 普通Activity

    /**
     * 添加普通Activity
     *
     * @param activity
     */
    public static void addActivity(Activity activity) {
        if (activities.contains(activity)) {
            removeActivity(activity);
        }
        activities.add(activity);
        Log.d("ActivityManager--enter", activity.getClass().getSimpleName());
    }

    /**
     * 删除普通Activity
     *
     * @param activity
     */
    public static void removeActivity(Activity activity) {
        activities.remove(activity);
        Log.d("ActivityManager--exit", activity.getClass().getSimpleName());
    }

    /**
     * 结束普通Activity的栈顶的元素
     *
     */
    public static void getTopActivityAndFinish() {
        Activity top = activities.get(activities.size() - 1);
        removeActivity(top);
        top.finish();
    }

    public static boolean isActivityTop(String name){
        Activity top = activities.get(activities.size() - 1);
        return top.getClass().getSimpleName().equals(name);
    }

    /**
     * 结束当前之外的Activity
     */
    public static void finishNotTopActivity(Activity top) {
        Iterator<Activity> iterator = activities.iterator();
        while (iterator.hasNext()) {
            Activity activity = iterator.next();
            if (top != activity) {
                iterator.remove();
                activity.finish();
            }
        }
    }

    /**
     * 结束所有activity
     */
    public static void finishAllActivity() {
        Iterator<Activity> iterator = activities.iterator();
        while (iterator.hasNext()) {
            Activity activity = iterator.next();
            iterator.remove();
            activity.finish();
        }
    }

    public static void backToActivity(String toActivity) {
        List<Activity> needRemove = new ArrayList<Activity>();
        for (int i = activities.size() - 1; i < activities.size(); i--) {
            if (i >= 0) {
                Activity activity = activities.get(i);
                if (toActivity.equals(activity.getClass().getSimpleName())) {
                    break;
                }
                needRemove.add(activity);
                // removeActivity(activity);
                // activity.finish();
            }
        }
        activities.removeAll(needRemove);
        // finish后对象销毁，确保上一步准确执行还是提出来做
        for (Activity activity : needRemove) {
            activity.finish();
        }
    }
}

