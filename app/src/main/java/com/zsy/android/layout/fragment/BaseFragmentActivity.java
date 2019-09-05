package com.zsy.android.layout.fragment;

import android.app.Fragment;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.View;
import android.widget.FrameLayout;

import com.zsy.android.R;
import com.zsy.android.layout.fragment.samlpe.AppFragment;
import com.zsy.android.layout.fragment.samlpe.V4Fragment;

/**
 * Title: ZsyAndroid
 * <p>
 * Description:Fragment的基本使用(使用FragmentActivity(内部有getSupportFragmentManager()方法))
 * </p>
 *
 * @author Zsy
 * @date 2019/8/23 9:34
 */

public class BaseFragmentActivity extends FragmentActivity {

    FrameLayout frameLayout;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_activity_base);
        frameLayout = findViewById(R.id.fl_fragment1);
        //低版本支持
        support();

        //app版本
        base();

        //
        replace();

    }

    public void getFragmentByTag(View view){
        AppFragment second = (AppFragment) getFragmentManager().findFragmentByTag("second");
        second.show("通过Tag find fragment");
    }


    private void replace() {
        //获取fragmentmanger
        android.app.FragmentManager manager = getFragmentManager();
        //开启事务
        android.app.FragmentTransaction transaction = manager.beginTransaction();
        //通过事务替换fragmetn
        transaction.replace(R.id.fl_fragment, new AppFragment(),"first");
        transaction.replace(R.id.fl_fragment, new AppFragment(),"second");
        transaction.commit();
    }

    private void base() {
        android.app.FragmentManager manager = getFragmentManager();
        android.app.FragmentTransaction transaction = manager.beginTransaction();
        transaction.replace(R.id.fl_fragment1, new AppFragment());
        transaction.commit();
    }

    private void support() {
        //v4包获取FragmentManager的方式跟android.app包中有所不同
        //要使用 getSupportFragmentManager() 想要使用 getSupportFragmentManager()方法
        //当前的activity要继承v4包中的FragmentActivity
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction beginTransaction = fragmentManager.beginTransaction();
        beginTransaction.replace(R.id.fl_fragment2, new V4Fragment());
        beginTransaction.commit();
    }

    /**
     *
     * @param view
     */
    public void shfragment(View view){
        startActivity(new Intent(this,ShowHideFragmentActivity.class));
    }

    /**
     *
     * @param view
     */
    public void vfragment(View view){
        startActivity(new Intent(this,ShowHideFragmentActivity.class));
    }




}
