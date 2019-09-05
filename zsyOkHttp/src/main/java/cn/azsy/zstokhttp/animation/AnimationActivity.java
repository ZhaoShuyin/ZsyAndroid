package cn.azsy.zstokhttp.animation;

import android.graphics.drawable.AnimationDrawable;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.animation.DecelerateInterpolator;
import android.view.animation.LinearInterpolator;
import android.view.animation.RotateAnimation;
import android.view.animation.ScaleAnimation;
import android.view.animation.TranslateAnimation;
import android.widget.ImageView;
import android.widget.Toast;

import cn.azsy.zstokhttp.R;

/**
 * Created by zsy on 2017/6/25.
 */

public class AnimationActivity extends AppCompatActivity {

    ImageView image;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_animation);
        image = (ImageView) findViewById(R.id.image);
        image.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(AnimationActivity.this, "点击图片", Toast.LENGTH_SHORT).show();
            }
        });
    }
    public void stopAnimation(View view){
        image.clearAnimation();
    }

    public void reSetBack(View view){
        image.setBackgroundResource(R.mipmap.ic_launcher);
        ViewGroup.LayoutParams params = image.getLayoutParams();
        params.height= View.MeasureSpec.EXACTLY;
        params.width=View.MeasureSpec.EXACTLY;
        image.setLayoutParams(params);
    }

    RotateAnimation rotateAnimation;//旋转动画
    TranslateAnimation translateAnimation;//平移动画
    ScaleAnimation scaleAnimation;
    AnimationDrawable animationDrawable;
    /**
     *自旋动画
     */
    public void Animation01(View view){
        rotateAnimation = new RotateAnimation(0f,360f,//开始角度,结束角度
                Animation.RELATIVE_TO_SELF,0.5f,//基于点,50%
                Animation.RELATIVE_TO_SELF, 0.5f);//基于点,50%
        rotateAnimation.setDuration(5000);
        rotateAnimation.setInterpolator(new LinearInterpolator());
        rotateAnimation.setRepeatCount(3);
        rotateAnimation.setRepeatMode(Animation.REVERSE);//重复模式
        rotateAnimation.setAnimationListener(new Listener());//动画监听器
        image.startAnimation(rotateAnimation);
    }

    /**
     *平移动画
     */
    public void Animation02(View view){
//        translateAnimation = new TranslateAnimation(Animation.RELATIVE_TO_SELF, 0f,
//                                                    Animation.RELATIVE_TO_SELF, 3f,
//                                                    Animation.RELATIVE_TO_SELF, 0f,
//                                                    Animation.RELATIVE_TO_SELF, 3f);
//        translateAnimation.setInterpolator(new AccelerateDecelerateInterpolator());
//        translateAnimation.setDuration(6000);
//        image.startAnimation(translateAnimation);
        Animation animation = AnimationUtils.loadAnimation(this, R.anim.translation);
        //开启动画
        image.startAnimation(animation);
    }

    /**
     * 比例动画
     */
    public void Animation03(View view){
        scaleAnimation = new ScaleAnimation(1.0f, 6.0f,//开始时x方向大小,结束时x方向大小
                                            1.0f, 6.0f,//开始时y方向大小,结束时y方向大小
//                                            Animation.RELATIVE_TO_PARENT, 0.5f,//缩放中心点的坐标
                                            Animation.RELATIVE_TO_SELF, 0.5f,//缩放中心点的坐标
//                                            Animation.RELATIVE_TO_PARENT, 0.5f);//缩放中心点的坐标
                                            Animation.RELATIVE_TO_SELF, 0.5f);//缩放中心点的坐标
        scaleAnimation.setDuration(6000);
        scaleAnimation.setInterpolator(new DecelerateInterpolator());
        image.startAnimation(scaleAnimation);
    }

    /**
     * 帧动画
     */
    public void Animation04(View v){
        image.setBackgroundResource(R.drawable.animation);
        ViewGroup.LayoutParams params = image.getLayoutParams();
        params.height=200;
        params.width=200;
        image.setLayoutParams(params);
        animationDrawable = (AnimationDrawable) image.getBackground();
        animationDrawable.start();
    }

    class Listener implements Animation.AnimationListener {

        @Override
        public void onAnimationStart(Animation animation) {
            Toast.makeText(AnimationActivity.this, "动画开始了", Toast.LENGTH_SHORT).show();
        }

        @Override
        public void onAnimationEnd(Animation animation) {
            Toast.makeText(AnimationActivity.this, "动画结束了", Toast.LENGTH_SHORT).show();
        }

        @Override
        public void onAnimationRepeat(Animation animation) {
            Toast.makeText(AnimationActivity.this, "动画重复了", Toast.LENGTH_SHORT).show();
        }
    }

}
