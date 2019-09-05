package com.zsy.android.function;

import android.app.Activity;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

/**
 * Title: ZsyAndroid
 * <p>
 * Description:AsyncTask的实现类需要定义为静态内部类或者独立类，否则会持有外部类的引用，可能造成内存泄露
 * </p>
 *
 * @author Zsy
 * @date 2019/8/1 14:56
 */

public class AsyncTaskActivity extends Activity {

    private TextView textView;
    private AsyncTask asyncTask;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        LinearLayout linearLayout = new LinearLayout(this);
        linearLayout.setOrientation(LinearLayout.VERTICAL);
        textView = new TextView(this);
        Button button1 = new Button(this);
        button1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                test();
            }
        });
        Button button2 = new Button(this);
        button2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                asyncTask.cancel(true);
                MyAsyncTask myAsyncTask = new MyAsyncTask();
                myAsyncTask.execute(111);
            }
        });
        linearLayout.addView(textView);
        linearLayout.addView(button1);
        linearLayout.addView(button2);
        setContentView(linearLayout);
    }

    public void test() {

        final String TAG = "asyncTask";
        asyncTask = new AsyncTask() {

            //执行之前
            @Override
            protected void onPreExecute() {
                super.onPreExecute();
                Log.e(TAG, "onPreExecute: ");
            }

            //执行完毕
            @Override
            protected void onPostExecute(Object o) {
                super.onPostExecute(o);
                Log.e(TAG, "onPostExecute: " + o);
            }

            @Override
            protected void onProgressUpdate(Object[] values) {
//                super.onProgressUpdate(values);
                Log.e(TAG, "onProgressUpdate: " + values[0]);
            }

            @Override
            protected Object doInBackground(Object[] objects) {
                if (objects!=null)
                Log.e(TAG, "doInBackground: " + objects[0]);
                int total = 0;
                for (int i = 0; i < 20; i++) {
                    try {
                        Thread.sleep(1000);
                        Log.e(TAG, "doInBackground: " + total);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    total++;
                    publishProgress(total);
                }
                return total;
            }

            @Override
            protected void onCancelled(Object o) {
                super.onCancelled(o);
                Log.e(TAG, "onCancelled: " + o);
            }
            @Override
            protected void onCancelled() {
                super.onCancelled();
                Log.e(TAG, "onCancelled: ");
            }
        };
        asyncTask.execute(11);

    }

    //输入参数 = String类型、执行进度 = Integer类型、执行结果 = String类型
    public class MyAsyncTask extends AsyncTask<Integer, Integer, String> {

        final String TAG = "asyncTask";

        //该方法不运行在UI线程中,主要用于异步操作,通过调用publishProgress()方法
        //触发onProgressUpdate对UI进行操作
        @Override
        protected String doInBackground(Integer... params) {
            Log.e(TAG, "doInBackground: " + params[0]);
            int total = 0;
            for (int i = 0; i < 20; i++) {
                try {
                    Thread.sleep(1000);
                    Log.e(TAG, "doInBackground: " + total);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                total++;
            }
            return total+"";
        }

        //该方法运行在UI线程中,可对UI控件进行设置
        @Override
        protected void onPreExecute() {
            Log.e(TAG, "开始执行异步线程: ");
        }


        //在doBackground方法中,每次调用publishProgress方法都会触发该方法
        //运行在UI线程中,可对UI控件进行操作


        @Override
        protected void onProgressUpdate(Integer... values) {
            int value = values[0];
            Log.e(TAG, "onProgressUpdate: "+value );
        }
    }


}
