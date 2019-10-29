package cn.azsy.zstokhttp.game;

import android.app.Activity;
import android.graphics.PixelFormat;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Window;
import android.view.WindowManager;

import cn.azsy.zstokhttp.R;

/**
 * Created by zsy on 2017/7/18.
 */

public class GameActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //设置全屏显示
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        requestWindowFeature(Window.FEATURE_NO_TITLE);

        //保持在最前端
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON,
                WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);

        DisplayMetrics outMetrics = new DisplayMetrics();
        this.getWindowManager().getDefaultDisplay().getMetrics(outMetrics);
        GameSurfaceView.SCREEN_WIDTH = outMetrics.widthPixels;
        GameSurfaceView.SCREEN_HEIGHT = outMetrics.heightPixels;

        Log.i("GameSur", "GameSurfaceView获取屏幕宽=="+outMetrics.widthPixels+"屏幕高=="+outMetrics.heightPixels);

        GameSurfaceView gameView = new GameSurfaceView(this);
        gameView.setBackgroundResource(R.drawable.map);
        gameView.setZOrderOnTop(true);      // 这句不能少
        gameView.getHolder().setFormat(PixelFormat.TRANSPARENT);
        setContentView(gameView);
    }


}
