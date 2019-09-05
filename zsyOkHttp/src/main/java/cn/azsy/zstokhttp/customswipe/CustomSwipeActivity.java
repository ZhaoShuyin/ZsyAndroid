package cn.azsy.zstokhttp.customswipe;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.TextView;

import cn.azsy.zstokhttp.R;

/**
 * Created by zsy on 2017/6/22.
 */

public class CustomSwipeActivity extends AppCompatActivity implements TopRefreshLayout.OnRefreshListener {

    TopRefreshLayout zRefreshLayout;
    private TextView tvTop ,show;

    int refreshTime = 5;

    Handler handler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what){
                case 1:

                    break;
                case 2:
                    refreshTime = 15;
                    handler.sendEmptyMessageDelayed(3,1000);
                    break;
                case 3:
                    if (refreshTime>0){
                        refreshTime--;
                        tvTop.setText("还有"+refreshTime+"秒刷新完毕");
                        show.setText("还有"+refreshTime+"秒刷新完毕");
                        handler.sendEmptyMessageDelayed(3,1000);
                    }else{
                        zRefreshLayout.finishRefresh();
                    }
                    break;
                case 4:
                    tvTop.setText("发生异常情况");
                    show.setText("发生异常情况");
                    break;
            }
        }
    };

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_custom_swpie);
        zRefreshLayout = (TopRefreshLayout) findViewById(R.id.swip_layout);
        zRefreshLayout.setOnRefreshListener(this);
        tvTop = (TextView) findViewById(R.id.zrefresh_top_text);
        show = (TextView) findViewById(R.id.zrefresh_content_text);
    }

    //放弃刷新
    @Override
    public void onNormal() {
        tvTop.setText("正常刷新状态");
        show.setText("正常刷新状态");
    }

    @Override
    public void onLoose() {
        tvTop.setText("准备刷新状态");
        show.setText("准备刷新状态");
    }

    @Override
    public void onRefresh() {
        tvTop.setText("正在刷新状态");
        show.setText("正在刷新状态");
//        if (zRefreshLayout.isRefreshing()){
//            zRefreshLayout.setRefreshing(false);
//            zRefreshLayout.stopRefresh();
//        }
        handler.sendEmptyMessageDelayed(2,10);
    }

    @Override
    public void onRefreshStop() {
        handler.removeMessages(3);
        tvTop.setText("发生异常情况");
        show.setText("发生异常情况");
//        runOnUiThread();
    }

    public void callRefresh(View view){
        zRefreshLayout.setAnimationDuration(5000);
        zRefreshLayout.startRefresh();
    }

    public void stopRefresh(View view){
        zRefreshLayout.stopRefresh();
    }
    public void cancelRefresh(View view){
        zRefreshLayout.setMaxRefreshTime(0);
    }
}
