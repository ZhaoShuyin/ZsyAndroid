package com.zsy.android.layout.screenmatch;

import android.app.Activity;
import android.graphics.Point;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.DisplayMetrics;
import android.view.Display;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.zsy.android.R;

import java.lang.reflect.Method;
import java.math.BigDecimal;

/**
 * Title: ZsyGit
 * <p>
 * Description:屏幕适配(使用screenMatch插件通过密度比使用不同dp,sp值)
 * ScreenMatch不是比例适配,而是像素适配
 * 原始dp是比例适配,使用ScreenMath在密度比大时使用较小的dp实现像素不变
 * </p>
 *
 * @author Zsy
 * @date 2019/5/21 14:06
 */

public class ScreenMatchActivity extends Activity {

    TextView tv1, tv2;
    TextView tv_width;//屏幕宽度
    private TextView textView;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_screen_match);
        tv1 = findViewById(R.id.tv_screen_view1);
        tv2 = findViewById(R.id.tv_screen_view2);
        tv_width = findViewById(R.id.tv_width);
        textView = findViewById(R.id.tv_show);
        densityTest();
//        Toast.makeText(this, "手机" + getScreenInch(this), Toast.LENGTH_LONG).show();
    }

    /**
     * 屏幕密度 密度比  比例     分辨率        1(drawable图片)
     * ldpi     120     3      320*240       2(drawable-ldpi)
     * mdpi     160     4      320*480       3(drawable-mdpi)
     * hdpi     240     6      480*800       4(drawable-hdpi)
     * xhdpi    320     8     1280*720       5(drawable-xhdpi)
     * xxhdpi   480    12    1920*1080       6(drawable-xxhdpi)
     * xxxhdpi  640    16    3840*2160       7(drawable-xxxhdpi)
     * sw  = minSize / density 计算使用哪个value下的值
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
     * ****************KTE KT960 平板*******************
     * density== 1.0
     * densityDpi== 160
     * widthPixels==1280
     * heightPixels==752
     * sw == 1280 sw2 == 752 (取宽高较小值获取sw)(计算为)752则使用800(向上匹配)
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
        int width = displayMetrics.widthPixels;          //屏幕宽度
        int height = displayMetrics.heightPixels;        //屏幕高度
        //在X维中屏幕每英寸的确切物理像素
        int xdpi = (int) displayMetrics.xdpi;
        //在Y维中屏幕每英寸的确切物理像素
        int ydpi = (int) displayMetrics.ydpi;
        int densityDpi = displayMetrics.densityDpi;            //像素除以英寸即为密度比
        float density = displayMetrics.density;                //密度(每平方英寸像素点个数)
        float cWidth = (width / xdpi);
        float cHeight = (height / ydpi);
        String msg = "onCreate: " +
                "\n密度:(每平方英寸像素点个数)" +
                "\n   密度:   density== " + density +
                "\n密度比:(对角线的像素值/对角线的尺寸)" +
                "\n   密度比: densityDpi== " + densityDpi +
                "\n" +
                "\n 屏幕宽: width:" + width + "像素 屏幕高: height:" + height + "像素" +
                "\n xdpi==" + xdpi + "  ydpi==" + ydpi +
                "\n 物理尺寸 <" + cWidth + ">英寸宽  <" + cHeight + ">英寸高" +
                "\n 手机尺寸:" + Math.sqrt(cWidth * cWidth + cHeight * cHeight) + " 寸";


        float dp100 = getResources().getDimension(R.dimen.dp_100);
        float minSize = Math.min(width, height);
        int sw = (int) (minSize / density);
        String text = msg + "\n\n使用 sw == <"+minSize+"> / <"+density+"> == " + sw +
                "\n" +
                "\n  屏幕宽度== " + minSize / density + "dp"
                + "\n 未适配 100dp  == " + (100 * density) + "像素"
                + "\n 未适配 100dp/屏幕宽度== " + (100 * density) / minSize
                + "\n 适配后 dp_100 == " + dp100 + "像素"
                + "\n 适配后 100dp/屏幕宽度== " + dp100 / minSize;
        textView.setText(text);

        ViewGroup.LayoutParams params = tv1.getLayoutParams();
        int v = (int) (200 * density);
        params.width = v;
        tv1.setLayoutParams(params);
        tv1.setText(v + "像素宽");


        ViewGroup.LayoutParams params2 = tv2.getLayoutParams();
        params2.width = 200;
        tv2.setLayoutParams(params2);
        tv2.setText("200像素宽");
    }

    private static double mInch = 0;

    public static double getScreenInch(Activity context) {
        if (mInch != 0.0d) {
            return mInch;
        }

        try {
            int realWidth = 0, realHeight = 0;
            Display display = context.getWindowManager().getDefaultDisplay();
            DisplayMetrics metrics = new DisplayMetrics();
            display.getMetrics(metrics);
            if (android.os.Build.VERSION.SDK_INT >= 17) {
                Point size = new Point();
                display.getRealSize(size);
                realWidth = size.x;
                realHeight = size.y;
            } else if (android.os.Build.VERSION.SDK_INT < 17
                    && android.os.Build.VERSION.SDK_INT >= 14) {
                Method mGetRawH = Display.class.getMethod("getRawHeight");
                Method mGetRawW = Display.class.getMethod("getRawWidth");
                realWidth = (Integer) mGetRawW.invoke(display);
                realHeight = (Integer) mGetRawH.invoke(display);
            } else {
                realWidth = metrics.widthPixels;
                realHeight = metrics.heightPixels;
            }
            mInch = formatDouble(Math.sqrt((realWidth / metrics.xdpi) * (realWidth / metrics.xdpi) + (realHeight / metrics.ydpi) * (realHeight / metrics.ydpi)), 1);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return mInch;
    }

    /**
     * Double类型保留指定位数的小数，返回double类型（四舍五入）
     * newScale 为指定的位数
     */
    private static double formatDouble(double d, int newScale) {
        BigDecimal bd = new BigDecimal(d);
        return bd.setScale(newScale, BigDecimal.ROUND_HALF_UP).doubleValue();
    }
}
