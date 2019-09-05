package com.zsy.android.layout.recyclerview.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.zsy.android.R;

/**
 * Title: ZsyAndroid
 * <p>
 * Description:
 * </p>
 *
 * @author Zsy
 * @date 2019/6/25 14:30
 */

public class RecOutAdapter extends RecyclerView.Adapter {

    Context context;

    public RecOutAdapter(Context context) {
        this.context = context;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View inflate = LayoutInflater.from(context).inflate(R.layout.item_recycele_out, parent, false);
        return new OutViewHolder(inflate);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        OutViewHolder holder1 = (OutViewHolder) holder;
        holder1.textView.setText(" 外层 " + position);
        holder1.recyclerView.setAdapter(new RecAdapter(context));
    }

    @Override
    public int getItemCount() {
        return 5;
    }

    class OutViewHolder extends RecyclerView.ViewHolder {
        TextView textView;
        RecyclerView recyclerView;

        public OutViewHolder(View itemView) {
            super(itemView);
            textView = itemView.findViewById(R.id.tv_item_out);
            recyclerView = itemView.findViewById(R.id.rec_out_item);
            recyclerView.setLayoutManager(new LinearLayoutManager(context));
        }
    }

}
