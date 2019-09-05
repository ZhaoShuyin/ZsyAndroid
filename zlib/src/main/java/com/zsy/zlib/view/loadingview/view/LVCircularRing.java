package com.zsy.zlib.view.loadingview.view;

import android.animation.Animator;
import android.animation.ValueAnimator;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import android.util.AttributeSet;

import com.zsy.zlib.view.loadingview.base.LVBase;

/**
 * Created by lumingmin on 16/6/20.
 * 圆环(幅形)旋转View
 */

public class LVCircularRing extends LVBase {

    private Paint mPaint;
    private Paint mPaintPro;

    private float mWidth = 0f;
    private float mPadding = 0f;
    private float startAngle = 0f;
    RectF rectF = new RectF();

    public LVCircularRing(Context context) {
        super(context);
    }

    public LVCircularRing(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public LVCircularRing(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }


    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);

        if (getMeasuredWidth() > getHeight())
            mWidth = getMeasuredHeight();
        else
            mWidth = getMeasuredWidth();
        mPadding = 5;//辅助粗度
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        //画圆
        canvas.drawCircle(mWidth / 2, mWidth / 2, //圆心坐标
                mWidth / 2 - mPadding,//半径
                mPaintPro);//画笔
        //创建一个矩形
        rectF = new RectF(mPadding, mPadding, mWidth - mPadding, mWidth - mPadding);
        //画弧
        canvas.drawArc(rectF,      //圆弧的外轮廓矩形区域
                startAngle, 100 //起始角度和弧度角度
                , true     //第四个参数是否显示半径,即为扇形
                , mPaint);  //画笔

    }


    private void initPaint() {
        //圆弧画笔
        mPaint = new Paint();
        mPaint.setAntiAlias(true);//抗锯齿效果
//        mPaint.setDither(true)//防抖动效果
        mPaint.setStyle(Paint.Style.FILL_AND_STROKE);//Paint.Style.FILL：填充内部
        // Paint.Style.FILL_AND_STROKE  ：填充内部和描边
        //Paint.Style.STROKE  ：描边
        mPaint.setColor(Color.BLUE);
        mPaint.setStrokeWidth(10);//圆弧粗度

        //圆环画笔
        mPaintPro = new Paint();
        mPaintPro.setAntiAlias(true);
        mPaintPro.setStyle(Paint.Style.STROKE);
        mPaintPro.setColor(Color.RED);
        mPaintPro.setStrokeWidth(10);//圆粗度

    }


    public void setViewColor(int color) {
        mPaintPro.setColor(color);
        postInvalidate();
    }

    public void setBarColor(int color) {
        mPaint.setColor(color);
        postInvalidate();
    }


    @Override
    protected void InitPaint() {
        initPaint();
    }

    @Override
    protected void OnAnimationUpdate(ValueAnimator valueAnimator) {
        float value = (float) valueAnimator.getAnimatedValue();
        startAngle = 360 * value;

        invalidate();
    }

    @Override
    protected void OnAnimationRepeat(Animator animation) {

    }

    @Override
    protected int OnStopAnim() {
        return 0;
    }

    @Override
    protected int SetAnimRepeatMode() {
        return ValueAnimator.RESTART;
    }

    @Override
    protected void AinmIsRunning() {

    }

    @Override
    protected int SetAnimRepeatCount() {
        return ValueAnimator.INFINITE;
    }
}
