package com.zsy.android.view.animation;

import android.animation.Animator;
import android.animation.AnimatorInflater;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.app.Activity;
import android.graphics.drawable.AnimationDrawable;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.view.ViewPropertyAnimator;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.animation.LinearInterpolator;
import android.view.animation.RotateAnimation;
import android.view.animation.ScaleAnimation;
import android.view.animation.TranslateAnimation;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.zsy.android.R;
import com.zsy.zlib.animation.AniUtil;

/**
 * Title: ZsyAndroid
 * <p>
 * Description:Android动画
 * </p>
 *
 * @author Zsy
 * @date 2019/6/5 10:49
 */

public class AnimationActivity extends Activity {

    FrameLayout frameLayout;
    TextView textView;
    ImageView imageViewBack;
    private String TAG = "test";

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_animation);
        frameLayout = findViewById(R.id.fl_ani);
        textView = findViewById(R.id.iv_animation);
        imageViewBack = findViewById(R.id.iv_back);
        textView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(AnimationActivity.this, "点击了", Toast.LENGTH_SHORT).show();
            }
        });
    }

    //平移动画
    public void translation(View view) {
        Toast.makeText(this, "平移动画", Toast.LENGTH_SHORT).show();
        TranslateAnimation translateAnimation = new TranslateAnimation(
                Animation.RELATIVE_TO_SELF, 0f,
                Animation.RELATIVE_TO_SELF, 3f,
                Animation.RELATIVE_TO_SELF, 0f,
                Animation.RELATIVE_TO_SELF, 3f);
        translateAnimation.setInterpolator(new AccelerateDecelerateInterpolator());
        translateAnimation.setDuration(6000);
        textView.startAnimation(translateAnimation);
    }

    //旋转动画
    public void rotate(View view) {
        RotateAnimation rotateAnimation = new RotateAnimation(0, 360
                , Animation.RELATIVE_TO_SELF, 0.5f
                , Animation.RELATIVE_TO_SELF, 0.5f
        );
        rotateAnimation.setDuration(5000);
        textView.startAnimation(rotateAnimation);
    }

    //比例动画
    public void zoom(View view) {
        ScaleAnimation scaleAnimation = new ScaleAnimation(1f, 2f, 1f, 3f//X,Y的起始和结束比例
//                ,100,100 //缩放X,Y轴心绝对坐标
                , Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF, 0.5f//缩放X,Y轴心相对坐标
        );
        scaleAnimation.setInterpolator(new AccelerateDecelerateInterpolator());
        scaleAnimation.setDuration(6000);
        textView.startAnimation(scaleAnimation);
    }

    //透明度动画
    public void transparency(View view) {
        AlphaAnimation alphaAnimation = new AlphaAnimation(0f, 1f);
        alphaAnimation.setInterpolator(new AccelerateDecelerateInterpolator());
        alphaAnimation.setDuration(6000);
        textView.startAnimation(alphaAnimation);
    }


    AnimationDrawable mFrameAnim;

    //帧动画
    public void frameAnim(View view) {
        if (mFrameAnim == null) {
            mFrameAnim = new AnimationDrawable();
            mFrameAnim.addFrame(getResources().getDrawable(R.drawable.frame_1), 100);
            mFrameAnim.addFrame(getResources().getDrawable(R.drawable.frame_2), 100);
            mFrameAnim.addFrame(getResources().getDrawable(R.drawable.frame_3), 100);
            mFrameAnim.addFrame(getResources().getDrawable(R.drawable.frame_4), 100);
            mFrameAnim.addFrame(getResources().getDrawable(R.drawable.frame_5), 100);
            mFrameAnim.addFrame(getResources().getDrawable(R.drawable.frame_6), 100);
            mFrameAnim.addFrame(getResources().getDrawable(R.drawable.frame_7), 100);
            mFrameAnim.addFrame(getResources().getDrawable(R.drawable.frame_8), 100);
            mFrameAnim.addFrame(getResources().getDrawable(R.drawable.frame_9), 100);
            mFrameAnim.addFrame(getResources().getDrawable(R.drawable.frame_10), 100);
            mFrameAnim.addFrame(getResources().getDrawable(R.drawable.frame_11), 100);
            // 设置为循环播放
            mFrameAnim.setOneShot(false);
            textView.setBackground(mFrameAnim);
        }
        if (mFrameAnim.isRunning()) {
            mFrameAnim.stop();
        } else {
            mFrameAnim.start();
        }

    }


    //XML定义动画
    public void xmlAnim(View view) {
        //XML帧动画
//        AnimationDrawable frameAnim=(AnimationDrawable) getResources().getDrawable(R.drawable.anim_frame);
//        imageView.setImageDrawable(frameAnim);
//        frameAnim.start();

        //XML旋转动画
//        Animation animation = AnimationUtils.loadAnimation(this, R.anim.anim_rotate);
//        //开启动画
//        imageView.startAnimation(animation);

        //透明度
//        Animation animation = AnimationUtils.loadAnimation(this, R.anim.anim_alpha);
//        imageView.startAnimation(animation);

        //闪烁动画
        Animation animation = AnimationUtils.loadAnimation(this, R.anim.anim_blink);
        textView.startAnimation(animation);
    }

    boolean trun3d = false;

    /**
     * 3D翻转动画
     */
    public void rotate3d(View view) {
//        final float centerX = frameLayout.getWidth() / 2.0f;
//        final float centerY = frameLayout.getHeight() / 2.0f;
//        final Rotate3dAnimation rotation =
//                new Rotate3dAnimation(0, 90, centerX, centerY, 310.0f, true);
//        rotation.setDuration(2000);
//        rotation.setFillAfter(true);
//        rotation.setInterpolator(new AccelerateInterpolator());
//        frameLayout.startAnimation(rotation);
        AniUtil.turnOverAnimation(frameLayout, textView, imageViewBack, 5000, trun3d);
        trun3d = !trun3d;
    }


    public void objectXml(View view){
        Animator animator = AnimatorInflater.loadAnimator(this, R.animator.object_rotate);
        animator.setTarget(textView);
        animator.start();
    }


    // 属性动画 平移
    public void translateObject(View view) {
        ObjectAnimator objectAnimator = ObjectAnimator.ofFloat(textView, "translationX",
                0.0f, 350.0f, 0.0f);
        objectAnimator.setDuration(2000);
        //Animation.INFINITE
        objectAnimator.setRepeatCount(Animation.RESTART);
        objectAnimator.setRepeatMode(ValueAnimator.RESTART);
        objectAnimator.start();
    }

    // 属性动画 缩放
    public void scaleXObject(View view) {
        ObjectAnimator objectAnimator = ObjectAnimator.ofFloat(textView, "scaleX", 1.0f, 1.5f);
        objectAnimator.setDuration(5000);
        objectAnimator.setRepeatCount(Animation.RESTART);
        objectAnimator.setRepeatMode(ValueAnimator.RESTART);
        objectAnimator.start();
    }

    // 属性动画 旋转
    public void rotateObject(View view) {
        ObjectAnimator objectAnimator = ObjectAnimator.ofFloat(textView, "rotationX", 0.0f, 90.0f, 0.0F);
        objectAnimator.setDuration(5000);
        objectAnimator.setRepeatCount(Animation.RESTART);
        objectAnimator.setRepeatMode(ValueAnimator.RESTART);
        objectAnimator.start();
    }

    // 属性动画 透明度
    public void alphaObject(View view) {
        ObjectAnimator objectAnimator = ObjectAnimator.ofFloat(textView, "alpha", 1.0f, 0.3f, 1.0F);
        objectAnimator.setDuration(5000);
        objectAnimator.setRepeatCount(Animation.RESTART);
        objectAnimator.setRepeatMode(ValueAnimator.RESTART);
        objectAnimator.start();

    }

    //属性动画 组合动画
    public void multiObject(View view) {
        ObjectAnimator objectAnimator1 = ObjectAnimator.ofFloat(textView, "scaleX", 1.0f, 2.5f, 1.0f);
        ObjectAnimator objectAnimator2 = ObjectAnimator.ofFloat(textView, "scaleY", 1.0f, 2.5f, 1.0f);
        ObjectAnimator objectAnimator3 = ObjectAnimator.ofFloat(textView, "rotationX", 0.0f, 90.0f, 0.0F);
        AnimatorSet animatorSet = new AnimatorSet();
        animatorSet.play(objectAnimator1).with(objectAnimator2).with(objectAnimator3);
        animatorSet.setDuration(5000);
        animatorSet.start();

    }

    //属性动画之多属性动画
    public void viewPropertyObject(View view) {
        ViewPropertyAnimator animator = textView.animate();
        animator.translationX(200)
                .scaleX(2)
                .scaleY(2)
                .setDuration(5000)
                .start();

    }

    public void valueAni(View view) {
        ValueAnimator valueAnimator = ValueAnimator.ofInt(100);
        valueAnimator.setDuration(5000);
        valueAnimator.setInterpolator(new LinearInterpolator());
        valueAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                textView.setText(String.valueOf(animation.getAnimatedValue()));
            }
        });
        valueAnimator.start();
    }


}
