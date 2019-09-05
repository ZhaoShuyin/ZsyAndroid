package com.zsy.android.layout.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;

import com.zsy.android.R;
import com.zsy.android.layout.fragment.samlpe.PeriodFragment;

import java.util.ArrayList;
import java.util.List;

/**
 * Title: ZsyGit
 * <p>
 * Description:
 * </p>
 *
 * @author Zsy
 * @date 2019/5/27 11:24
 */

public class ViewpagerFragmentActivity extends AppCompatActivity {

    ViewPager viewPager;
    private List<PeriodFragment> fragments;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_activity_viewpager);
        viewPager = findViewById(R.id.viewpager);
        fragments = new ArrayList<>();
        fragments.add(new PeriodFragment("1"));
        fragments.add(new PeriodFragment("2"));
        fragments.add(new PeriodFragment("3"));
        FragmentManager fragmentManager = getSupportFragmentManager();
        viewPager.setAdapter(new Adapter(fragmentManager));
    }


    class Adapter extends FragmentPagerAdapter{
        public Adapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            return fragments.get(position);
        }

        @Override
        public int getCount() {
            return fragments.size();
        }
    }

}
