package com.zsy.zlib.view.ecg;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.Rect;
import android.text.TextPaint;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;


import java.io.File;

/**
 * @Title com.pengyang.ecg.view
 * @date 2019/10/23
 * @autor Zsy
 */
public class EcgView3 extends View {

    //左侧标识文字
    private String[] strings = new String[]{"I", "II", "III", "AVR", "AVL", "AVF", "V1", "V2", "V3", "V4", "V5", "V6"};
    private Paint mPaint;                          //图形画笔
    private TextPaint mTPaint;                     //文字画笔
    private Paint mProPaint;                       //进度画笔
    private Path mPath;                            //波形路径
    private int mW, mH;                            //宽度, 高度
    private int slideStart, slideTotal; //开始索引 ,每次点击偏移量
    private float progress = 0.5f;                 //进度
    private boolean mDrawProgress;
    private Rect mProRect;
    private int countW = 10, countH = 24;          //网格数量
    private int mUnitW, mUnitH, mUnitS;            //单元宽高度,滑动触发距离
    private int mProY, mProStartX, mProStopX, mProWidth;          //进度条高度范围,进度条长度
    private int count;
    private float leftScale = 0.1f, slideScale = 0.05f, slideUnit;
    private float mFontHeight;
    private int leftW, chartW;
    private boolean isLoading;
    private String mStrShow;
    private EcgBean mEcgBean;
    private int dataInterval = 5;  //数据间隔

    public EcgView3(Context context) {
        this(context, null);
    }

    public EcgView3(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    private void init() {
        this.setBackgroundColor(0xffffefd5);//设置为心电图纸颜色
        mPath = new Path();                 //图形画笔
        mPaint = new Paint();               //网格画笔
        mPaint.setStyle(Paint.Style.STROKE);
        mPaint.setStrokeWidth(1);
        mPaint.setAntiAlias(true);
        mTPaint = new TextPaint();
        mProPaint = new Paint();
        mProPaint.setColor(0xff00ffff);
        mProPaint.setStyle(Paint.Style.FILL);
        mProPaint.setStrokeWidth(10);
        mProRect = new Rect();
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        mW = w;
        mH = h;
        leftW = (int) (mW * leftScale);         //计算左侧宽度
        chartW = mW - leftW;
        mUnitW = (chartW) / countW;          //横向网格单元长度
        mUnitH = mH / (strings.length + 1);     //竖向网格单元长度

        mProY = mH - mUnitH;
        mProStartX = leftW + 50;
        mProStopX = mW - 50;
        mProWidth = chartW - 100;
        mProRect.set(0, mProY, mW, mH);
        mUnitS = (int) (mW * slideScale);        //滑动触发距离

        mTPaint.setTextSize(mW / 30);           //文字大小
        Paint.FontMetrics left = mTPaint.getFontMetrics();
        mFontHeight = left.descent - left.ascent;//计算文字高度
    }


    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        slideStart = (int) (slideTotal * progress);
        drawGrid(canvas);
        drawProgress(canvas);
        if (mDrawProgress) {
            return;
        }
        if (mEcgBean != null) {
            mPaint.setColor(0xffff0000);            //波形折线颜色值
            mPaint.setStrokeWidth(2);
            drawWave(canvas, mEcgBean.I, 0);
            drawWave(canvas, mEcgBean.II, 1);
            drawWave(canvas, mEcgBean.III, 2);
            drawWave(canvas, mEcgBean.AVR, 3);
            drawWave(canvas, mEcgBean.AVL, 4);
            drawWave(canvas, mEcgBean.AVF, 5);
            drawWave(canvas, mEcgBean.V1, 6);
            drawWave(canvas, mEcgBean.V2, 7);
            drawWave(canvas, mEcgBean.V3, 8);
            drawWave(canvas, mEcgBean.V4, 9);
            drawWave(canvas, mEcgBean.V5, 10);
            drawWave(canvas, mEcgBean.V6, 11);
        } else {
            drawStatus(canvas);
        }
    }

    /**
     * 文字状态显示 (数据加载中 / 数据文件缺失)
     */
    private void drawStatus(Canvas canvas) {
        if (mStrShow == null) {
            return;
        }
        String s = "";
        switch (count % 3) {
            case 0:
                s = mStrShow + " .";
                break;
            case 1:
                s = mStrShow + " ..";
                break;
            case 2:
                s = mStrShow + " ...";
                break;
        }
        mTPaint.setColor(0xff000000);
        mTPaint.setTextSize(mW / 15);
        canvas.drawText(s, mW / 2 - mW / 10, mH / 2, mTPaint);
        if (isLoading) {
            count++;
            postDelayed(new Runnable() {
                @Override
                public void run() {
                    invalidate();
                }
            }, 500);
        }
    }

    /**
     * 初始化数据
     */
    public void initData(CaseBean caseBean) {
        if (caseBean == null || caseBean.xmlFile == null) {
//            mStrShow = getContext().getResources().getString(R.string.view_data_missing);
            invalidate();
            return;
        }
        final File file = new File(caseBean.xmlFile);
        if (!file.exists()) {
//            mStrShow = getContext().getResources().getString(R.string.view_data_missing);
            invalidate();
            return;
        }
//        mStrShow = getContext().getResources().getString(R.string.view_data_loading);
        isLoading = true;
        invalidate();
        //开启子线程加载数据
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
//                    mEcgBean = XmlUtil.parse(file,5);
                    slideTotal = mEcgBean.I.length;
                    if (slideTotal / dataInterval < chartW) {             //数据过小,不需翻页
                        slideStart = 0;
                        slideUnit = 0f;
                    } else if (slideTotal / dataInterval > chartW * 200) { //数据超过200页,一页为像素一半值
                        slideStart = 0;
                        slideUnit = chartW * 1f / slideTotal / 2;
                    } else {                                //文件适中 ,一页为 0.5% , 共200页;
                        slideStart = 0;
                        slideUnit = 0.005f;  //每次偏移0.5%
                    }
                    if (isShown()) {
                        isLoading = false;
                        postInvalidate();
                    }
                } catch (Exception e) {
                    e.printStackTrace();
//                    mStrShow = getContext().getResources().getString(R.string.view_data_error);
                    isLoading = false;
                    postInvalidate();
                }
            }
        }).start();
    }

    /**
     * 绘制波形
     */
    private void drawWave(Canvas canvas, int[] ints, int postion) {
        if (mEcgBean == null || ints == null) {
            return;
        }
        mPath.reset();
        int hY = (int) (mUnitH / 2 * (postion * 2 + 1));
        mPath = new Path();
        mPath.moveTo(leftW, hY);
        int u = chartW;
        int x, y;
        int position;
        for (int i = 0; i < chartW; i++) {
            x = i + leftW;
            position = i * dataInterval + slideStart;
            if (position < slideTotal) {
                y = hY + (ints[position] >> 4);
                mPath.lineTo(x, y);
            }
        }
        canvas.drawPath(mPath, mPaint);
    }

    /**
     * 绘制坐标标志
     */
    private void drawProgress(Canvas canvas) {
        mTPaint.setTextSize(20);
        mTPaint.setColor(0xff000000);
        int bottomY = mH - mUnitH / 2;
        canvas.drawText((int) (progress * 100) + "%", leftW / 3, bottomY, mTPaint);
        mProPaint.setColor(0xffdddddd);
        canvas.drawLine(mProStartX, bottomY, mProStopX, bottomY, mProPaint);
        mProPaint.setColor(0xff0000ff);
        canvas.drawLine(mProStartX, bottomY, mProStartX + progress * mProWidth, bottomY, mProPaint);
    }


    /**
     * 绘制网格
     */
    private void drawGrid(Canvas canvas) {
        mTPaint.setColor(0xff009900);       //左边文字颜色
        mTPaint.setTextSize(mW / 20);
        //横线及左侧标识
        String s;
        float w;
        for (int i = 0; i < (strings.length) * 2; i++) {
            int y = mUnitH * i / 2;
            if (i % 2 == 0) {
                mPaint.setColor(0xff000000);
            } else {
                mPaint.setColor(0xffdddddd);
                s = strings[i / 2];
                w = mTPaint.measureText(s);
                canvas.drawText(s, (leftW - w) / 2, y + mFontHeight / 2, mTPaint);
            }
            canvas.drawLine(leftW, y, mW, y, mPaint);
        }
        //竖线
        mTPaint.setTextSize(15);
        mTPaint.setColor(0xff0000ff);
        for (int i = 0; i <= countW; i++) {
            int x = mUnitW * i + leftW;
            canvas.drawLine(x, 0, x, mH - mUnitH, mPaint);
            canvas.drawText(String.valueOf(slideStart + i), x, mH - mUnitH, mTPaint);
        }

    }

    int sX, sY;
    int mX, mY;
    int tX, tY;
    int vX, vY;
    int uX, uY;

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        /*if (mEcgBean == null) {
            return false;
        }*/
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                mDrawProgress = true;
                sX = (int) event.getX();
                sY = (int) event.getY();
                tX = sX;
                tY = sY;
                break;
            case MotionEvent.ACTION_MOVE:
                mX = (int) event.getX();
                mY = (int) event.getY();
                vX = Math.abs(mX - tX);
                vY = Math.abs(mY - tY);
                if (vX < vY) {         //竖向滑动不处理
                    return false;
                }
                if (Math.abs(mX - sX) > mUnitS && judgePro(tX, tY, mX, mY)) {
                    progress = (mX - mProStartX) * 1f / mProWidth;
                    if (progress < 0f) {
                        progress = 0f;
                    }
                    if (progress > 1f) {
                        progress = 1f;
                    }
                    sX = mX;
                    sY = mY;
                    Log.e("daohelper", "移动  progress: " + progress);
                    invalidate();
                }
                tX = mX;
                tY = mY;
                break;
            case MotionEvent.ACTION_UP:
                uX = (int) event.getX();
                uY = (int) event.getY();
                mDrawProgress = false;
                if (uY < mProY) {
                    if (uX < (mW / 2 + leftW / 2)) {
                        progress -= slideUnit;
                        Log.e("daohelper", "向前................");
                    } else {
                        progress += slideUnit;
                        Log.e("daohelper", "向后................");
                    }
                    if (progress < 0) {
                        progress = 0;
                    }
                    if (progress >= 1f) {
                        progress = 1 - slideUnit;
                    }
                }
                invalidate();
                break;
        }
        return true;
    }

    private boolean judgePro(int sX, int sY, int eX, int eY) {
        return sX > leftW && sY > mProY && eX > leftW && eY > mProY;
    }


}
