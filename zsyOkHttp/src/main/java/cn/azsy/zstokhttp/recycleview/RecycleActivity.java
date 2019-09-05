package cn.azsy.zstokhttp.recycleview;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import cn.azsy.zstokhttp.R;

/**
 * Created by zsy on 2017/6/19.
 */

public class RecycleActivity extends AppCompatActivity {

    RecyclerView recyclerView;
    RecyclerView.LayoutManager layoutManager;
    RecAdapter recAdapter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recycle);
        recyclerView = (RecyclerView) findViewById(R.id.rec_view);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.addItemDecoration(new DividerItemDecoration(this,DividerItemDecoration.VERTICAL_LIST));
        recAdapter = new RecAdapter();
        recyclerView.setAdapter(recAdapter);
    }



    class RecAdapter extends RecyclerView.Adapter{

        @Override
        public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(RecycleActivity.this).inflate(R.layout.rec_item, parent, false);
            return new RecViewHolder(view);
        }

        @Override
        public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
            RecViewHolder viewHolder = (RecViewHolder) holder;
            viewHolder.textView.setText("这是条目 "+position);
        }

        @Override
        public int getItemCount() {
            return 10;
        }






    }


    class RecViewHolder extends RecyclerView.ViewHolder{

        TextView textView;

        public RecViewHolder(View itemView) {
            super(itemView);
            textView = (TextView) itemView.findViewById(R.id.rec_item);
        }
    }






}
