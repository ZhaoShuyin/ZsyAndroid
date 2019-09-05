package com.zsy.android.layout.recyclerview.flow;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.zsy.android.R;

import java.util.Random;

/**
 * Title: ZsyAndroid
 * <p>
 * Description:流式布局RecyclerView
 * </p>
 *
 * @author Zsy
 * @date 2019/7/6 13:39
 */

public class FlowRecyclerViewActivity extends Activity {


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recycler_flow);
        RecyclerView recyclerView = findViewById(R.id.rec_flow);
        recyclerView.setLayoutManager(new StaggeredGridLayoutManager(3,StaggeredGridLayoutManager.VERTICAL));
    }

    Random random = new Random();
    String string = "一二三四五六七八九十";

    class Adapter extends RecyclerView.Adapter {
        @NonNull
        @Override
        public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            TextView itemView = new TextView(FlowRecyclerViewActivity.this);
            return new Holder(itemView);
        }

        @Override
        public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
            Holder holder1 = (Holder) holder;
            holder1.textView.setText(string.substring(0, random.nextInt(9) + 1));
        }

        @Override
        public int getItemCount() {
            return 100;
        }
    }


    class Holder extends RecyclerView.ViewHolder {
        TextView textView;

        public Holder(View itemView) {
            super(itemView);
            textView = (TextView) itemView;
            textView.setTextSize(50);
        }
    }

}
