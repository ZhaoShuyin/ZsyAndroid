package com.zsy.android.layout.listview;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;
import android.widget.Toast;

import com.zsy.android.R;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Title: ZsyAndroid
 * <p>
 * Description:
 * </p>
 *
 * @author Zsy
 * @date 2019/8/21 9:09
 */

public class SimpleListViewActivity extends Activity {

    private ListView mListView;
    private int i = 0;
    private String TAG = "ListView";

    /**
     * ListView多层嵌套
     */
    public void nested(View view) {
        startActivity(new Intent(this, ListViewNestedActivity.class));
    }


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_listview_simple);
        mListView = findViewById(R.id.ls_list_view);
//        ArrayAdapter();
//        SimpleAdapter();
        BaseAdapter();
    }

    /**
     * 多层数据展示
     */
    private void SimpleAdapter() {
        List<Map<String, String>> data = new ArrayList<Map<String, String>>();
        Map<String, String> data1 = new HashMap<String, String>();
        data1.put("title", "标题1");
        data1.put("content", "内容1");
        data.add(data1);
        Map<String, String> data2 = new HashMap<String, String>();
        data2.put("title", "标题2");
        data2.put("content", "内容2");
        data.add(data2);
        String[] from = new String[]{"title", "content"};
        int[] to = new int[]{R.id.tv_title, R.id.tv_content};
        SimpleAdapter adapter = new SimpleAdapter(this, data, R.layout.item_title_content, from, to);
        mListView.setAdapter(adapter);
        mListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Toast.makeText(SimpleListViewActivity.this, "position==" + position, Toast.LENGTH_SHORT).show();
            }
        });
    }

    /**
     * 简单数据的展示
     */
    private void ArrayAdapter() {
        String[] objects = new String[]{"A", "B", "C", "D"};
        //如果要显示的内容比较简单 只有一个textview需要修改具体的内容可以选择ArrayAdapter 通过这个adapter可以直接展示数据 不用继承BaseAdapter
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, R.layout.item_textview, R.id.tv_item, objects);
        mListView.setAdapter(adapter);
        mListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Toast.makeText(SimpleListViewActivity.this, "position==" + position, Toast.LENGTH_SHORT).show();
            }
        });
    }


    private void BaseAdapter() {
        Myadapter adapter = new Myadapter();
        mListView.setAdapter(adapter);
        mListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Toast.makeText(SimpleListViewActivity.this,
                        "",
                        Toast.LENGTH_SHORT).show();
            }
        });
    }

    /**
     * BaseAdapter
     * 而getItem和getItemId是调用某些函数才会触发的方法，如果不需要使用可以暂时不修改。
     */
    private class Myadapter extends BaseAdapter {
        //通过这个方法可以确定 adapter中 一共要显示多少条数据
        @Override
        public int getCount() {
            return 26;
        }

        @Override
        public int getItemViewType(int position) {
            return position;
        }


        // 来获取当前行数据的,return this.datalist.get(position);也可以这样写
        @Override
        public Object getItem(int position) {
            return null;
        }

        // 它返回的是该postion对应item的id
        //return position;这样写也可以
        @Override
        public long getItemId(int position) {
            return 0;
        }


        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            // 通过这个方法可以获得需要在listview上显示的条目长成什么样子
            //第一个参数position 返回当前条目 在整个listview中的索引
            //第二个参数convertView 可以用来重复使用的旧的view  因为listview中展示的条目大部分情况下 结构都是一样的
            //所以 不用每个条目都创建新的view对象 一旦view对象被滑动除屏幕 这个view的布局就可以被重新使用 目的是节省内存
            TextView textView;
            if (convertView == null) {
                //如果等于空 说明 没有可以被复用的view对象
                textView = new TextView(SimpleListViewActivity.this);
                i++;
                textView.setTag(i);
                textView.setPadding(50, 50, 50, 50);
            } else {
                //说明可以有被复用的view对象
                textView = (TextView) convertView;
            }
            int tag = (int) textView.getTag();
            textView.setText("<" + tag + "> 我是第" + position + "个条目");
            return textView;
        }

    }

}
