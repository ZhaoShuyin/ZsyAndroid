package com.zsy.android.base.service.sample;

import android.app.Service;
import android.content.Intent;
import android.media.MediaRecorder;
import android.os.IBinder;
import android.telephony.PhoneStateListener;
import android.telephony.TelephonyManager;

/**
 * Title: ZsyAndroid
 * <p>
 * Description:通话录音服务
 * </p>
 *
 * @author Zsy
 * @date 2019/8/26 15:26
 */

public class RecordService extends Service {

    @Override
    public IBinder onBind(Intent intent) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        // 电话管理器
        TelephonyManager mananger = (TelephonyManager) getSystemService(TELEPHONY_SERVICE);
        MyPhoneStateListener listener = new MyPhoneStateListener();
        //通过电话管理器监听电话状态
        mananger.listen(listener, PhoneStateListener.LISTEN_CALL_STATE);
    }

    private class MyPhoneStateListener extends PhoneStateListener {
        private MediaRecorder recorder;

        @Override
        public void onCallStateChanged(int state, String incomingNumber) {
            switch (state) {
                case TelephonyManager.CALL_STATE_IDLE:
                    //idle空闲 电话处于空闲状态
                    System.out.println("空闲状态 如果有录音机 要停止录音");
                    //录音机停止
                    if (recorder != null) {
                        recorder.stop();
                        //重置录音机
                        recorder.reset();   // You can reuse the object by going back to setAudioSource() step
                        //释放录音机对象
                        recorder.release(); // Now the object cannot be reused
                    }

                    break;
                case TelephonyManager.CALL_STATE_OFFHOOK:
                    //hook钩子 offhook摘机 接通状态
                    System.out.println("通话状态 如果有录音机 要开始录音");
                    if (recorder != null) {
                        recorder.start();   // Recording is now started
                    }
                    break;
                case TelephonyManager.CALL_STATE_RINGING:
                    //ring 铃声 ringing 响铃状态
                    System.out.println("响铃状态 要准备一个录音机");
                    recorder = new MediaRecorder();
                    //设置音频输入源   MIC麦克风 只能记录自己的声音  VOICE_CALL语音通话 可以记录通话双方的声音
                    recorder.setAudioSource(MediaRecorder.AudioSource.MIC);
                    //设置输出文件格式 设置为3gp 早期手机媒体文件的格式
                    recorder.setOutputFormat(MediaRecorder.OutputFormat.THREE_GPP);
                    //设置音频编码方式AMR_NB amr 手机上用来做手机铃声的音频格式 nb narrow band 窄带
                    recorder.setAudioEncoder(MediaRecorder.AudioEncoder.AMR_NB);
                    //设置保存的文件的路径
                    recorder.setOutputFile(getFilesDir().getAbsolutePath() + "/" + incomingNumber + ".3gp");
                    //开始准备录音
                    try {
                        recorder.prepare();
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    break;
            }
        }
    }
}
