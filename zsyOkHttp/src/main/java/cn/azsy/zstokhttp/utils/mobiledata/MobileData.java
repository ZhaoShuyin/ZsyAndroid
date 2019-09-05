package cn.azsy.zstokhttp.utils.mobiledata;

import android.content.Context;
import android.view.WindowManager;

/**
 * Created by zsy on 2017/5/25.
 */

public class MobileData {

    //获取手机屏幕宽度
    public static int getPrintWidth(Context context) {
        WindowManager wm = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
        int printWith = wm.getDefaultDisplay().getWidth();
        return printWith;
    }

    //获取手机屏幕高度
    public static int getPrintHeight(Context context) {
        WindowManager wm = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
        int printHeight = wm.getDefaultDisplay().getHeight();
        return printHeight;
    }

    /**
     * 获取dp,密度比算出具体px像素值
     */
    public static int getDpPx(Context context, float dpValue) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (dpValue * scale + 0.5f);
    }

    /**
     * 获取sp,密度比算出sp像素值
     */
    public static int getSpPx(Context context, float spValve) {
        final float fontScale = context.getResources().getDisplayMetrics().scaledDensity;
        return (int) (spValve * fontScale + 0.5f);
    }

    /**
     *获取系统版本信息
     */
    public static int getAndroidVersion(){
        return android.os.Build.VERSION.SDK_INT;
    }

}
