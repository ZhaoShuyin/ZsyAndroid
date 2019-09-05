package com.zsy.zlib.view.mapscroll;

import android.content.Context;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

public class ScrollRecyclerView extends RecyclerView {

    public static boolean canScroll = true;
    public float lastY;

    public ScrollRecyclerView(Context context) {
        this(context, null);
    }

    public ScrollRecyclerView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public ScrollRecyclerView(Context context, @Nullable AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }

    @Override
    public boolean onTouchEvent(MotionEvent e) {
        float eY = e.getY();
        switch (e.getAction()) {
            //因为是滚动列表，所以父View的dispatchTouchEvent不会调用Down事件，而是从Move事件开始调用
            case MotionEvent.ACTION_DOWN:
                lastY = eY;
                canScroll = true;
                super.onTouchEvent(e);
                return true;
            case MotionEvent.ACTION_MOVE:
                if (!canScroll) {
                    return false;
                }
                //下滑负  上滑正
                int offset = (int) (lastY - eY);
                lastY = eY;
                canScroll = !isTop() || offset > 0;
                super.onTouchEvent(e);//如果不写，则会不让滑动
                return canScroll;
            case MotionEvent.ACTION_CANCEL:
            case MotionEvent.ACTION_UP:
                canScroll = true;
                super.onTouchEvent(e);
                return true;
        }
        return super.onTouchEvent(e);
    }

    private boolean isTop() {
        LayoutManager manager = getLayoutManager();
        if (manager == null
                || !(manager instanceof LinearLayoutManager)) {
            return false;
        }
        //显示区域最上面一条信息的position
        int visibleItemPosition = ((LinearLayoutManager) manager).findFirstVisibleItemPosition();
        View childView = getChildAt(0);//getChildAt(0)只能获得当前能看到的item的第一个
        return childView != null && visibleItemPosition <= 0 && childView.getTop() >= 0;

    }
}
