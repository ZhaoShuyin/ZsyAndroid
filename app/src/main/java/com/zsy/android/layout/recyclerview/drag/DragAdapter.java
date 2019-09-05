package com.zsy.android.layout.recyclerview.drag;

import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.zsy.android.R;

import java.util.List;
import java.util.ArrayList;


public class DragAdapter extends RecyclerView.Adapter<DragAdapter.ViewHolder> implements OnItemMoveListener {


    private List<String> list = new ArrayList<>();
    private String TAG = "DragAdapter";

    /**
     * 添加数据
     */
    public void addList(List<String> list) {
        if (list != null) {
            this.list.addAll(list);
        }
        notifyDataSetChanged();
    }


    public class ViewHolder extends RecyclerView.ViewHolder implements OnDragVHListener {
        private ImageView pic;
        TextView textView;

        public ViewHolder(View root) {
            super(root);
            pic = (ImageView) root.findViewById(R.id.iv_item_upload_pic);
            textView = root.findViewById(R.id.iv_item_upload_delete);
        }

        @Override
        public void onItemSelected() {
            //开始推拽排序,可以设置被拖拽Item的背景
            //itemView.setBackgroundResource();
            Log.i(TAG, "onItemSelected: ");
        }

        @Override
        public void onItemFinish() {
            //推拽排序结束,也可以设置被拖拽Item的背景
            Log.i(TAG, "onItemFinish: ");
        }
    }

    @Override
    public int getItemCount() {
        return list == null ? 0 : list.size();
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        holder.pic.setImageResource(R.mipmap.ic_launcher_round);
        holder.textView.setText(position + "");
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View root = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_upload_pic, parent, false);
        ViewHolder vh = new ViewHolder(root);
        //为Item设置点击事件
        return vh;
    }

    /**
     * 拖拽排序相关
     */
    @Override
    public void onItemMove(int fromPosition, int toPosition) {
        if (list == null || fromPosition >= list.size() || toPosition >= list.size()) {
            return;
        }
        String picPath = list.get(fromPosition);
        list.remove(fromPosition);
        list.add(toPosition, picPath);
        notifyItemMoved(fromPosition, toPosition);
        Log.i(TAG, "onItemMove: fromPosition->" + fromPosition + "-> toPosition" + toPosition);
    }


}
