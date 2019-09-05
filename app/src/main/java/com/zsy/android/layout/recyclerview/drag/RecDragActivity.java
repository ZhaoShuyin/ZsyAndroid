package com.zsy.android.layout.recyclerview.drag;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.view.View;

import com.zsy.android.R;

import java.util.ArrayList;
import java.util.List;

/**
 * Title: ZsyAndroid
 * <p>
 * Description:
 * </p>
 *
 * @author Zsy
 * @date 2019/6/26 14:50
 */

public class RecDragActivity extends Activity {
    Activity activity;
    private DragAdapter adapter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        activity = this;
        setContentView(R.layout.activity_recycler_drag);
        initViews();
        List<String> list = new ArrayList<>();
        list.add("1");
        list.add("2");
        list.add("3");
        list.add("4");
        list.add("5");
        list.add("6");
        adapter.addList(list);
        findViewById(R.id.bt_shop_upload_save).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
    }

    private void initViews() {
        RecyclerView recyclerView = (RecyclerView) findViewById(R.id.rec_upload_select);
        recyclerView.setLayoutManager(new GridLayoutManager(this, 3));
        // 长按拖拽打开
        ItemDragHelper callback = new ItemDragHelper() {
            @Override
            public boolean isLongPressDragEnabled() {
                return true;
            }
        };
        ItemTouchHelper helper = new ItemTouchHelper(callback);
        helper.attachToRecyclerView(recyclerView);

        adapter = new DragAdapter();
        recyclerView.setAdapter(adapter);
    }
}
