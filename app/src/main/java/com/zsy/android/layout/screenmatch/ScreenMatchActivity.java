package com.zsy.android.layout.screenmatch;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.DisplayMetrics;
import android.widget.TextView;

import com.zsy.android.R;

/**
 * Title: ZsyGit
 * <p>
 * Description:屏幕适配(使用screenMatch插件通过密度比使用不同dp,sp值)
 * </p>
 *
 * @author Zsy
 * @date 2019/5/21 14:06
 */

public class ScreenMatchActivity extends Activity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_screen_match);
        densityTest();
    }

    /**
     * 屏幕密度  比例     分辨率        1(drawable图片)
     * ldpi     120     3     320*240       2(drawable-ldpi)
     * mdpi     160     4     320*480       3(drawable-mdpi)
     * hdpi     240     6     480*800       4(drawable-hdpi)
     * xhdpi    320     8    1280*720       5(drawable-xhdpi)
     * xxhdpi   480    12   1920*1080       6(drawable-xxhdpi)
     * xxxhdpi  640    16   3840*2160       7(drawable-xxxhdpi)
     * sw  = minSize / density
     * <p>
     * >>(屏宽752dp)>>低密度 10px  0.013(屏幕占比)>>>>>>>>>>>>>>22.22px  0.029(屏幕占比)
     * *          相同 10dp 值              通过screenmatch             接近一致(实现屏幕适配)
     * >>(屏宽411dp)>>高密度 26px  0.024(屏幕占比)>>>>>>>>>>>>>>29.96px  0.027(屏幕占比)
     * <p>
     * ***************三星手机*******************
     * density== 2.625
     * densityDpi== 420
     * widthPixels==1080
     * heightPixels==1920
     * sw == 411 sw2 == 731
     * ****************KTE KT960*******************
     * density== 1.0
     * densityDpi== 160
     * widthPixels==1280
     * heightPixels==752
     * sw == 1280 sw2 == 752 (取宽高较小值获取sw)(计算为)752则使用800(向上兼容)
     * ************************************
     * density== 1.0
     * densityDpi == 160
     * widthPixels==1280
     * heightPixels==752
     * sw == 1280 sw2 == 752
     * * *************1280*720*****************
     * density== 2.0      密度为每平方英寸像素点个数
     * densityDpi== 320   密度比为 (像素点)/尺寸
     * widthPixels==720
     * heightPixels==1280
     * sw == 360 sw2 == 640   sw = 较小宽度/密度
     * ************1920*1080*********************
     * density== 3.0      密度为每平方英寸像素点个数
     * densityDpi== 480   密度比为 (像素点)/尺寸
     * widthPixels==1080
     * heightPixels==1920
     * sw == 360 sw2 == 640   sw = 较小宽度/密度
     * *************2560*1440*********************
     * density== 4.0      密度为每平方英寸像素点个数
     * densityDpi== 640   密度比为 (像素点)/尺寸
     * widthPixels==1440
     * heightPixels==2560
     * sw == 360 sw2 == 640   sw = 较小宽度/密度
     */
    private void densityTest() {
        DisplayMetrics displayMetrics = getResources().getDisplayMetrics();
        int widthPixels = displayMetrics.widthPixels;          //屏幕宽度
        int heightPixels = displayMetrics.heightPixels;        //屏幕高度
        int densityDpi = displayMetrics.densityDpi;            //像素除以英寸即为密度比
        float density = displayMetrics.density;                //密度(每平方英寸像素点个数)
        int sw = (int) (widthPixels / density);
        int sw2 = (int) (heightPixels / density);
        String msg = "onCreate: " +
                "\n density== " + density +
                "\n densityDpi== " + densityDpi +
                "\n widthPixels==" + widthPixels +
                "\n heightPixels==" + heightPixels +
                "\n sw == " + sw + " sw2 == " + sw2;
        TextView textView = findViewById(R.id.tv_show);
        float dimension = getResources().getDimension(R.dimen.dp_100);
        float dimension10 = getResources().getDimension(R.dimen.dp_10);
        float size = Math.min(widthPixels, heightPixels);
        String text = msg + "\n使用sw== " + (int) (dimension / density) + "  屏幕宽度== " + size / density + "dp"
                + "\n 未适配10dp == " + (10 * density)
                + "\n 为适配比例== " + (10 * density) / size
                + "\n 适配后 10dp --> px == " + dimension10
                + "\n 适配后 比例== " + dimension10 / size;
        textView.setText(text);
    }

}
