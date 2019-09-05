package com.zsy.zlib.view;

import android.animation.Animator;
import android.animation.ValueAnimator;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.support.annotation.Nullable;
import android.text.TextPaint;
import android.util.AttributeSet;
import android.view.View;
import android.view.animation.LinearInterpolator;

/**
 * Title: ZsyAndroid
 * <p>
 * Description:
 * </p>
 *
 * @author Zsy
 * @date 2019/8/15 10:56
 */

public class DateView extends View {

    private Paint paint;
    private TextPaint textPaint;//文字画笔
    private int mW;
    private int mH;
    private int mCx;
    private int mCy;
    private ValueAnimator animator;
    private int duration = 5000;//动画时间
    private int endAngel = 270;
    private boolean isAni = false;
    private double offset = Math.PI / 4 * 5;//角度偏移90度(其实角度)
    private int radius;
    private String s1;
    private String s2;
    private String s3;

    public DateView(Context context) {
        super(context);
    }

    public DateView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    private void init() {
        paint = new Paint();
        textPaint = new TextPaint();
        animator = ValueAnimator.ofInt(270);
        animator.setDuration(duration);
        animator.setInterpolator(new LinearInterpolator());
        animator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                endAngel = (int) animation.getAnimatedValue();
                invalidate();
            }
        });
        animator.addListener(new Animator.AnimatorListener() {
            @Override
            public void onAnimationStart(Animator animation) {
                isAni = true;
            }

            @Override
            public void onAnimationEnd(Animator animation) {
                isAni = false;
            }

            @Override
            public void onAnimationCancel(Animator animation) {
                isAni = false;
            }

            @Override
            public void onAnimationRepeat(Animator animation) {

            }
        });
    }

    /**
     * 开启动画
     */
    public void animation() {
        if (animator != null) {
            if (animator.isRunning()) {
                return;
            }
            animator.start();
        }
    }

    public void setTextContent(String s1, String s2, String s3) {
        this.s1 = s1;
        this.s2 = s2;
        this.s3 = s3;
        invalidate();
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        mW = MeasureSpec.getSize(widthMeasureSpec);
        mH = MeasureSpec.getSize(heightMeasureSpec);
        mCx = mW / 2;
        mCy = mH / 2;
        radius = Math.min(mW, mH) / 2;
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
//        if (!isAni) {
            //画圆
            paint.setColor(0xff000000);
            paint.setStyle(Paint.Style.STROKE);
            paint.setStrokeWidth(5);
            canvas.drawCircle(mCx, mCy, radius / 4 * 3, paint);
            //画文字
            if (s1 != null) {
                textPaint.setColor(0xff444444);
                textPaint.setTextSize(radius / 3);
                Paint.FontMetrics fontMetrics = textPaint.getFontMetrics();
                float fontH = fontMetrics.descent - fontMetrics.ascent;
                float fontW = textPaint.measureText(s1);
                canvas.drawText(s1, mCx - fontW / 2, mCy + fontH / 2, textPaint);
            }
            if (s2 != null) {
                textPaint.setColor(0xff555555);
                textPaint.setTextSize(radius / 6);
                Paint.FontMetrics fontMetrics = textPaint.getFontMetrics();
                float fontH = fontMetrics.descent - fontMetrics.ascent;
                float fontW = textPaint.measureText(s2);
                canvas.drawText(s2, mCx - fontW / 2, mCy - fontH, textPaint);
            }
            if (s3 != null) {
                textPaint.setColor(0xff555555);
                textPaint.setTextSize(radius / 6);
                Paint.FontMetrics fontMetrics = textPaint.getFontMetrics();
                float fontH = fontMetrics.descent - fontMetrics.ascent;
                float fontW = textPaint.measureText(s3);
                canvas.drawText(s3, mCx - fontW / 2, mCy + fontH * 2, textPaint);
            }
//        }
        //画弧
        paint.setColor(0xffff0000);
        int inRedius = radius;
        int outRedius = radius / 8 * 7;
        double angel = Math.PI * 2 / 360;
        for (int i = 0; i <= endAngel; i++) {
            if (i % 3 == 0) {
                float sY = (int) (Math.sin(angel * i - offset) * outRedius) + mCx;
                float sX = (int) (Math.cos(angel * i - offset) * outRedius) + mCy;
                float eY = (int) (Math.sin(angel * i - offset) * inRedius) + mCx;
                float eX = (int) (Math.cos(angel * i - offset) * inRedius) + mCy;
                canvas.drawLine(sX, sY, eX, eY, paint);
            }
        }
    }
}
