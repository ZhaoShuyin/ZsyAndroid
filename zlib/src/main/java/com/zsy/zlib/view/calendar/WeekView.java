package com.zsy.zlib.view.calendar;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.View;

/**
 * 顶部显示的星期Layout
 */
public class WeekView extends View {

    private String[] mWeeks = {"S", "M", "T", "W", "T", "F", "S"};

    // 字体大小
    private float mWeekTextSize = 16;
    // 字体颜色
    private int mWeekTextColor = Color.GRAY;
    // 背景颜色
    private int mWeekBgColor = Color.WHITE;

    private DisplayMetrics mMetrics;
    private final Paint mPaint;

    public WeekView(Context context, AttributeSet attrs) {
        super(context, attrs);
        // 获取手机屏幕参数
        mMetrics = getResources().getDisplayMetrics();
        mWeekTextSize = mWeekTextSize * mMetrics.scaledDensity;
        // 创建画笔
        mPaint = new Paint();
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        int widthSize = MeasureSpec.getSize(widthMeasureSpec);
        int widthMode = MeasureSpec.getMode(widthMeasureSpec);

        int heightSize = MeasureSpec.getSize(heightMeasureSpec);
        int heightMode = MeasureSpec.getMode(heightMeasureSpec);

        if (heightMode == MeasureSpec.AT_MOST) {
            heightSize = mMetrics.densityDpi * 30;
        }
        if (widthMode == MeasureSpec.AT_MOST) {
            widthSize = mMetrics.densityDpi * 300;
        }
        setMeasuredDimension(widthSize, heightSize);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        int width = getWidth();
        int height = getHeight();

        // 画背景
        mPaint.setColor(mWeekBgColor);
        canvas.drawRect(0, 0, width, height, mPaint);

        // 画星期
        mPaint.setStyle(Paint.Style.FILL);
        mPaint.setTextSize(mWeekTextSize);
        mPaint.setColor(mWeekTextColor);
        int columnWidth = width / 7;
        for (int i = 0; i < mWeeks.length; i++) {
            String text = mWeeks[i];
            int fontWidth = (int) mPaint.measureText(text);
            int startX = columnWidth * i + (columnWidth - fontWidth) / 2;
            int startY = (int) (height / 2 - (mPaint.ascent() + mPaint.descent()) / 2);
            canvas.drawText(text, startX, startY, mPaint);
        }
    }

}
