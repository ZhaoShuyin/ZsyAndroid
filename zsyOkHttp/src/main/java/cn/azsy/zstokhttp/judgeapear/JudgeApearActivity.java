package cn.azsy.zstokhttp.judgeapear;

import android.app.ActivityManager;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;

import cn.azsy.zstokhttp.MainActivity;
import cn.azsy.zstokhttp.R;

/**
 * Created by zsy on 2017/7/21.
 */

public class JudgeApearActivity extends AppCompatActivity {


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_judge_apear);
        TextView textView = (TextView) findViewById(R.id.tv_apser);
        textView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(JudgeApearActivity.this, MainActivity.class));
            }
        });
    }

    @Override
    protected void onStop() {
        super.onStop();
        if (!isAppOnForeground()) {
            //app 进入后台
            // 全局变量isActive = false 记录当前已经进入后台
            Toast.makeText(this, "进入后台", Toast.LENGTH_SHORT).show();
        }
    }


    @Override
    protected void onResume() {
// TODO Auto-generated method stub
        super.onResume();

//        if (!isActive) {
//            //app 从后台唤醒，进入前台
//
//            //isActive = true;
//        }
        Toast.makeText(this, "恢复显示", Toast.LENGTH_SHORT).show();
    }


    public boolean isAppOnForeground() {
        // Returns a list of application processes that are running on the
        // device

        ActivityManager activityManager = (ActivityManager) getApplicationContext().getSystemService(Context.ACTIVITY_SERVICE);
        String packageName = getApplicationContext().getPackageName();

        List<ActivityManager.RunningAppProcessInfo> appProcesses = activityManager
                .getRunningAppProcesses();
        if (appProcesses == null)
            return false;

        for (ActivityManager.RunningAppProcessInfo appProcess : appProcesses) {
        // The name of the process that this object is associated with.
            if (appProcess.processName.equals(packageName)
                    && appProcess.importance == ActivityManager.RunningAppProcessInfo.IMPORTANCE_FOREGROUND) {
                return true;
            }
        }
        return false;
    }


}
