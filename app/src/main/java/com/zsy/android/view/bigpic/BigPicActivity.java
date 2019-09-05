package com.zsy.android.view.bigpic;

import android.os.Bundle;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.BitmapFactory.Options;
import android.graphics.Point;
import android.view.Menu;
import android.view.View;
import android.widget.ImageView;

import com.zsy.android.R;

/**
 * 加载大图
 */
public class BigPicActivity extends Activity {
    private String path = "mnt/sdcard/big.jpg";
    private ImageView iv_image;
    private int width;
    private int height;

    @SuppressLint("NewApi")
    @SuppressWarnings("deprecation")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        iv_image = (ImageView) findViewById(R.id.iv_image);
        //获取屏幕的宽度和高度
        width = getWindowManager().getDefaultDisplay().getWidth();
        height = getWindowManager().getDefaultDisplay().getHeight();

//      Point outSize = new Point();
//	getWindowManager().getDefaultDisplay().getSize(outSize);
//	int screenWidth = outSize.x;
//	int screenHeight = outSize.y;
//
//	System.out.println("width"+width+"==="+screenWidth);
//	System.out.println("height"+height+"==="+screenHeight);
    }

    public void loadpic(View v) {
        loadpic3();
    }

    public void loadpic1() {
        //用图片的宽高 跟屏幕的宽高比较
        //如果屏幕宽高比较小 把图片压缩到跟屏幕宽高差不多就可以了
        BitmapFactory.Options opts = new Options();
        //inJustDecodeBounds 这个参数设置为true 再调用BitmapFactory.decodeXXX方法
        //并不会把图片真正加载到内存 而是把图片的大小读出来 设置到opts.outHeight 和 opts.outWidth属性中
        opts.inJustDecodeBounds = true;
        Bitmap bitmap = BitmapFactory.decodeFile(path, opts);
        //获取图片的宽度和图片的高度
        int picHeight = opts.outHeight;
        int picWidth = opts.outWidth;
//	   if(bitmap == null){
//		   System.out.println("picheight"+picHeight+"picwide"+picWidth);
//	   }
        opts.inSampleSize = 1;
        if (picHeight > height || picWidth > width) {
            //为了结果跟准确 把宽高转换成float类型 然后用Math.round对结果进行四舍五入
            int heightIndex = Math.round((float) picHeight / (float) height);
            int widthIndex = Math.round((float) picWidth / (float) width);
            //取计算出来的两个数的最大值
            opts.inSampleSize = Math.max(heightIndex, widthIndex);
        }

        //把inJustDecodeBounds 改为false 这样可以用算出的inSampleSize去加载图片到内存
        opts.inJustDecodeBounds = false;
        System.out.println("opts.inSampleSize=" + opts.inSampleSize);
        bitmap = BitmapFactory.decodeFile(path, opts);
        iv_image.setImageBitmap(bitmap);
    }

    public void loadpic2() {
        BitmapFactory.Options opts = new Options();
        //inSampleSize 可以告诉BitmapFactory 在解析图片的时候压缩图片的大小 用来节省内存
        //inSampleSize的值 指的是对图片的宽度 和高度 做相同大小的压缩处理  也就是说 inSampleSize = 2
        //创建出来的位图文件 宽度是原图的1/2 高度 大小也是1/2 最终占用内存是不压缩情况下的1/4
        opts.inSampleSize = 2;
        //加载磁盘上的图片文件 转换成bitmap对象
        Bitmap bitmap = BitmapFactory.decodeFile(path, opts);
        //把bitmap位图文件展示到imageview上
        iv_image.setImageBitmap(bitmap);
    }

    public void loadpic3() {
        //第二种计算inSampleSize 压缩比例的思路 不断去试验 如果有内存溢出 就给它抓住 提高压缩比例 继续试验
        //知道能够正确加载图片位置
        BitmapFactory.Options opts = new Options();
        int i = 1;

        Bitmap bitmap = null;
        for (; ; ) {
            try {
                opts.inSampleSize = i;
                //如果当前的压缩比例能够争取的加载图片 循环就会退出
                bitmap = BitmapFactory.decodeFile(path, opts);
                break;
            } catch (Error e) {
                //如果走到异常了 就提高压缩比例 继续试验
                //inSampleSize 这个值要取2的幂指数 如果不是2的多少次幂
                //系统也会把这个数转化为最近的那个2的多少次幂的值
                i *= 2;
                System.out.println("i = " + i);
            }
        }
        iv_image.setImageBitmap(bitmap);
    }
}
