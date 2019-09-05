package com.zsy.android.api.alarm;

import android.app.Activity;
import android.app.AlarmManager;
import android.app.PendingIntent;
import android.app.Service;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.os.SystemClock;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import com.zsy.android.MainActivity;
import com.zsy.android.R;
import com.zsy.zlib.phone.NotifyUtil;

import java.util.Calendar;

/**
 * Title: ZsyAndroid
 * <p>
 * Description:闹钟服务
 * </p>
 *
 * @author Zsy
 * @date 2019/6/27 16:29
 * 从API 19开始，Alarm的机制都是非准确传递的，操作系统将会转换闹钟 ，来最小化唤醒和电池的使用！
 */

public class AlarmActivity extends Activity {

    private AlarmManager alarmManager;
    private TextView textView;
    private AlarmBroadCast alarmBroadCast;
    private PendingIntent pendingIntent;
    private PendingIntent pendingIntent1;

    private String TAG= "AlarmActivity";;
    Handler handler = new Handler() {
        public boolean going = true;
        public int deration = 0;

        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            if (!going) {
                going = true;
                return;
            }
            switch (msg.what) {
                case 0:
                    deration = 10;
                    textView.setText(deration + "");
                    handler.sendEmptyMessageDelayed(2, 1000);
                    break;
                case 1:
                    deration++;
                    if (deration <= 1000) {
                        textView.setText(deration + "");
                        handler.sendEmptyMessageDelayed(1, 1000);
                    }
                    break;
                case 2:
                    deration--;
                    if (deration >= 0) {
                        textView.setText(deration + "");
                        handler.sendEmptyMessageDelayed(2, 1000);
                    }
                    break;
                case -1:
                    going = false;
                    break;
            }
        }
    };

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_alarm);
        alarmManager = (AlarmManager) getSystemService(Service.ALARM_SERVICE);
        textView = findViewById(R.id.tv_alarm);
        NotifyUtil.init(this);
        alarmBroadCast = new AlarmBroadCast();
        IntentFilter filter = new IntentFilter();
        filter.addAction("zsyrecivcer");
        registerReceiver(alarmBroadCast, filter);
    }

    /**
     * AlarmManager.ELAPSED_REALTIME:
     * 闹钟在手机睡眠状态下不可用，该状态下闹钟使用相对时间（相对于系统启动开始），状态值为3;
     *
     * AlarmManager.ELAPSED_REALTIME_WAKEUP
     * 闹钟在睡眠状态下会唤醒系统并执行提示功能，该状态下闹钟也使用相对时间，状态值为2；
     *
     * AlarmManager.RTC
     * 闹钟在睡眠状态下不可用，该状态下闹钟使用绝对时间，即当前系统时间，状态值为1；
     *
     * AlarmManager.RTC_WAKEUP
     * 表示闹钟在睡眠状态下会唤醒系统并执行提示功能，该状态下闹钟使用绝对时间，状态值为0;
     *
     * AlarmManager.POWER_OFF_WAKEUP
     * 表示闹钟在手机关机状态下也能正常进行提示功能，所以是5个状态中用的最多的状态之一，该状态下闹钟也是用绝对时间，状态值为4；不过本状态好像受SDK版本影响，某些版本并不支持；
     */


    /**
     * @param view
     */
    public void alarmA(View view) {
        long currentTimeMillis = System.currentTimeMillis();
        Intent resultIntent = new Intent(this, AlarmBroadCast.class);
        resultIntent.putExtra("title", "3秒钟后闹铃");
        resultIntent.putExtra("text", "3秒钟后闹铃");
        PendingIntent pendingIntent = PendingIntent.getBroadcast(this,
                0,
                resultIntent,
                PendingIntent.FLAG_UPDATE_CURRENT);
        /**
         * FLAG_CANCEL_CURRENT，如果要创建的PendingIntent已经存在了，那么在创建新的PendingIntent之前，原先已经存在的PendingIntent中的intent将不能使用。
         * FLAG_NO_CREATE，如果要创建的PendingIntent尚未存在，则不创建新的PendingIntent，直接返回null。
         * FLAG_ONE_SHOT，相同的PendingIntent只能使用一次，且遇到相同的PendingIntent时不会去更新PendingIntent中封装的Intent的extra部分的内容。
         * FLAG_UPDATE_CURRENT，如果要创建的PendingIntent已经存在了，那么在保留原先PendingIntent的同时，将原先PendingIntent封装的Intent中的extra部分替换为现在新创建的PendingIntent的intent中extra的内容
         */
        alarmManager.set(AlarmManager.RTC_WAKEUP, currentTimeMillis + 10000, pendingIntent);
        handler.sendEmptyMessage(0);
    }

    /**
     * @param view
     */
    @RequiresApi(api = Build.VERSION_CODES.N)
    public void alarmB(View view) {
        Intent resultIntent = new Intent(this, AlarmBroadCast.class);
        resultIntent.putExtra("title", "间隔5秒钟一个闹铃");
        resultIntent.putExtra("text", "间隔5秒钟一个闹铃");
        PendingIntent pendingIntent = PendingIntent.getBroadcast(this,
                0,
                resultIntent,
                PendingIntent.FLAG_UPDATE_CURRENT);
        long currentTimeMillis = System.currentTimeMillis();
//        alarmManager.setRepeating(AlarmManager.RTC,
//                currentTimeMillis,
//                5,
//                pendingIntent);
        alarmManager.setExact(AlarmManager.RTC, currentTimeMillis+10000, "tag", new AlarmManager.OnAlarmListener() {
            @Override
            public void onAlarm() {
                Log.i(TAG, "onAlarm: !!!");
            }
        },new Handler(){
            @Override
            public void handleMessage(Message msg) {
                super.handleMessage(msg);
                Log.i(TAG, "handleMessage: "+msg.what+" "+msg.obj);
            }
        });
        handler.sendEmptyMessage(1);
    }

    /**
     * @param view
     */
    @RequiresApi(api = Build.VERSION_CODES.M)
    public void alarmC(View view) {
        Intent resultIntent = new Intent(this, AlarmBroadCast.class);
        resultIntent.putExtra("title", "粗略间隔5秒钟一个闹铃");
        resultIntent.putExtra("text", "粗略间隔5秒钟一个闹铃");
        PendingIntent pendingIntent1 = PendingIntent.getBroadcast(this,
                0,
                resultIntent,
                PendingIntent.FLAG_UPDATE_CURRENT);
        long currentTimeMillis = System.currentTimeMillis();
        alarmManager.setExactAndAllowWhileIdle(AlarmManager.ELAPSED_REALTIME_WAKEUP,
                SystemClock.elapsedRealtime(), pendingIntent);
        alarmManager.setInexactRepeating(AlarmManager.RTC, currentTimeMillis + 1000, 5000, pendingIntent1);
        handler.sendEmptyMessage(1);
    }



    /**
     * @param view
     */
    @RequiresApi(api = Build.VERSION_CODES.M)
    public void alarmT(View view) {
        Intent resultIntent = new Intent(this, AlarmBroadCast.class);
        resultIntent.putExtra("title", "粗略间隔5秒钟一个闹铃");
        resultIntent.putExtra("text", "粗略间隔5秒钟一个闹铃");
        PendingIntent pendingIntent1 = PendingIntent.getBroadcast(this,
                0,
                resultIntent,
                PendingIntent.FLAG_UPDATE_CURRENT);
        long currentTimeMillis = System.currentTimeMillis();
        alarmManager.setWindow(AlarmManager.RTC,currentTimeMillis+1000,2000,pendingIntent1);
//        AlarmManager.AlarmClockInfo alarmClockInfo = new AlarmManager.AlarmClockInfo(currentTimeMillis + 1000, pendingIntent1);
//        alarmManager.setAlarmClock(alarmClockInfo,pendingIntent1);
    }

    /**
     *
     * @param view
     */
    public void alarmCanael(View view) {
        handler.sendEmptyMessage(-1);
        Intent resultIntent = new Intent(this, AlarmBroadCast.class);
        resultIntent.putExtra("title", "间隔5秒钟一个闹铃");
        resultIntent.putExtra("text", "间隔5秒钟一个闹铃");
        PendingIntent pendingIntent = PendingIntent.getBroadcast(this,
                0,
                resultIntent,
                PendingIntent.FLAG_UPDATE_CURRENT);
        alarmManager.cancel(pendingIntent);
    }


    public void test() {
        alarmManager = (AlarmManager) getSystemService(Service.ALARM_SERVICE);

        Intent intent = new Intent(this, MainActivity.class);

        final PendingIntent pendingIntent = PendingIntent.getActivity(this, 0, intent, 0);


        TextView textView1 = null;

        textView1.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {

                Calendar currentTime = Calendar.getInstance();

                new TimePickerDialog(AlarmActivity.this, 0,

                        new TimePickerDialog.OnTimeSetListener() {

                            @Override

                            public void onTimeSet(TimePicker view, int hourOfDay, int minute) {

                                //设置当前时间

                                Calendar c = Calendar.getInstance();

                                c.setTimeInMillis(System.currentTimeMillis());

                                // 根据用户选择的时间来设置Calendar对象

                                c.set(Calendar.HOUR, hourOfDay);

                                c.set(Calendar.MINUTE, minute);

                                // ②设置AlarmManager在Calendar对应的时间启动Activity
                                alarmManager.set(AlarmManager.RTC_WAKEUP, c.getTimeInMillis(), pendingIntent);
                                Toast.makeText(AlarmActivity.this, "闹钟已开启！", Toast.LENGTH_SHORT).show();
                            }

                        }, currentTime.get(Calendar.HOUR_OF_DAY), currentTime
                        .get(Calendar.MINUTE), false).show();

            }

        });
    }

}
