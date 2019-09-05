package com.zsy.android.layout.recyclerview.group;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.zsy.android.R;

import java.util.ArrayList;
import java.util.List;

/**
 * Title: ZsyAndroid
 * <p>
 * Description:分组RecyclerView
 * </p>
 *
 * @author Zsy
 * @date 2019/6/26 13:37
 */

public class RecGroupActivity extends Activity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recycler_group);
        RecyclerView recyclerView = findViewById(R.id.rec_recyclerview_group);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.addItemDecoration(new StickGroupDecoration(this));
        List<Bean> beanList = new ArrayList<>();
        for (int i = 0; i < 6; i++) {
            beanList.add(new Bean(String.format("第一组%d号", i + 1), "第一组"));
        }
        for (int i = 0; i < 6; i++) {
            beanList.add(new Bean(String.format("第二组%d号", i + 1), "第二组"));
        }
        for (int i = 0; i < 6; i++) {
            beanList.add(new Bean(String.format("第三组%d号", i + 1), "第三组"));
        }
        for (int i = 0; i < 10; i++) {
            beanList.add(new Bean(String.format("第四组%d号", i + 1), "第四组"));
        }

        recyclerView.setAdapter(new RecyclerViewAdapter(this,beanList));
    }
}
