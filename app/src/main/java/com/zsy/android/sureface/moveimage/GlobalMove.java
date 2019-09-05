package com.zsy.android.sureface.moveimage;


import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.util.AttributeSet;
import android.view.SurfaceHolder;
import android.view.SurfaceHolder.Callback;
import android.view.SurfaceView;

import com.zsy.android.R;


public class GlobalMove extends SurfaceView implements Callback, Runnable {
    public final static String TAG = "GlobalMove";
    public static int count = 0;

    private boolean bSurfaceRun = true;
    private SurfaceHolder sh;
    private Thread th;
    private Paint p;
    private Canvas canvas;

    //objects to be moved...
    private Bitmap bmp;
    private int lastX = 0;

    public GlobalMove(Context context, AttributeSet attrs) {//这种写法。。。
        super(context, attrs);
        sh = this.getHolder();
        sh.addCallback(this);
        th = new Thread(this);
        p = new Paint();
        initComponents(context);
    }

    private void initComponents(Context context) {
        bmp = BitmapFactory.decodeResource(context.getResources(), R.mipmap.ic_launcher);
    }

    private void draw() {
        canvas = sh.lockCanvas();
        if (canvas != null && bmp != null) {
            //取消如下四行注释看效果。
            Paint paint = new Paint();
            paint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.CLEAR));
            canvas.drawPaint(paint);
            paint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.SRC));

            canvas.drawBitmap(bmp, lastX + 10, 50, p);
        }

        lastX += 10;
        if (lastX > 400) {
            lastX = 0;
        }

        sh.unlockCanvasAndPost(canvas);
    }

    @Override
    public void run() {
        while (bSurfaceRun) {
            draw();

            try {
                Thread.sleep(100);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }


    ///////////////////////////////////////////////////////////////////////////////////////////////////
    @Override
    public void surfaceCreated(SurfaceHolder holder) {
        th.start();
    }

    @Override
    public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {
    }

    @Override
    public void surfaceDestroyed(SurfaceHolder holder) {
        bSurfaceRun = false;
    }

}