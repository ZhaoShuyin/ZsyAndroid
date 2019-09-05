package cn.azsy.zstokhttp.views.objanim;

import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;

import cn.azsy.zstokhttp.views.viewanim.Point;
import cn.azsy.zstokhttp.views.viewanim.PointEvaluator;

/**
 * Created by zsy on 2017/7/13.
 */

public class ObjAnimView extends View {
    public static final float RADIUS = 50f;//固定的圆半径

    private Point currentPoint;

    private Paint mPaint;

    public ObjAnimView(Context context, AttributeSet attrs) {
        super(context, attrs);
        mPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        mPaint.setColor(Color.BLUE);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        if (currentPoint == null) {
            currentPoint = new Point(RADIUS, RADIUS);
            drawCircle(canvas);
            startAnimation();
        } else {
            drawCircle(canvas);
        }
    }

    private void drawCircle(Canvas canvas) {
        float x = currentPoint.getX();
        float y = currentPoint.getY();
        canvas.drawCircle(x, y, RADIUS, mPaint);
    }

    private void startAnimation() {
        Point startPoint = new Point(RADIUS, RADIUS);//左上角
        Point endPoint = new Point(getWidth() - RADIUS, getHeight() - RADIUS);//右下角
        ValueAnimator anim = ValueAnimator.ofObject(new PointEvaluator(), startPoint, endPoint);
        anim.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                currentPoint = (Point) animation.getAnimatedValue();
//                mPaint.setColor(Color.BLUE);
                invalidate();
            }
        });

        ObjectAnimator anim2 = ObjectAnimator.ofObject(this,//Target目标对象
                                                        "color",//propertyName描述类型
                                                        new ColorEvaluator(),//评估监听器
                                                        "#0000FF",    //开始值
                                                        "#FF0000");  //结束值
        anim2.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                String colorStr = (String) animation.getAnimatedValue();
//                int a = Integer.parseInt(colorStr);
                Log.i("ObjAnimView", "onAnimationUpdate: "+colorStr);
//                int r = Integer.valueOf("0x"+str.substring(1, 3));
//                int g = Integer.valueOf("0x"+str.substring(3, 5));
//                int b = Integer.valueOf("0x"+str.substring(5, 7));
//                mPaint.setColor(a);
            }
        });



        AnimatorSet animSet = new AnimatorSet();
        animSet.play(anim).with(anim2);
        animSet.setDuration(5000);
        animSet.start();
    }
}
