package com.zsy.android.layout.listview;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.zsy.android.R;

import java.util.Random;

public class ListAdapter extends BaseAdapter {
    int number = 5;
    Context context;

    public ListAdapter(Context context) {
        this.context = context;
        number = new Random().nextInt(10) + 1;
    }

    @Override
    public int getCount() {
        return number;
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View view;
        if (convertView == null) {
            LayoutInflater inflater = LayoutInflater.from(context);
            view = inflater.inflate(R.layout.item_textview, null);
        } else {
            view = convertView;
        }
        TextView textView = view.findViewById(R.id.tv_item);
        textView.setText("<" + number + "> - " + position);
        return view;
    }
    }