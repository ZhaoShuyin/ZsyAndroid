package com.zsy.android.layout.listview;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.zsy.android.R;

/**
 * Title: ZsyAndroid
 * <p>
 * Description:多层嵌套listview
 * </p>
 *
 * @author Zsy
 * @date 2019/6/24 9:25
 */

public class ListViewNestedActivity extends Activity {
    String TAG = "zsylistview";
    private ListView listView;
    private OutAdapter outAdapter;
    int outNumber = 10;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_listview);
        listView = findViewById(R.id.ls_out);
//        LayoutInflater inflater = ListViewActivity.this.getLayoutInflater();
//        View view = inflater.inflate(R.layout.item, null);
//        listView.addHeaderView(view);
//        listView.addFooterView(view);
        outAdapter = new OutAdapter();
        listView.setAdapter(outAdapter);
        findViewById(R.id.bt_list_refresh).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                outNumber += 5;
                outAdapter.notifyDataSetChanged();
            }
        });
    }

    /**
     * 外层Adapter
     */
    class OutAdapter extends BaseAdapter {
        @Override
        public int getCount() {
            return outNumber;
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
        public int getItemViewType(int position) {
            return position % 2 == 0 ? 0 : 1;
        }

        @Override
        public int getViewTypeCount() {
            return super.getViewTypeCount();
        }


        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            View view;
            int type = getItemViewType(position);
            if (convertView == null) {
                if (type == 0) {
                    LayoutInflater inflater = ListViewNestedActivity.this.getLayoutInflater();
                    view = inflater.inflate(R.layout.item_list_out, parent, false);
                } else {
                    LayoutInflater inflater = ListViewNestedActivity.this.getLayoutInflater();
                    view = inflater.inflate(R.layout.item_grid_out, parent, false);
                }
                Log.i(TAG, position + "-" + type + " getView: convertView == null");
            } else {
                view = convertView;
                Log.i(TAG, position + "-" + type + " getView: convertView == null");
            }
            if (type == 0) {
                CustomListView listView = view.findViewById(R.id.ls_item_inner);
                if (listView != null) {
                    ListAdapter innerAdapter = new ListAdapter(ListViewNestedActivity.this);
                    listView.setAdapter(innerAdapter);
                }
                TextView viewById = view.findViewById(R.id.tv_inner_show);
                viewById.setText("<< " + position + " >>");
            } else {
                CustomGridView gridView = view.findViewById(R.id.gr_item_inner);
                if (gridView != null) {
                    GridAdapter innerAdapter = new GridAdapter(ListViewNestedActivity.this);
                    gridView.setAdapter(innerAdapter);
                }
                TextView viewById = view.findViewById(R.id.tv_inner_show);
                viewById.setText("<< " + position + " >>");
            }
            return view;
        }
    }


}
