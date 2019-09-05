package com.zsy.android;

import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.ResolveInfo;
import android.os.Environment;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import com.zsy.android.base.broadcast.BroadcastActivity;
import com.zsy.android.base.db.SqliteActivity;
import com.zsy.android.base.service.ServiceActivity;
import com.zsy.android.function.net.BaseNetnctivity;
import com.zsy.android.view.animation.AnimationActivity;
import com.zsy.android.function.AsyncTaskActivity;
import com.zsy.android.base.contentprovider.ContentProviderActivity;
import com.zsy.android.function.drawboard.DrawBoardActivity;
import com.zsy.android.api.keyboard.KeyboardActivity;
import com.zsy.android.layout.activity.ActivityMain;
import com.zsy.android.layout.fragment.BaseFragmentActivity;
import com.zsy.android.layout.listview.SimpleListViewActivity;
import com.zsy.android.layout.viewpager.ViewPagerActivity;
import com.zsy.android.function.xml.XmlActivity;
import com.zsy.jni.JniActivity;
import com.zsy.android.api.camera.CameraActivity;
import com.zsy.android.layout.coordinate.CoordinateActivity;
import com.zsy.android.layout.recyclerview.SimpleRecyclerViewActivity;
import com.zsy.android.layout.screenmatch.ScreenMatchActivity;
import com.zsy.android.view.view.CustomViewActivity;

import java.io.File;
import java.io.RandomAccessFile;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    Activity activity;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        activity = this;
        setContentView(R.layout.activity_main);

        //activity
        toActivity(R.id.activity,ActivityMain.class);
        //fragment
        toActivity(R.id.frag_ment,BaseFragmentActivity.class);
        //自定义View
        toActivity(R.id.custom_view,CustomViewActivity.class);
        //屏幕适配
        toActivity(R.id.screenmatch, ScreenMatchActivity.class);
        //服务
        toActivity(R.id.service,ServiceActivity.class);
        //广播
        toActivity(R.id.broadcast,BroadcastActivity.class);
        //内容提供者
        toActivity(R.id.content_provider,ContentProviderActivity.class);
        //数据库
        toActivity(R.id.sqlit,SqliteActivity.class);
        //Jni
        toActivity(R.id.jni,JniActivity.class);
        //相机
        toActivity(R.id.frag_camera,CameraActivity.class);
        //键盘
        toActivity(R.id.keyboard,KeyboardActivity.class);
        //动画
        toActivity(R.id.animation,AnimationActivity.class);
        //网络
        toActivity(R.id.net,BaseNetnctivity.class);
        //ListView
        toActivity(R.id.list_view, SimpleListViewActivity.class);
        //RecyclerView
        toActivity(R.id.recycler_view, SimpleRecyclerViewActivity.class);
        //相对布局(滚动对应)
        toActivity(R.id.coordinate_layout, CoordinateActivity.class);
        //Viewpager
        toActivity(R.id.viewpager,ViewPagerActivity.class);
        //AsyncTask
        toActivity(R.id.asynctask, AsyncTaskActivity.class);
        //
        findViewById(R.id.xml).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(activity, XmlActivity.class));
            }
        });

    }

    /**
     * 跳转到指定Activity
     */
    private void toActivity(int rid, final Class aClass) {
        findViewById(rid).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(activity, aClass));
            }
        });
    }



    /**
     * 其他
     *
     * @param view
     */
    public void other(View view) {
        final boolean[] checkedItems;
        checkedItems = new boolean[]{true, false, false, false, false};

        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("最喜欢的水果");

        final String[] singleitems = {"画板"};
        builder.setSingleChoiceItems(singleitems, 0, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                startA(singleitems[which]);
                dialog.dismiss();
            }
        });
        //待选项被选中状态的数组
        //待选项内容数组
       /*
        final String[] items = {"西瓜", "榴莲", "苹果", "香蕉", "芒果"};
       builder.setMultiChoiceItems(items, checkedItems, new DialogInterface.OnMultiChoiceClickListener() {
            //which 那个条目被点击 isCheced 条目是被选中了 还是没被选中
            @Override
            public void onClick(DialogInterface dialog, int which, boolean isChecked) {
                //通过被选中的状态 修改 状态数组
                checkedItems[which]=isChecked;
                //遍历状态数组 根据被选中的状态决定显示那个数据
                for(int i = 0;i<checkedItems.length;i++){
                    if(checkedItems[i]){
                        System.out.println(items[i]);
                    }
                }
            }
        });*/
        builder.show();
    }

    private void startA(String s) {
        Intent intent = new Intent();
        switch (s) {
            case "画板":
                intent.setClass(this, DrawBoardActivity.class);
                break;
        }
        startActivity(intent);
    }


    private void loadApps() {
        Intent intent = new Intent(Intent.ACTION_MAIN, null);
        intent.addCategory(Intent.CATEGORY_LAUNCHER);
        List<ResolveInfo> apps = getPackageManager().queryIntentActivities(intent, 0);
        //for循环遍历ResolveInfo对象获取包名和类名
        for (int i = 0; i < apps.size(); i++) {
            ResolveInfo info = apps.get(i);
            String packageName = info.activityInfo.packageName;
            CharSequence cls = info.activityInfo.name;
            CharSequence name = info.activityInfo.loadLabel(getPackageManager());
            Log.e("！！！！！", name + "----" + packageName + "----" + cls);
        }
    }

    private void write(String log) {
        String path = Environment.getExternalStorageDirectory().getAbsolutePath();
        File file = new File(path, "loglog.txt");
        RandomAccessFile randomAccessFile = null;
        try {
            randomAccessFile = new RandomAccessFile(file, "rwd");
            randomAccessFile.seek(file.length());
            randomAccessFile.write(log.getBytes());
            randomAccessFile.close();
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

}
