package com.zsy.android.function;

import android.app.Activity;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.widget.MediaController;
import android.widget.VideoView;

import com.zsy.android.R;

/**
 * Title: ZsyAndroid
 * <p>
 * Description:MediaPlayer的使用
 * </p>
 *
 * @author Zsy
 * @date 2019/8/28 11:13
 */

public class MediaActivity extends Activity {
    private MediaPlayer mediaPlayer;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_media_player);
        mediaPlayer = new MediaPlayer();
    }

    /**
     * 播放网络音乐
     * if(mp.isPlaying()){
     * mp.pause();
     * }else{
     * mp.start();
     * }
     */
    private void netMusic() {
        try {
            mediaPlayer.setDataSource("http://10.0.2.2:8080/xpg.mp3");
            mediaPlayer.prepareAsync();//异步准备
            //给mediaplayer设置一个异步准备好之后的监听器
            mediaPlayer.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
                @Override
                public void onPrepared(MediaPlayer mp) {
                    //当准备好之后会走onPrepared方法
                    mp.start();
                }
            });
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 播放网络视频
     * if(mp.isPlaying()){
     * mp.pause();
     * }else{
     * mp.start();
     * }
     */
    private void netVideo() {
        SurfaceView sf_video = (SurfaceView) findViewById(R.id.sf_video);
        SurfaceHolder holder = sf_video.getHolder();
        try {
            mediaPlayer.setDataSource("http://10.0.2.2:8080/oppo.mp4");
            mediaPlayer.setDisplay(holder);
            mediaPlayer.prepareAsync();
            mediaPlayer.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
                @Override
                public void onPrepared(MediaPlayer mp) {
                    mp.start();
                }
            });
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * VideoView封装了MediaPlayer 继承了SurfaceView 把相关方法暴露出来 使用起来比较方便
     * 大部分控制的方法跟MediaPlayer都是一样的 实际上就是对MediaPlayer的封装
     * VideoView(MediaPlayer)支持格式有限 mp4 avi 3gp / rmvb mkv 都不支持
     * public void pause(View v){
     * if(video.isPlaying()){
     * video.pause();
     * }else{
     * video.start();
     * }
     * }
     */
    private void videoView() {
        final VideoView video = (VideoView) findViewById(R.id.video_view);
        //调用setVideoPath VideoView会帮助创建MediaPlayer 并且调用了异步准备的方法
        video.setVideoPath("http://10.0.2.2:8080/mp4.mp4");
        video.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
            @Override
            public void onPrepared(MediaPlayer mp) {
                video.start();
            }
        });
        video.setMediaController(new MediaController(this));
    }

}
