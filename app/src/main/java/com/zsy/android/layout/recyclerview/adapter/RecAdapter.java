package com.zsy.android.layout.recyclerview.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.zsy.android.R;

/**
 * Title: ZsyAndroid
 * <p>
 * Description:
 * </p>
 *
 * @author Zsy
 * @date 2019/6/25 14:17
 */

public class RecAdapter extends RecyclerView.Adapter {

    public RecAdapter(Context context) {
        this.context = context;
    }

    Context context;

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View inflate = LayoutInflater.from(context).inflate(R.layout.item_textview, parent, false);
        return new RecViewHolder(inflate);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {

    }

    @Override
    public int getItemCount() {
        return 3;
    }

    class RecViewHolder extends RecyclerView.ViewHolder {
        public RecViewHolder(View itemView) {
            super(itemView);
        }
    }

}
