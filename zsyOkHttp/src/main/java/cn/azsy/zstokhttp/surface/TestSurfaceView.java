package cn.azsy.zstokhttp.surface;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

/**
 * Created by zsy on 2017/7/18.
 */

public class TestSurfaceView extends SurfaceView implements SurfaceHolder.Callback {

    private SurfaceHolder holder;
    private MyThread myThread;
    //MyView的构造方法
    public TestSurfaceView(Context context) {
        super(context);
        holder = this.getHolder();
        holder.addCallback(this);
        myThread = new MyThread(holder);// 创建一个绘图线程
    }

    public TestSurfaceView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    public TestSurfaceView(Context context, AttributeSet attrs) {
        super(context, attrs);
        holder = this.getHolder();
        holder.addCallback(this);
//        myThread = new MyThread(holder);// 创建一个绘图线程
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
//        myThread.isRun = true;
//        myThread.start();
    }

    @Override
    public void surfaceDestroyed(SurfaceHolder holder) {
        System.out.println("22surfaceDestroyed方法调用了");
//        myThread.isRun = false;
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
            while (isRun) {
                Canvas canvas = null;
                try {]

                    synchronized (holder) {
                        canvas = holder.lockCanvas();// 锁定画布，一般在锁定后就可以通过其返回的画布对象Canvas，在其上面画图等操作了。
                        canvas.drawColor(Color.BLACK);// 设置画布背景颜色
                        Paint p = new Paint(); // 创建画笔
                        p.setColor(Color.BLUE);
//                        Rect r = new Rect(100, 50, 300, 250);
//                        canvas.drawRect(r, p);

                        canvas.drawText("这是第" + (count++) + "秒", 100, 310, p);
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

}
