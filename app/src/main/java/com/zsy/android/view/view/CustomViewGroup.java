package com.zsy.android.view.view;

import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;

/**
 * Title: ZsyAndroid
 * <p>
 * Description:
 * </p>
 *
 * @author Zsy
 * @date 2019/6/21 10:33
 */
public class CustomViewGroup extends ViewGroup {

    String TAG = "ZsyCustomView";

    public CustomViewGroup(Context context) {
        super(context);
    }

    public CustomViewGroup(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public CustomViewGroup(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    public boolean getTouchscreenBlocksFocus() {
        return super.getTouchscreenBlocksFocus();
    }

    /**
     * 请求不接受拦截事件
     */
    @Override
    public void requestDisallowInterceptTouchEvent(boolean disallowIntercept) {
        super.requestDisallowInterceptTouchEvent(disallowIntercept);
        Log.i(TAG, "requestDisallowInterceptTouchEvent: ");
    }

    /**
     * 第一步
     * 先执行dispatchTouchEvent方法分发触摸事件
     * return false; 只处理down事件
     * retuen true;只在笨方法处理事件(不执行拦截及Touch方法)
     */
    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        Log.i(TAG, "dispatchTouchEvent: ");
        return super.dispatchTouchEvent(ev);
//        return false;
    }

    /**
     * 第二步
     * 执行onInterceptTouchEvent方法,是否拦截事件
     * return true  则不传递事件给子View(本类Touch处理,只处理down)
     * return false 不拦截事件(子View Touch方法执行                   )
     */
    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {
        Log.i(TAG, "onInterceptTouchEvent: ");
//        return super.onInterceptTouchEvent(ev);
//        return true;
        return false;
    }


    /**
     * return true也是子View的处理事件为优先
     */
    @Override
    public boolean onTouchEvent(MotionEvent event) {
        float x = event.getX();
        float y = event.getY();
        Log.i(TAG, "onTouchEvent CustomViewGroup: x==" + x + " y==" + y);
        return super.onTouchEvent(event);
//        return true;
    }

    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        Log.i(TAG, "onLayout: changed==" + changed + " l==" + l + " t==" + t + " r==" + r + " b==" + b);
        View childAt = getChildAt(0);
        childAt.layout(50, 50, 350, 350);
        View childAt1 = getChildAt(1);
        childAt1.layout(50, 450, 350, 750);
    }
}
