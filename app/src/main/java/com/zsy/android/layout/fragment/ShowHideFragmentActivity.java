package com.zsy.android.layout.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Toast;

import com.zsy.android.R;
import com.zsy.android.layout.fragment.samlpe.PeriodFragment;

/**
 * Title: ZsyGit
 * <p>
 * Description:
 * </p>
 *
 * @author Zsy
 * @date 2019/5/27 11:22
 */

public class ShowHideFragmentActivity extends AppCompatActivity {

    PeriodFragment fragmentA;
    PeriodFragment fragmentB;
    private android.support.v4.app.FragmentManager fragmentManager;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_activity_show_hide);
        //getSupportFragmentManager()//这个是FragmentActivity中的额方法
        fragmentManager = getSupportFragmentManager();

        findViewById(R.id.bt_fragment_a).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(ShowHideFragmentActivity.this, "A", Toast.LENGTH_SHORT).show();
                android.support.v4.app.FragmentTransaction transaction = fragmentManager.beginTransaction();
                if (fragmentA == null) {
                    fragmentA = new PeriodFragment("A");
                    transaction.add(R.id.framlayout, fragmentA);
                }
                if (fragmentB != null) {
                    transaction.hide(fragmentB);
                }
                transaction.show(fragmentA);
                transaction.commit();
            }
        });
        findViewById(R.id.bt_fragment_b).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(ShowHideFragmentActivity.this, "B", Toast.LENGTH_SHORT).show();
                android.support.v4.app.FragmentTransaction transaction = fragmentManager.beginTransaction();
                if (fragmentB == null) {
                    fragmentB = new PeriodFragment("B");
                    transaction.add(R.id.framlayout, fragmentB);
                }
                if (fragmentA != null) {
                    transaction.hide(fragmentA);
                }
                transaction.show(fragmentB);
                transaction.commit();
            }
        });
        findViewById(R.id.bt_fragment_data).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Bundle bundle = new Bundle();
                bundle.putString("key",String.valueOf(System.currentTimeMillis()));
                fragmentA.setArguments(bundle);
            }
        });
    }


    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
    }
}
