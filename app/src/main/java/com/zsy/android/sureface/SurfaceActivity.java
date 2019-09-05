package com.zsy.android.sureface;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.PixelFormat;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.zsy.android.R;


/**
 * Created by zsy on 2017/6/25.
 */

public class SurfaceActivity extends AppCompatActivity {

    private LinearLayout llContain;
    private MyView myView;

    public void btbt(View view) {
        Toast.makeText(this, "点击按钮", Toast.LENGTH_SHORT).show();
    }

    public void btbt2(View view) {
        myView.myThread.isRun = true;
        myView.myThread.start();
//        if (myView.myThread.isAlive())
    }

    public void btbt3(View view) {
        myView.myThread.isRun = false;
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_surface);
//        setContentView(new GlobalMove(this));
        getWindow().setFormat(PixelFormat.TRANSLUCENT);
        llContain = (LinearLayout) findViewById(R.id.ll_contain);
        TextView textView = new TextView(this);
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(150, 50);
        textView.setLayoutParams(params);
        textView.setText("这是添加的");
        llContain.addView(textView);

        LinearLayout.LayoutParams params2 = new LinearLayout.LayoutParams(400, 400);
        myView = new MyView(this);
        myView.setLayoutParams(params2);
        llContain.addView(myView);
//        myView.myThread.isRun = true;
//        myView.myThread.start();
    }


    // 视图内部类
    class MyView extends SurfaceView implements SurfaceHolder.Callback {

        private SurfaceHolder holder;
        public MyThread myThread;

        //MyView的构造方法
        public MyView(Context context) {
            super(context);
            holder = this.getHolder();
            holder.addCallback(this);
            myThread = new MyThread(holder);// 创建一个绘图线程
        }

        /**
         * SurfaceHolder.Callback的三个方法
         */
        @Override
        public void surfaceChanged(SurfaceHolder holder, int format, int width,
                                   int height) {
            System.out.println("22surfaceChanged方法调用了");

        }

        @Override
        public void surfaceCreated(SurfaceHolder holder) {
            System.out.println("22surfaceCreated方法调用了");
//            myThread.isRun = true;
//            myThread.start();
        }

        @Override
        public void surfaceDestroyed(SurfaceHolder holder) {
            System.out.println("22surfaceDestroyed方法调用了");
//            myThread.isRun = false;
        }

    }


    // 线程内部类
    class MyThread extends Thread {
        private SurfaceHolder holder;
        public boolean isRun;

        public MyThread(SurfaceHolder holder) {
            this.holder = holder;
            isRun = true;
        }

        @Override
        public void run() {
            int count = 0;
            int radius = 0;
            while (isRun) {
                Canvas canvas = null;
                radius += 10;
                try {
                    synchronized (holder) {
                        canvas = holder.lockCanvas();// 锁定画布，一般在锁定后就可以通过其返回的画布对象Canvas，在其上面画图等操作了。
                        canvas.drawColor(Color.BLUE);// 设置画布背景颜色
                        Paint p = new Paint(); // 创建画笔
                        p.setColor(Color.GREEN);
//                        Rect r = new Rect(100, 50, 300, 250);
//                        canvas.drawRect(r, p);
                        if (radius>=200){
                            radius = 10;
                        }
                        canvas.drawCircle(200, 200, radius, p);
                        p.setColor(Color.YELLOW);
//                        canvas.drawText();
                        p.setTextSize(40);
                        canvas.drawText("这是第" + (count++) + "秒", 100, 350, p);
                        Thread.sleep(1000);// 睡眠时间为1秒
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                } finally {
                    if (canvas != null) {
                        holder.unlockCanvasAndPost(canvas);// 结束锁定画图，并提交改变。
                    }
                }
            }
        }
    }


    class MyCount extends CountDownTimer {

        Canvas canvas;
        Paint p;
        int r = 0;

        public MyCount(long millisInFuture, long countDownInterval, Canvas canvas) {
            super(millisInFuture, countDownInterval);
            this.canvas = canvas;
            p = new Paint(); // 创建画笔
            p.setColor(Color.RED);
        }

        @Override
        public void onTick(long millisUntilFinished) {
            r += 100;
            canvas.drawCircle(200, 200, r, p);
        }

        @Override
        public void onFinish() {

        }
    }


}
