package com.zsy.android.function.drawboard;

import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ImageView;

import com.zsy.android.R;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;

/**
 * Title: ZsyAndroid
 * <p>
 * Description:画板
 * </p>
 *
 * @author Zsy
 * @date 2019/8/23 13:27
 */

public class DrawBoardActivity extends Activity {

    private ImageView iv_image;
    private ImageView iv_image2;


    private ImageView ivPic;
    private Bitmap mBitmap;

    private Canvas canvas;
    private Bitmap copy_bm;
    private Paint paint;
    private ImageView ivBoard;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_draw_board);
        ivPic = (ImageView) findViewById(R.id.iv_image);
        ivBoard = findViewById(R.id.iv_board);

        matrix();
        

        draw();//画板

        coverPic();//刮奖
    }

    /**
     * 通过matrix操作图片
     */
    private void matrix() {
        iv_image = (ImageView) findViewById(R.id.iv_right);
        iv_image2 = (ImageView) findViewById(R.id.iv_bottom);

        Bitmap bitmap = BitmapFactory.decodeResource(getResources(), R.drawable.tomcat);
        iv_image.setImageBitmap(bitmap);
        //用要复制的图片的相关信息创建一张新的空图片(参数3: 图片的配置信息)
        Bitmap copy_bm = Bitmap.createBitmap(bitmap.getWidth(), bitmap.getHeight(), bitmap.getConfig());
        Canvas canvas = new Canvas(copy_bm);
        Matrix matrix = new Matrix();
        //rotate 旋转  tranlate 平移  scale 缩放
        matrix.setScale(1f, -1f);//设置比例(传了负数 就会有镜像的效果)
        //多次设置 大小 平移和旋转 需要使用post 每一次setXXX都会清空上一句set的结果
        matrix.postTranslate(0, copy_bm.getHeight());
        canvas.drawBitmap(bitmap, matrix, new Paint());

        iv_image2.setImageBitmap(copy_bm);
    }

    private void draw() {
        Bitmap bitmap = BitmapFactory.decodeResource(getResources(), R.drawable.bg);
        //①创建空图片
        copy_bm = Bitmap.createBitmap(bitmap.getWidth(), bitmap.getHeight(), bitmap.getConfig());
        //用空的图片准备canvas 画板
        canvas = new Canvas(copy_bm);
        //用来做图片处理的 矩阵对象
        Matrix matrix = new Matrix();
        //画笔
        paint = new Paint();
        paint.setStrokeWidth(10);
        //在canvas上把要花的图片 画到创建的空的图片对象上
        canvas.drawBitmap(bitmap, matrix, paint);
        ivBoard.setImageBitmap(copy_bm);

        //给imagview设置了一个触摸监听
        ivBoard.setOnTouchListener(new View.OnTouchListener() {

            private float startX;
            private float startY;

            @Override
            public boolean onTouch(View v, MotionEvent event) {
                int action = event.getAction();
                switch (action) {
                    case MotionEvent.ACTION_DOWN:
                        System.out.println("按下");
                        startX = event.getX();
                        startY = event.getY();
                        break;
                    case MotionEvent.ACTION_MOVE:
                        float stopX = event.getX();
                        float stopY = event.getY();
                        canvas.drawLine(startX, startY, stopX, stopY, paint);
                        startX = stopX;
                        startY = stopY;
                        ivBoard.setImageBitmap(copy_bm);
                        break;
                    case MotionEvent.ACTION_UP:
                        System.out.println("抬起");
                        break;

                }
                return true;
            }
        });
    }

    public void save(View v) {
        try {
            //compress方法 压缩保存图片
            //第一个参数 保存的格式 CompressFormat是一个枚举类 只能取固定的枚举值  CompressFormat.PNG CompressFormat.JPEG
            //第二个参数 保存图片的质量 0 压缩的最厉害 图片体积最小 100质量最高 体积最大
            //第三个参数 保存文件用到的输出流
            FileOutputStream stream = openFileOutput("pic.png", MODE_PRIVATE);
            copy_bm.compress(Bitmap.CompressFormat.PNG, 100, stream);
        } catch (FileNotFoundException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

    /**
     * 多层画板
     */
    private void coverPic() {
        //getResources() 获取资源 就是找到res目录 这个是context上下文的api
        //activity就是上下文 所以可以直接调用getResources()方法
        Bitmap bitmap = BitmapFactory.decodeResource(getResources(), R.drawable.front);
        mBitmap = Bitmap.createBitmap(bitmap.getWidth(), bitmap.getHeight(), bitmap.getConfig());
        //② 准备画板
        Canvas canvas = new Canvas(mBitmap);
        Paint paint = new Paint();
        //③对照着原图 画出来
        canvas.drawBitmap(bitmap, new Matrix(), paint);
        ivPic.setImageBitmap(mBitmap);
        //给imageview设置触摸事件监听器
        ivPic.setOnTouchListener(new View.OnTouchListener() {

            @Override
            public boolean onTouch(View v, MotionEvent event) {
                int action = event.getAction();
                if (action == MotionEvent.ACTION_MOVE) {
                    setTransparent(event.getX(), event.getY());
                    //把修改完的图片设置到imageview上
                }

                //设置为true说明触摸事件被当前的imageview消费掉
                return true;
            }
        });
    }

    private void setTransparent(float x, float y) {
        try {
            for (int i = -6; i <= 6; i++) {
                for (int j = -6; j <= 6; j++) {
                    if (Math.sqrt(i * i + j * j) <= 6) {
                        //根据手指移动的位置 修改图片对应的像素 为透明的
                        mBitmap.setPixel((int) x + i, (int) y + j, Color.TRANSPARENT);
                    }
                }
            }
        } catch (Exception e) {
            // TODO: handle exception
        }
        ivPic.setImageBitmap(mBitmap);
    }


}
