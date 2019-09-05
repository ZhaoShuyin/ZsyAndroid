package com.zsy.android.layout.activity.router;

import android.content.Context;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;

import java.util.ArrayList;
import java.util.List;

/**
 * Title: ZsyAndroid
 * <p>
 * Description:
 * </p>
 *
 * @author Zsy
 * @date 2019/7/30 9:41
 */

public class RouterUtil {

    public static List<RouterActivity> routerActivities = new ArrayList<>();

    public static class RouterActivity {
        String host;
        Class clazz;

        public RouterActivity(String host, Class clazz) {
            this.host = host;
            this.clazz = clazz;
        }
    }

    public static void init(Context context) {
        PackageManager pm = context.getPackageManager();
        PackageInfo info = pm.getPackageArchiveInfo(context.getPackageCodePath(), PackageManager.GET_ACTIVITIES);
        ActivityInfo[] activities = info.activities;
        for (int i = 0; i < activities.length; i++) {
            ActivityInfo act = activities[i];
            try {
                Class<?> aClass = Class.forName(act.name);
                if (aClass.isAnnotationPresent(RouterNode.class)) {
                    RouterNode annotation = aClass.getAnnotation(RouterNode.class);
                    String host = annotation.host();
                    routerActivities.add(new RouterActivity(host, aClass));
                }
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            }
        }
    }


    public static void start(Context context, String host) {
        for (int i = 0; i < routerActivities.size(); i++) {
            RouterActivity routerActivity = routerActivities.get(i);
            if (routerActivity.host.equals(host)) {
                context.startActivity(new Intent(context,routerActivity.clazz));
            }
        }
    }
}
