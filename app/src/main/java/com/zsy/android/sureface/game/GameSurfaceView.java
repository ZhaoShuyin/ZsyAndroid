package com.zsy.android.sureface.game;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.util.Log;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

import com.zsy.android.R;

import java.io.InputStream;


/**
 * 功能描述：
 * 时间：2016/8/4
 * 作者：vision
 */
public class GameSurfaceView extends SurfaceView implements
        SurfaceHolder.Callback {
    private static final String TAG = "GameSur";
    //屏幕宽高
    public static int SCREEN_WIDTH;
    public static int SCREEN_HEIGHT;

    private Context mContext;
    private SurfaceHolder mHolder;
    //最大帧数 (1000 / 30)
    private static final int DRAW_INTERVAL = 100;//每次刷新时间间隔

    private DrawThread mDrawThread;
    private FrameAnimation[] spriteAnimations;//行走动画的集合
    private Sprite mSprite;
    private int spriteWidth = 0;
    private int spriteHeight = 0;
    private float spriteSpeed = (float) ((500 * SCREEN_WIDTH / 480) * 0.001); //刷新图片的位置距离
    private int row = 4;//row横行
    private int col = 4;//column竖列
    private Canvas canvas;

    //构造方法
    public GameSurfaceView(Context context) {
        super(context);
        this.mContext = context;
        mHolder = this.getHolder();
        mHolder.addCallback(this);
        initResources();
        mSprite = new Sprite(spriteAnimations, 0, 0, spriteWidth, spriteHeight, spriteSpeed);
    }

    //初始化资源,创建四种动画资源
    private void initResources() {
        Bitmap[][] spriteImgs = generateBitmapArray(mContext, R.drawable.sprite2, row, col);
        spriteAnimations = new FrameAnimation[row];
        for (int i = 0; i < row; i++) {
            Bitmap[] spriteImg = spriteImgs[i];//每行的图片资源

            FrameAnimation spriteAnimation = new FrameAnimation(spriteImg,//图片Bitmap资源数组
                    new int[]{150, 150, 150, 150},//持续时间资源数组
                    true);//是否重复
            spriteAnimations[i] = spriteAnimation;
        }
    }

    //从文件中获取转换Bitmap对象
    public Bitmap decodeBitmapFromRes(Context context, int resourseId) {
        BitmapFactory.Options opt = new BitmapFactory.Options();
        opt.inPreferredConfig = Bitmap.Config.RGB_565;
        opt.inPurgeable = true;
        opt.inInputShareable = true;

        InputStream is = context.getResources().openRawResource(resourseId);
        return BitmapFactory.decodeStream(is, null, opt);
    }

    //分切Bitmap
    public Bitmap createBitmap(Context context, Bitmap source, int row,
                               int col, int rowTotal, int colTotal) {
        Bitmap bitmap = Bitmap.createBitmap(source,
                (col - 1) * source.getWidth() / colTotal,//起点X坐标
                (row - 1) * source.getHeight() / rowTotal,//起点Y坐标
                source.getWidth() / colTotal,  //X方向长度
                source.getHeight() / rowTotal);//Y方向长度
        return bitmap;
    }

    //循环创建行数乘以列数的Bitmap
    public Bitmap[][] generateBitmapArray(Context context, int resourseId, int row, int col) {
        Bitmap bitmaps[][] = new Bitmap[row][col];
        Bitmap source = decodeBitmapFromRes(context, resourseId);
        this.spriteWidth = source.getWidth() / col;
        this.spriteHeight = source.getHeight() / row;
        for (int i = 1; i <= row; i++) {
            for (int j = 1; j <= col; j++) {
                bitmaps[i - 1][j - 1] = createBitmap(context, source, i, j, row, col);
            }
        }
        if (source != null && !source.isRecycled()) {
            source.recycle();
            source = null;
        }
        return bitmaps;
    }


    @Override
    public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {

    }

    @Override
    public void surfaceCreated(SurfaceHolder holder) {
        if (null == mDrawThread) {
            mDrawThread = new DrawThread();
            mDrawThread.start();
        }
    }

    @Override
    public void surfaceDestroyed(SurfaceHolder holder) {
        if (null != mDrawThread) {
            mDrawThread.stopThread();
        }
    }

    public boolean Moving = false;

    //控制精灵行走的线程任务
    private class DrawThread extends Thread {
        public boolean isRunning = false;

        public DrawThread() {
            isRunning = true;
        }

        public void stopThread() {
            isRunning = false;
            boolean workIsNotFinish = true;
            while (workIsNotFinish) {
                try {
                    this.join();// 保证run方法执行完毕
                } catch (InterruptedException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }
                workIsNotFinish = false;
            }
        }

        @Override
        public void run() {
            long deltaTime = 0;
            long tickTime = 0;
            tickTime = System.currentTimeMillis();

            while (isRunning) {
                canvas = null;
                try {
                    synchronized (mHolder) {
                        canvas = mHolder.lockCanvas();

//                        Resources res=getResources();
//                        Bitmap bmp=BitmapFactory.decodeResource(res, R.drawable.map);
//                        canvas.setBitmap(bmp);

                        //设置方向
                        if (!Moving) {
                            mSprite.setDirection();
                        }
                        //更新精灵位置
                        mSprite.updatePosition(deltaTime);
                        drawSprite(canvas);
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                } finally {
                    if (null != mHolder) {
                        mHolder.unlockCanvasAndPost(canvas);
                    }
                }

                deltaTime = System.currentTimeMillis() - tickTime;
                if (deltaTime < DRAW_INTERVAL) {//小于刷新时间就暂停一下
                    try {
                        Thread.sleep(DRAW_INTERVAL - deltaTime);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
                tickTime = System.currentTimeMillis();

            }
        }
    }


    private void drawSprite(Canvas canvas) {
        //清屏操作
//        canvas.drawColor(Color.BLACK);
//        canvas.drawColor(Color.DKGRAY);
        Paint paint = new Paint();
        paint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.CLEAR));
        canvas.drawPaint(paint);
        paint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.SRC));
        invalidate();
        mSprite.draw(canvas);
    }

    float xX, yY, x, y, difX, difY, endX, endY;

    @Override
    public boolean onTouchEvent(MotionEvent event) {

        switch (event.getAction()) {

            case MotionEvent.ACTION_DOWN:
                Log.i(TAG, "按下");
                xX = event.getX();
                yY = event.getY();
//                if (null != mDrawThread) {
//                    mDrawThread.stopThread();
//                }
//                mSprite.direction = Sprite.RIGHT;
//                newThrea();
                break;
            case MotionEvent.ACTION_MOVE:
                x = event.getX();
                y = event.getY();
                difX = x - xX;
                difY = y - yY;
                move(difX, difY);
                Moving = true;
                Log.i(TAG, "移动difX==" + difX + "difY==" + difY);
                break;
            case MotionEvent.ACTION_UP:
                endX = event.getX();
                endY = event.getY();
                Moving = false;
                Log.i(TAG, "抬起");
                break;
        }
        return true;
    }

    private void move(float difX, float difY) {
        if (Math.abs(difX) > Math.abs(difY)) {
            if (difX > 0) {//向右
                mSprite.direction = Sprite.RIGHT;
            } else {//向左
                mSprite.direction = Sprite.LEFT;
            }
        } else {
            if (difY > 0) {//向下
                mSprite.direction = Sprite.DOWN;
            } else {//向上
                mSprite.direction = Sprite.UP;
            }
        }
    }

    private void newThrea() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                long deltaTime = 0;
                long tickTime = 0;
                tickTime = System.currentTimeMillis();
                while (Moving) {

                    mSprite.updatePosition(deltaTime);
                    drawSprite(canvas);
                    deltaTime = System.currentTimeMillis() - tickTime;
                    if (deltaTime < DRAW_INTERVAL) {//小于刷新时间就暂停一下
                        try {
                            Thread.sleep(DRAW_INTERVAL - deltaTime);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }
                    tickTime = System.currentTimeMillis();
                }
            }
        }) {
        }.start();
    }


}
