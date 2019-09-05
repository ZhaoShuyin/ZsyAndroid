package com.zsy.android.layout.recyclerview;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Rect;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Toast;

import com.zsy.android.R;
import com.zsy.android.layout.recyclerview.drag.RecDragActivity;
import com.zsy.android.layout.recyclerview.flow.FlowRecyclerViewActivity;
import com.zsy.android.layout.recyclerview.group.RecGroupActivity;
import com.zsy.android.layout.recyclerview.adapter.RecOutAdapter;

/**
 * Title: ZsyAndroid
 * <p>
 * Description:
 * </p>
 *
 * @author Zsy
 * @date 2019/6/24 9:32
 */

public class SimpleRecyclerViewActivity extends Activity {

    public void grop(View view) {
        startActivity(new Intent(this,RecGroupActivity.class));
    }

    public void flow(View view) {
        startActivity(new Intent(this, FlowRecyclerViewActivity.class));
    }

    public void drag(View view) {
        startActivity(new Intent(this, RecDragActivity.class));
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.recycler_view);
        RecyclerView recyclerView = findViewById(R.id.rec_recyclerview);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);
        //添加底部滚动监听
        addBottomListener(recyclerView, layoutManager);
        RecOutAdapter adapter = new RecOutAdapter(this);
        recyclerView.setAdapter(adapter);
    }

    /**
     * 添加分割线
     */
    private void addDecoratio(RecyclerView recyclerView) {
        recyclerView.addItemDecoration(new RecyclerView.ItemDecoration() {
            private int mSpace = 15;

            @Override
            public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
                super.getItemOffsets(outRect, view, parent, state);
                outRect.left = mSpace;
                outRect.right = mSpace;
                outRect.bottom = mSpace;
                outRect.top = mSpace;
            }
        });
    }


    /**
     * 添加底部滚动监听
     */
    private void addBottomListener(RecyclerView recyclerView, final LinearLayoutManager layoutManager) {
        recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                if (newState == RecyclerView.SCROLL_STATE_IDLE) {
                    int lastPosition = layoutManager.findLastVisibleItemPosition();
                    if (lastPosition == layoutManager.getItemCount() - 1) {
                        Toast.makeText(SimpleRecyclerViewActivity.this, "到底了", Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });
    }

    /**
     * 设置混合布局
     */
    private void setMuliLayout(GridLayoutManager layoutManager) {
        layoutManager.setSpanSizeLookup(new GridLayoutManager.SpanSizeLookup() {
            @Override
            public int getSpanSize(int position) {
                return position == 0 || position == 1 ? 2 : 1;//第一个,第二个为单独一行
            }
        });
    }

    /**
     * 添加滚动监听
     */
    private void addScrollListener(RecyclerView recyclerView, final GridLayoutManager layoutManager) {
        recyclerView.setOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                int fvp = layoutManager.findFirstVisibleItemPosition();//可见范围内的第一项的位置
                if (fvp != 0) {
                    Toast.makeText(SimpleRecyclerViewActivity.this, "", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(SimpleRecyclerViewActivity.this, "", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

}
