package cn.azsy.zstokhttp.views;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.ValueAnimator;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.animation.LinearInterpolator;

/**
 * Created by zsy on 2017/6/29.
 */

public class CustomView extends View {
    String TAG = "CustomView";

    public CustomView(Context context) {
        super(context);
        init();
    }

    public CustomView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public CustomView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

   /* public CustomView(Context context) {
        this(context, null);
    }

    public CustomView(Context context, AttributeSet attrs) {
        this(context, null, 0);
    }

    public CustomView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }*/

    private void init() {
        yellowPaint = new Paint();
        yellowPaint.setColor(Color.YELLOW);
        yellowPaint.setStyle(Paint.Style.STROKE);
        yellowPaint.setStrokeWidth(5);

        redPaint = new Paint();
        redPaint.setColor(Color.RED);
        redPaint.setStyle(Paint.Style.STROKE);
        redPaint.setStrokeWidth(5);

        greenPaint = new Paint();
        greenPaint.setColor(Color.GREEN);
        greenPaint.setStyle(Paint.Style.STROKE);
        greenPaint.setStrokeWidth(5);

        bluePaint = new Paint();
        bluePaint.setColor(Color.BLUE);
        bluePaint.setStyle(Paint.Style.STROKE);
        bluePaint.setStrokeWidth(5);
    }

    Paint yellowPaint;
    Paint redPaint;
    Paint greenPaint;
    Paint bluePaint;

    /**
     * 测量
     *
     * @param widthMeasureSpec
     * @param heightMeasureSpec
     */
    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        Log.i(TAG, "onMeasure: " + "widthMeasureSpec==" + widthMeasureSpec + ", heightMeasureSpec==" + heightMeasureSpec);

        int mWidth = getMeasuredWidth();
        int mHight = getMeasuredHeight();
        Log.i(TAG, "mWidth==" + mWidth + ", mHight==" + mHight);

        int widthSpecMode = MeasureSpec.getMode(widthMeasureSpec);//测量View控件宽--模式
        int widthSpecSize = MeasureSpec.getSize(widthMeasureSpec);//测量View控件宽--大小
        int heightSpecMode = MeasureSpec.getMode(heightMeasureSpec);//测量View控件高--模式
        int heightSpecSize = MeasureSpec.getSize(heightMeasureSpec);//测量View控件高--大小
        Log.i(TAG, "<<LuckPanLayout>>测量模式 widthSpecMode == " + widthSpecMode + ", widthSpecSize == " + widthSpecSize +
                ", heightSpecMode == " + heightSpecMode + ", heightSpecSize == " + heightSpecSize);

        setMeasuredDimension(300, 300);//设置this==View的大小
    }

    /**
     * 放置
     *
     * @param changed
     * @param left
     * @param top
     * @param right
     * @param bottom
     */
    @Override
    protected void onLayout(boolean changed, int left, int top, int right, int bottom) {
        super.onLayout(changed, left, top, right, bottom);
        //在屏幕中的坐标
        Log.i(TAG, "onLayout: changed==" + changed + ", left==" + left + ", top==" + top + ", right==" + right + ", bottom==" + bottom);
    }


    /**
     * 绘画
     *
     * @param canvas
     */
    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        Log.i(TAG, "onDraw: ");
        canvas.drawLine(0, 0, 100, 100, yellowPaint);//划线起点---终点

        canvas.drawCircle(100, 100, 80, bluePaint);//画圆

        canvas.drawArc(new RectF(50f, 50f, 150f, 150f), 0, 90, false, redPaint);//画弧

        canvas.drawRect(50, 50, 150, 150, greenPaint);//画矩形
    }

    /**
     * 点击
     *
     * @param event
     * @return
     */
    @Override
    public boolean onTouchEvent(MotionEvent event) {
        Log.i(TAG, "onTouchEvent: ");
        return super.onTouchEvent(event);
    }

    /**
     *
     */
    @Override
    public boolean dispatchTouchEvent(MotionEvent event) {
        Log.i(TAG, "dispatchTouchEvent: ");
        return super.dispatchTouchEvent(event);
    }


    public ValueAnimator valueAnimator;//补间动画只对View有动画


    public void startViewAnim() {
        valueAnimator = ValueAnimator.ofFloat(0f, 1f);
        valueAnimator.setDuration(1000);
        valueAnimator.setInterpolator(new LinearInterpolator());
        valueAnimator.setRepeatCount(10);//动画重复十次
        valueAnimator.setRepeatMode(ValueAnimator.REVERSE);
        valueAnimator.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(Animator animation) {
                super.onAnimationEnd(animation);
                Log.i(TAG, "onAnimationEnd: ");
            }

            @Override
            public void onAnimationStart(Animator animation) {
                super.onAnimationStart(animation);
                Log.i(TAG, "onAnimationStart: ");
            }

            @Override
            public void onAnimationRepeat(Animator animation) {
                super.onAnimationRepeat(animation);
                Log.i(TAG, "onAnimationRepeat: ");
            }
        });
        if (!valueAnimator.isRunning()) {
            valueAnimator.start();
        }
    }


    public void stopViewAnim() {
        if (valueAnimator != null) {
            clearAnimation();

            valueAnimator.setRepeatCount(0);
            valueAnimator.cancel();
            valueAnimator.end();
        }
    }


}
