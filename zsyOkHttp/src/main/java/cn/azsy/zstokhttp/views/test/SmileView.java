package cn.azsy.zstokhttp.views.test;

import android.animation.Animator;
import android.animation.ValueAnimator;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import android.util.AttributeSet;

/**
 * Created by zsy on 2017/7/13.
 */

public class SmileView extends LVBase {

    private Paint mPaint;
    private float mWidth = 0f;
    RectF rectF = new RectF();
    private float mPadding = 0f;
    private float startAngle = 0f;

    public SmileView(Context context) {
        super(context);
    }

    public SmileView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public SmileView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected void InitPaint() {
        mPaint = new Paint();
        mPaint.setAntiAlias(true);
        mPaint.setStyle(Paint.Style.STROKE);
        mPaint.setColor(Color.BLUE);
        mPaint.setStrokeWidth(dip2px(3));
    }


    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);

        if (getMeasuredWidth() > getHeight())
            mWidth = getMeasuredHeight();
        else
            mWidth = getMeasuredWidth();
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        rectF = new RectF(mPadding, mPadding, mWidth - mPadding, mWidth - mPadding);
        mPaint.setStyle(Paint.Style.STROKE);

        canvas.drawArc(rectF, startAngle, 180, false, mPaint);//第四个参数是否显示半径
    }

    float mAnimatedValue = 0f;
    @Override
    protected void OnAnimationUpdate(ValueAnimator valueAnimator) {
        //获取到父类补间动画对象
        mAnimatedValue = (float) valueAnimator.getAnimatedValue();
        if (mAnimatedValue < 0.5) {//前半段
            startAngle = 720 * mAnimatedValue;
        } else {                   //后半段
            startAngle = 720;
        }
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
        return ValueAnimator.RESTART;//重复进行动画
    }

    @Override
    protected int SetAnimRepeatCount() {
        return ValueAnimator.INFINITE;//最大
    }

    @Override
    protected void AinmIsRunning() {

    }
}
