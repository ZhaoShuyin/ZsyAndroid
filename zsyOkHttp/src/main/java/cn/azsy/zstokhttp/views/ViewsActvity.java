package cn.azsy.zstokhttp.views;

import android.annotation.SuppressLint;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.SeekBar;
import android.widget.Toast;

import cn.azsy.zstokhttp.R;

/**
 * Created by zsy on 2017/6/18.
 */

public class ViewsActvity extends AppCompatActivity {

    private ProgressBar progressBar;
    private Button btStatus;
    private SeekBar seekBar;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_views);
        customView = (CustomView) findViewById(R.id.customview);
        progressBar = (ProgressBar) findViewById(R.id.bar);
//        bar.clearAnimation();
//        bar.
        seekBar = (SeekBar) findViewById(R.id.seekBar);
        btStatus = (Button) findViewById(R.id.bt_status);
        seekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {

            }
            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
                Toast.makeText(ViewsActvity.this, "拖到进度=="+seekBar.getProgress(), Toast.LENGTH_SHORT).show();
            }
        });

    }

    public void setColor(View view){
//        btStatus.setBackgroundColor(0xff00ff00);//在设置为颜色后,选择器失效
//        btStatus.setClickable(true);
        btStatus.setEnabled(!btStatus.isEnabled());
    }

    public void StartPro(View view) {
        Drawable drawable = progressBar.getIndeterminateDrawable();
        Rect bounds = drawable.getBounds();
        Drawable d = getResources().getDrawable(R.drawable.progress_bar1);
        d.setBounds(bounds);
        progressBar.setIndeterminateDrawable(d);

//        progressBar.
//        progressBar.setIndeterminate(true);

    }

    @SuppressLint("NewApi")
    public void StopPro(View view) {
        Drawable drawable = progressBar.getIndeterminateDrawable();
        Rect bounds = drawable.getBounds();
        Drawable d = getResources().getDrawable(R.drawable.loading);
        d.setBounds(bounds);
        progressBar.setIndeterminateDrawable(d);
//        progressBar.setIndeterminate(false);
//        progressBar.setIndeterminate(false);
//        progressBar.setBackground(getResources().getDrawable(R.drawable.loading));
//        progressBar.setBackgroundResource(R.drawable.loading);
//        progressBar.getIndeterminateDrawable().
    }


    private CustomView customView;

    public void startAni(View view){
        if (customView!=null){
            customView.startViewAnim();
        }else{
            Toast.makeText(this, "startAni--kong", Toast.LENGTH_SHORT).show();
            customView = (CustomView) findViewById(R.id.customview);
        }

    }

    public void stopAni(View view){
        if (customView!=null){
            customView.startViewAnim();
        }else{
            Toast.makeText(this, "stopAni--kong", Toast.LENGTH_SHORT).show();
            customView = (CustomView) findViewById(R.id.customview);
        }
    }
}
