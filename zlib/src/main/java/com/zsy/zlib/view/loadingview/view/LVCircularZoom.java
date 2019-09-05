package com.zsy.zlib.view.loadingview.view;

import android.animation.Animator;
import android.animation.ValueAnimator;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;

import com.zsy.zlib.view.loadingview.base.LVBase;

/**
 * 三个小点缩放闪烁
 */

public class LVCircularZoom extends LVBase {

    private Paint mPaint;

    private float mWidth = 0f;
    private float mHigh = 0f;
    private float mMaxRadius = 18;//小圆点半径
    private int circularCount = 3;
    private float mAnimatedValue = 1.0f;
    private int mJumpValue = 0;

    public LVCircularZoom(Context context) {
        super(context);
        float scale = context.getResources().getDisplayMetrics().density;
        mMaxRadius = (int) (15 * scale + 0.5f);
    }

    public LVCircularZoom(Context context, AttributeSet attrs) {
        super(context, attrs);
        float scale = context.getResources().getDisplayMetrics().density;
        mMaxRadius = (int) (15 * scale + 0.5f);
    }

    public LVCircularZoom(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }


    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        mWidth = getMeasuredWidth();
        mHigh = getMeasuredHeight();
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        float circularX = mWidth / circularCount;
        for (int i = 0; i < circularCount; i++) {
            if (i == mJumpValue % circularCount) {
                canvas.drawCircle(i * circularX + circularX / 2f,
                        mHigh / 2,
                        mMaxRadius * mAnimatedValue, mPaint);
            } else {
                canvas.drawCircle(i * circularX + circularX / 2f,
                        mHigh / 2,
                        mMaxRadius, mPaint);
            }
        }
    }


    private void initPaint() {
        mPaint = new Paint();
        mPaint.setAntiAlias(true);
        mPaint.setStyle(Paint.Style.FILL);
        mPaint.setColor(Color.YELLOW);


    }


    public void setViewColor(int color) {
        mPaint.setColor(color);
        postInvalidate();
    }


    @Override
    protected void OnAnimationRepeat(Animator animation) {
        mJumpValue++;
    }

    @Override
    protected void OnAnimationUpdate(ValueAnimator valueAnimator) {
        mAnimatedValue = (float) valueAnimator.getAnimatedValue();
        if (mAnimatedValue < 0.2) {
            mAnimatedValue = 0.2f;
        }
        invalidate();
    }

    @Override
    protected int OnStopAnim() {
        mAnimatedValue = 0f;
        mJumpValue = 0;
        return 0;
    }

    @Override
    protected int SetAnimRepeatMode() {
        return ValueAnimator.RESTART;
    }

    @Override
    protected void InitPaint() {
        initPaint();
    }

    @Override
    protected void AinmIsRunning() {

    }

    @Override
    protected int SetAnimRepeatCount() {
        return ValueAnimator.INFINITE;
    }
}
