package cn.azsy.zstokhttp.utils.zsyutils;

import android.content.Context;
import android.content.SharedPreferences;

import java.util.Set;

/**
 * Created by zsy on 2017/5/24.
 */

public class SpSave {

    public static String spName = "config";

    public static void spPutBoolean(Context context, String paramName, boolean value) {
        SharedPreferences sp = context.getSharedPreferences(spName, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sp.edit();
        editor.putBoolean(paramName, value);
        editor.commit();
    }

    public static void spPutInt(Context context, String paramName, int vlaue) {
        SharedPreferences sp = context.getSharedPreferences(spName, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sp.edit();
        editor.putInt(paramName, vlaue);
        editor.commit();
    }

    public static void spPutFloat(Context context, String paramName, float value) {
        SharedPreferences sp = context.getSharedPreferences(spName, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sp.edit();
        editor.putFloat(paramName, value);
        editor.commit();
    }

    public static void spPutLong(Context context, String paramName, long value) {
        SharedPreferences sp = context.getSharedPreferences(spName, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sp.edit();
        editor.putLong(paramName, value);
        editor.commit();
    }

    public static void spPutString(Context context, String paramName, String vlaue) {
        SharedPreferences sp = context.getSharedPreferences(spName, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sp.edit();
        editor.putString(paramName, vlaue);
        editor.commit();
    }

    public static void spPutStringSet(Context context, String paramName, Set<String> vlaue) {
        SharedPreferences sp = context.getSharedPreferences(spName, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sp.edit();
        editor.putStringSet(paramName, vlaue);
        editor.commit();
    }

    public static boolean spGetBoolean(Context context, String paramName, boolean defaultValue) {
        SharedPreferences sp = context.getSharedPreferences(spName, Context.MODE_PRIVATE);
        return sp.getBoolean(paramName, defaultValue);
    }

    public static int spGetInt(Context context, String paramName, int defaultValue) {
        SharedPreferences sp = context.getSharedPreferences(spName, Context.MODE_PRIVATE);
        return sp.getInt(paramName, defaultValue);
    }

    public static float spGetFloat(Context context, String paramName, float defaultValue) {
        SharedPreferences sp = context.getSharedPreferences(spName, Context.MODE_PRIVATE);
        return sp.getFloat(paramName, defaultValue);
    }

    public static float spGetLong(Context context, String paramName, long defaultValue) {
        SharedPreferences sp = context.getSharedPreferences(spName, Context.MODE_PRIVATE);
        return sp.getLong(paramName, defaultValue);
    }

    public static String spGetString(Context context, String paramName, String defaultValue) {
        SharedPreferences sp = context.getSharedPreferences(spName, Context.MODE_PRIVATE);
        return sp.getString(paramName, defaultValue);
    }

    public static Set<String> spGetStringSert(Context context, String paramName, Set<String> defaultValue) {
        SharedPreferences sp = context.getSharedPreferences(spName, Context.MODE_PRIVATE);
        return sp.getStringSet(paramName, defaultValue);
    }
}
