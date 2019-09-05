package com.zsy.android.layout.viewpager;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.zsy.android.R;

import java.util.ArrayList;
import java.util.List;

/**
 * Title: ZsyAndroid
 * <p>
 * Description:ViewPager的使用
 * </p>
 *
 * @author Zsy
 * @date 2019/8/21 15:11
 */

public class ViewPagerActivity extends Activity {

    ViewPager viewPager;
    List<TextView> imageViews = new ArrayList<>();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_viewpager);
        viewPager = findViewById(R.id.view_pager);
        for (int i = 0; i < 9; i++) {
            TextView textView = new TextView(this);
            textView.setTextSize(50);
            textView.setGravity(Gravity.CENTER);
            textView.setBackgroundResource(R.mipmap.ic_launcher_round);
            imageViews.add(textView);
        }
        viewPager.setAdapter(new VpAdapter());
    }

    class VpAdapter extends PagerAdapter {
        @Override
        public int getCount() {
            return imageViews.size();
        }

        @Override
        public boolean isViewFromObject(@NonNull View view, @NonNull Object object) {
            return view == object;
        }

        @Override
        public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object object) {
            TextView textView = imageViews.get(position);
            container.removeView(textView);
        }

        @NonNull
        @Override
        public Object instantiateItem(@NonNull ViewGroup container, int position) {
            TextView textView = imageViews.get(position);
            textView.setText(String.valueOf(position));
            container.addView(textView);
            String url = (String) textView.getTag();
            return textView;
        }
    }
}
