package com.zsy.zlib.animation;

import android.view.View;
import android.view.animation.AccelerateInterpolator;
import android.view.animation.Animation;
import android.view.animation.DecelerateInterpolator;

/**
 * Title: ZsyAndroid
 * <p>
 * Description:动画工具类
 * </p>
 *
 * @author Zsy
 * @date 2019/6/11 10:02
 */

public class AniUtil {

    /**
     * @param comtain   父布局View
     * @param front     正面View
     * @param back      背面View
     * @param trunBack  是否从背面翻到正面
     */
    public static void turnOverAnimation(final View comtain, final View front, final View back, int durationTime, final boolean trunBack) {
        final float centerX = comtain.getWidth() / 2.0f;
        final float centerY = comtain.getHeight() / 2.0f;
        final int fromDegree = trunBack ? 180 : 0;//翻转起始角度
        final int toDegree = 90;                  //翻转转换角度
        final int endDegree = 180 - fromDegree;   //翻转停止角度
        final int duration = durationTime >> 1;   //动画时间
        final float depthZ = 300f;                //翻转深度(图片缩小翻转)
        final Rotate3dAnimation fpRotation = new Rotate3dAnimation(fromDegree, toDegree, centerX, centerY, depthZ, true);

        fpRotation.setDuration(duration);
        fpRotation.setFillAfter(true);
        fpRotation.setInterpolator(new AccelerateInterpolator());
        fpRotation.setAnimationListener(new Animation.AnimationListener() {
            Rotate3dAnimation lpRotation;

            @Override
            public void onAnimationStart(Animation animation) {
            }

            @Override
            public void onAnimationEnd(Animation animation) {
                comtain.post(new Runnable() {
                    @Override
                    public void run() {
                        lpRotation = new Rotate3dAnimation(toDegree, endDegree, centerX, centerY, depthZ, false);
                        if (!trunBack) {
                            front.setVisibility(View.GONE);
                            back.setVisibility(View.VISIBLE);
                        } else {
                            front.setVisibility(View.VISIBLE);
                            back.setVisibility(View.GONE);
                        }
                        lpRotation.setDuration(duration);
                        lpRotation.setFillAfter(true);
                        lpRotation.setInterpolator(new DecelerateInterpolator());
                        comtain.startAnimation(lpRotation);
                    }
                });
            }

            @Override
            public void onAnimationRepeat(Animation animation) {
            }
        });
        comtain.startAnimation(fpRotation);
    }


}
