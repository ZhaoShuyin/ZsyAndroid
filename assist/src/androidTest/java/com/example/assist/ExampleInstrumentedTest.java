package com.example.assist;

import android.content.Context;
import android.os.AsyncTask;
import android.support.test.InstrumentationRegistry;
import android.support.test.runner.AndroidJUnit4;
import android.util.Log;

import org.junit.Test;
import org.junit.runner.RunWith;

import static org.junit.Assert.*;

/**
 * Instrumented test, which will execute on an Android device.
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
@RunWith(AndroidJUnit4.class)
public class ExampleInstrumentedTest {
    @Test
    public void useAppContext() throws Exception {
        // Context of the app under test.
//        Context appContext = InstrumentationRegistry.getTargetContext();

//        assertEquals("com.example.assist", appContext.getPackageName());
        final String TAG = "asyncTask";
        AsyncTask asyncTask = new AsyncTask() {

            @Override
            protected void onPreExecute() {
                super.onPreExecute();
                Log.e(TAG, "onPreExecute: ");
            }

            @Override
            protected void onPostExecute(Object o) {
                super.onPostExecute(o);
                Log.e(TAG, "onPostExecute: "+o);
            }

            @Override
            protected void onProgressUpdate(Object[] values) {
                super.onProgressUpdate(values);
                Log.e(TAG, "onProgressUpdate: "+values);
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

            @Override
            protected Object doInBackground(Object[] objects) {
                int total = 0;
                for (int i = 0; i < 20; i++) {
                    try {
                        Thread.sleep(1000);
                        Log.e(TAG, "doInBackground: "+total);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    total++;
                }
                return total;
            }
        };

        asyncTask.execute(11);

    }
}
