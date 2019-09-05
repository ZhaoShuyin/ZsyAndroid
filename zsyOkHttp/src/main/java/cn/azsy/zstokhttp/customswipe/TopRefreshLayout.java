/*
 * Copyright (C) 2013 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package cn.azsy.zstokhttp.customswipe;

import android.content.Context;
import android.content.res.TypedArray;
import android.support.v4.view.MotionEventCompat;
import android.support.v4.view.ViewCompat;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewConfiguration;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.Animation.AnimationListener;
import android.view.animation.DecelerateInterpolator;
import android.view.animation.Transformation;
import android.widget.AbsListView;

/**
 * 这个是根据SwipeRefreshLayout源码修改后的自定义SwipeRefreshLayout实现自定义头布局刷新
 */
public class TopRefreshLayout extends ViewGroup {

    private static final String LOG_TAG = TopRefreshLayout.class.getSimpleName();

    private static long RETENTION_TIME = 300;//放弃刷新时延迟返回原位置超时时间
    private static float ANIMATION_GRADIENT = 2f;  //时间差值类使用的减速插入要素
    private static final float SCREEN_PRECENT = .1f;       //最大滑动距离第二个控件(屏幕宽度)高度所乘比值
    private static final int TRIGGER_DISTANCE = 120;         //松开引发距离,在OnMeasure中计算dp,在没刷新条高度的情况下使用
    private static final int INVALID_POINTER = -1;         //无效指示器,触摸点击时屏幕指针位置,此处做一常数作对比
    private static int DRAG_DAMPING = 2;     //在刷新条完全滑出并显示时Drag动作的衰减比例
    private static int KEEP_SHOW_STOP_TIME = 1000;     //在自定义中断刷新的情况下停留时间
    private View mTarget; // 就是第二子个布局
    private int mOriginalOffsetTop;
    private OnRefreshListener mListener;//实现刷新三种状态控制的监听控制类
    private int mFrom;
    private boolean mRefreshing = false; //记录刷新是否进行的状态

    private int mTouchSlop; //手的移动要大于这个距离才开始移动控件
    private float triggerDistance = -1;//同步触发距离,在onMeasure中选择屏幕高度的SCREEN_PRECENT*倍或TRIGGER_DISTANCE的<dp>值

    private int animationDuration;//执行动画时间
    private int mCurrentTargetOffsetTop; //记录当前刷新条滑出的位置距离

    private float mInitialMotionY;
    private float mLastMotionY;
    private boolean mIsBeingDragged;
    private int mActivePointerId = INVALID_POINTER;

    // Target is returning to its start offset because it was cancelled or a
    // refresh was triggered.
    private boolean mReturningToStart; //记录正在刷新的状态

    private final DecelerateInterpolator mDecelerateInterpolator;//时间差值类
    private static final int[] LAYOUT_ATTRS = new int[]{android.R.attr.enabled};

    private View mHeaderView;//第一个子布局===刷新条布局
    private int mHeaderHeight;//刷新条布局高度

    private STATUS mStatus = STATUS.NORMAL;//记录当前刷新的三种状态
    private boolean mDisable; // 用来控制控件是否允许滚动

    //使用枚举定义刷新的三种状态,正常等待刷新,准备松开刷新,正在进行刷新
    private enum STATUS {
        NORMAL, LOOSEN, REFRESHING
    }

    /**
     * 自定义的刷新布局滑回屏幕到顶部的动画
     */
    private final Animation animationHint = new Animation() {
        @Override
        public void applyTransformation(float interpolatedTime, Transformation t) {
            int targetTop = 0;
            if (mFrom != mOriginalOffsetTop) {
                targetTop = (mFrom + (int) ((mOriginalOffsetTop - mFrom) * interpolatedTime));
            }
            int offset = targetTop - mTarget.getTop();
            final int currentTop = mTarget.getTop();

            if (offset + currentTop < 0) {
                offset = 0 - currentTop;
            }
            setTargetOffsetTopAndBottom(offset);
        }
    };

    /**
     * 自定义的刷新布局滑出屏幕到出现的动画
     */
    private final Animation animationApear = new Animation() {
        @Override
        public void applyTransformation(float interpolatedTime, Transformation t) {
            int targetTop = 0;
            if (mFrom != mHeaderHeight) {
                targetTop = (mFrom + (int) ((mHeaderHeight - mFrom) * interpolatedTime));
            }
            int offset = targetTop - mTarget.getTop();
            final int currentTop = mTarget.getTop();

            if (offset + currentTop < 0) {
                offset = 0 - currentTop;
            }
            setTargetOffsetTopAndBottom(offset);
            /*mTarget.offsetTopAndBottom(offset);      //设置距离
            mHeaderView.offsetTopAndBottom(offset);  //设置距离
			mCurrentTargetOffsetTop = mTarget.getTop();//重新测量一下当前的距离
			invalidate(); //刷新*/
        }
    };

    /**
     * 监听头布局动画的监听器
     */
    private final AnimationListener anHintListener = new BaseAnimationListener() {
        @Override
        public void onAnimationEnd(Animation animation) {
            // Once the target content has returned to its start position, reset
            // the target offset to 0
            mCurrentTargetOffsetTop = 0;
            mStatus = STATUS.NORMAL;
            mDisable = false;
            Log.i("ani", "动画回复到显示刷新条");
        }
    };

    private final AnimationListener anApearListener = new BaseAnimationListener() {
        @Override
        public void onAnimationEnd(Animation animation) {
            // Once the target content has returned to its start position, reset
            // the target offset to 0
            mCurrentTargetOffsetTop = mHeaderHeight;
            mStatus = STATUS.REFRESHING;
            Log.i("ani", "动画回复到隐藏刷新条");
        }
    };

    /**
     * 停止刷新,隐藏刷新条的任务
     */
    private final Runnable hintRunnale = new Runnable() {
        @Override
        public void run() {
            mReturningToStart = true;//记录正在刷新
            animateOffsetToStartPosition(mCurrentTargetOffsetTop + getPaddingTop(), anHintListener);
            Log.i("ani", "停止刷新任务mReturnToStartPosition执行");
        }
    };

    /**
     * 开始刷新,出现刷新条的任务
     */
    private final Runnable apearRunnable = new Runnable() {
        @Override
        public void run() {
            mReturningToStart = true;//记录正在刷新
            animateOffsetToHeaderPosition(mCurrentTargetOffsetTop + getPaddingTop(), anApearListener);
            Log.i("ani", "开始刷新任务mReturnToStartPosition执行了");
        }
    };

    // Cancel the refresh gesture and animate everything back to its original state
    /**
     * 取消执行动作的任务
     */
    private final Runnable cancelRunnable = new Runnable() {
        @Override
        public void run() {
            mReturningToStart = true;
            animateOffsetToStartPosition(mCurrentTargetOffsetTop + getPaddingTop(), anHintListener);
            Log.i("ani", "任务mCancel");
        }
    };

    /**
     * 构造方法一
     */
    public TopRefreshLayout(Context context) {
        this(context, null);
    }

    /**
     * 构造方法二
     */
    public TopRefreshLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
        mTouchSlop = ViewConfiguration.get(context).getScaledTouchSlop();//手的移动要大于这个距离才开始移动控件====灵敏度
        animationDuration = getResources().getInteger(android.R.integer.config_mediumAnimTime);//动画时间设为系统默认400
        mDecelerateInterpolator = new DecelerateInterpolator(ANIMATION_GRADIENT);//时间差值类

        final TypedArray a = context.obtainStyledAttributes(attrs, LAYOUT_ATTRS);
        setEnabled(a.getBoolean(0, true));
        a.recycle();
    }

    @Override
    public void onAttachedToWindow() {
        super.onAttachedToWindow();
        removeCallbacks(cancelRunnable);
        removeCallbacks(hintRunnale);
        removeCallbacks(apearRunnable);
    }

    @Override
    public void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        removeCallbacks(hintRunnale);
        removeCallbacks(cancelRunnable);
        removeCallbacks(apearRunnable);
    }

    /**
     * 执行刷新条滑回顶部
     */
    private void animateOffsetToStartPosition(int from, AnimationListener listener) {
        mFrom = from;
        animationHint.reset();//重置动画
        animationHint.setDuration(animationDuration);
        animationHint.setAnimationListener(listener);
        animationHint.setInterpolator(mDecelerateInterpolator);
        mTarget.startAnimation(animationHint);
    }

    /**
     * 执行进度条滑出顶部
     */
    private void animateOffsetToHeaderPosition(int from, AnimationListener listener) {
        mFrom = from;
        animationApear.reset();
        animationApear.setDuration(animationDuration);//时间
        animationApear.setAnimationListener(listener);
        animationApear.setInterpolator(mDecelerateInterpolator);
        mTarget.startAnimation(animationApear);
    }

    /**
     * 刷新监听器
     */
    public void setOnRefreshListener(OnRefreshListener listener) {
        mListener = listener;
    }

    /**
     * 设置是否刷新
     */
    public void setRefreshing(boolean refreshing) {
        if (mRefreshing != refreshing) {
            ensureTarget();//确认子控件头布局
            mRefreshing = refreshing;
        }
    }

    /**
     * 判断是否正在刷新
     */
    public boolean isRefreshing() {
        return mRefreshing;
    }

    /**
     * 确定目标View的方法,获取一个子控件就是刷新条布局
     */
    private void ensureTarget() {
        if (mTarget == null) {
            if (getChildCount() > 2 && !isInEditMode()) {
                throw new IllegalStateException(
                        "ZRefreshLayout must host two children");
            }
            mTarget = getChildAt(1);

            // 控制是否允许滚动
            mTarget.setOnTouchListener(new OnTouchListener() {
                @Override
                public boolean onTouch(View v, MotionEvent event) {
                    return mDisable;
                }
            });
            mOriginalOffsetTop = mTarget.getTop() + getPaddingTop();
        }
        if (triggerDistance == -1) {
            if (getParent() != null && ((View) getParent()).getHeight() > 0) {
                final DisplayMetrics metrics = getResources()
                        .getDisplayMetrics();
                triggerDistance = (int) Math.min(((View) getParent()).getHeight() * SCREEN_PRECENT,
                        TRIGGER_DISTANCE * metrics.density);
//                Log.e("LOG_TAG","SCREEN_PRECENT == "+SCREEN_PRECENT+" 计算触发距离1== "+((View) getParent()).getHeight()* SCREEN_PRECENT+" , 2 == "+TRIGGER_DISTANCE * metrics.density);
            }
        }
    }

    @Override
    protected void onLayout(boolean changed, int left, int top, int right, int bottom) {
        final int width = getMeasuredWidth();
        final int height = getMeasuredHeight();
        if (getChildCount() == 0 || getChildCount() == 1) {
            return;
        }
        final View child = getChildAt(1);
        final int childLeft = getPaddingLeft();
        final int childTop = mCurrentTargetOffsetTop + getPaddingTop();
        final int childWidth = width - getPaddingLeft() - getPaddingRight();
        final int childHeight = height - getPaddingTop() - getPaddingBottom();
        //布置第二个子View
        child.layout(childLeft, childTop, childLeft + childWidth, childTop + childHeight);
        //刷新条布局布局到上方隐藏位置
        mHeaderView.layout(childLeft, childTop - mHeaderHeight, childLeft + childWidth, childTop);
    }

    /**
     * onMeasure   测量方法
     * 判断子1.View的数量,2测量刷新条布局高度,3设置第二个子View大小
     */
    @Override
    public void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        if (getChildCount() <= 1) {
            throw new IllegalStateException(
                    "ZRefreshLayout must have the headerview and contentview");
        }
        if (getChildCount() > 2 && !isInEditMode()) {
            throw new IllegalStateException(
                    "ZRefreshLayout can only host two children");
        }
        //获取第一个布局即刷新条高度,定义刷新的距离
        if (mHeaderView == null) {
            mHeaderView = getChildAt(0);
            measureChild(mHeaderView, widthMeasureSpec, heightMeasureSpec);
            mHeaderHeight = mHeaderView.getMeasuredHeight();
            triggerDistance = mHeaderHeight;
            Log.e("LOG_TAG", "triggerDistance = " + triggerDistance);
            final DisplayMetrics metrics = getResources()
                    .getDisplayMetrics();
//            Log.e("LOG_TAG","SCREEN_PRECENT == "+SCREEN_PRECENT+" 计算触发距离1== "+((View) getParent()).getHeight()* SCREEN_PRECENT+" , 2 == "+TRIGGER_DISTANCE * metrics.density);
        }
        //这是为了找出一个视图应该有多大。父在宽度和高度参数中提供约束信息。
        //指定第二个View为matchParent,充满父控件
        getChildAt(1).measure(
                MeasureSpec.makeMeasureSpec(getMeasuredWidth() - getPaddingLeft() - getPaddingRight(), MeasureSpec.EXACTLY),
                MeasureSpec.makeMeasureSpec(getMeasuredHeight() - getPaddingTop() - getPaddingBottom(), MeasureSpec.EXACTLY)
        );
    }

    /**
     * 第二个布局,子控件顶部坐标判断是否可以向上滑动
     */
    public boolean canChildScrollUp() {
        if (android.os.Build.VERSION.SDK_INT < 14) {
            if (mTarget instanceof AbsListView) {
                final AbsListView absListView = (AbsListView) mTarget;
                return absListView.getChildCount() > 0
                        && (absListView.getFirstVisiblePosition() > 0 || absListView
                        .getChildAt(0).getTop() < absListView
                        .getPaddingTop());
            } else {
                return mTarget.getScrollY() > 0;//View顶部位置dayu0
            }
        } else {
            return ViewCompat.canScrollVertically(mTarget, -1);
        }
    }

    /**
     * 拦截触摸事件的处理方法
     */
    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {
        ensureTarget();//确认刷新条布局

        final int action = MotionEventCompat.getActionMasked(ev);

        if (mReturningToStart && action == MotionEvent.ACTION_DOWN) {
            mReturningToStart = false;
        }
        //不做处理1.不可点击 2.正在刷新 3.第二个,子控件是否到顶部  4.正在刷新
        if (!isEnabled() || mReturningToStart || canChildScrollUp() || mStatus == STATUS.REFRESHING) {
            // Fail fast if we're not in a state where a swipe is possible
            return false;
        }

        switch (action) {
            case MotionEvent.ACTION_DOWN:
                mLastMotionY = mInitialMotionY = ev.getY();
                mActivePointerId = MotionEventCompat.getPointerId(ev, 0);
                mIsBeingDragged = false;
                break;

            case MotionEvent.ACTION_MOVE:
                if (mActivePointerId == INVALID_POINTER) {//点击开始时,初始化指针失败
                    Log.e(LOG_TAG, "Got ACTION_MOVE event but don't have an active pointer id.");
                    return false;
                }
                final int pointerIndex = MotionEventCompat.findPointerIndex(ev,
                        mActivePointerId);
                if (pointerIndex < 0) {//指针处于屏幕外,无效指针
                    Log.e(LOG_TAG, "Got ACTION_MOVE event but have an invalid active pointer id.");
                    return false;
                }

                final float y = MotionEventCompat.getY(ev, pointerIndex);
                final float yDiff = y - mInitialMotionY;//获取拖动距离
                if (yDiff > mTouchSlop) {
                    mLastMotionY = y;
                    mIsBeingDragged = true;
                }
                break;

            case MotionEventCompat.ACTION_POINTER_UP:
                onSecondaryPointerUp(ev);
                break;
            case MotionEvent.ACTION_UP:
            case MotionEvent.ACTION_CANCEL:
                mIsBeingDragged = false;
                mActivePointerId = INVALID_POINTER;
                break;
        }
        return mIsBeingDragged;
    }

    //请求父类不要拦截点击事件
    @Override
    public void requestDisallowInterceptTouchEvent(boolean b) {
        // Nope.
    }

    /**
     * 触摸事件的处理方法,主要处理界面到顶后的刷新,,处理在结束时是否执行刷新
     */
    @Override
    public boolean onTouchEvent(MotionEvent ev) {
        final int action = MotionEventCompat.getActionMasked(ev);

        if (mReturningToStart && action == MotionEvent.ACTION_DOWN) {
            mReturningToStart = false;
        }
        //不做处理1.不可点击 2.正在刷新 3.子控件是否到顶部  4.正在刷新
        if (!isEnabled() || mReturningToStart || canChildScrollUp() || mStatus == STATUS.REFRESHING) {
            // Fail fast if we're not in a state where a swipe is possible
            return false;
        }

        switch (action) {
            case MotionEvent.ACTION_DOWN:
                mLastMotionY = mInitialMotionY = ev.getY();
                mActivePointerId = MotionEventCompat.getPointerId(ev, 0);
                mIsBeingDragged = false;
                break;
            case MotionEvent.ACTION_MOVE:
                final int pointerIndex = MotionEventCompat.findPointerIndex(ev,
                        mActivePointerId);
                if (pointerIndex < 0) {
                    Log.e(LOG_TAG,
                            "Got ACTION_MOVE event but have an invalid active pointer id.");
                    return false;
                }
                final float y = MotionEventCompat.getY(ev, pointerIndex);

                final float yDiff = y - mInitialMotionY;

                if (!mIsBeingDragged && yDiff > mTouchSlop) {//没有被拖动且大于系统默认拖动距离
                    mIsBeingDragged = true;
                }
                if (mIsBeingDragged) {
                    // User velocity passed min velocity; trigger a refresh
                    if (yDiff > triggerDistance) {//判断超过顶部
                        if (mStatus == STATUS.NORMAL) {
                            mStatus = STATUS.LOOSEN;//设置为准备刷新状态

                            if (mListener != null) {
                                mListener.onLoose();   //实行准备刷新方法
                            }
                        }
                        updateContentOffsetTop((int) (yDiff));
                    } else {
                        if (mStatus == STATUS.LOOSEN) {
                            mStatus = STATUS.NORMAL;//设置为等待刷新状态
                            if (mListener != null) {
                                mListener.onNormal();  //实行等待刷新方法
                            }
                        }
                        /**
                         * 限制一下,刷新布局下拉的距离
                         */
                        updateContentOffsetTop((int) (yDiff));
                        if (mLastMotionY > y && mTarget.getTop() == getPaddingTop()) {
                            removeCallbacks(cancelRunnable);
                        }
                    }
                    mLastMotionY = y;
                }
                break;

            case MotionEventCompat.ACTION_POINTER_DOWN: {
                final int index = MotionEventCompat.getActionIndex(ev);
                mLastMotionY = MotionEventCompat.getY(ev, index);
                mActivePointerId = MotionEventCompat.getPointerId(ev, index);
                break;
            }

            case MotionEventCompat.ACTION_POINTER_UP:
                onSecondaryPointerUp(ev);
                break;

            case MotionEvent.ACTION_UP:
                if (mStatus == STATUS.LOOSEN) {
                    startRefresh();//判断距离够长执行刷新方法
                } else {
                    updatePositionTimeout();
                }
                mIsBeingDragged = false;
                mActivePointerId = INVALID_POINTER;
                return false;

            case MotionEvent.ACTION_CANCEL:
                updatePositionTimeout();
                mIsBeingDragged = false;
                mActivePointerId = INVALID_POINTER;
                return false;
        }
        return true;
    }


    /**
     * @param targetTop
     */
    private void updateContentOffsetTop(int targetTop) {
        final int currentTop = mTarget.getTop();
        if (targetTop > triggerDistance) {
            // 超过触发松手刷新的距离后，就只显示滑动一半的距离，避免随手势拉动到最底部，用户体验不好
            targetTop = (int) triggerDistance + (int) (targetTop - triggerDistance) / DRAG_DAMPING;
        } else if (targetTop < 0) {
            targetTop = 0;
        }
        setTargetOffsetTopAndBottom(targetTop - currentTop);//减掉超过刷新条矩形的的距离就剩下刷新条高度了
    }

    /**
     * @param offset
     */
    private void setTargetOffsetTopAndBottom(int offset) {
        mTarget.offsetTopAndBottom(offset);      //设置距离
        mHeaderView.offsetTopAndBottom(offset);  //设置距离
        mCurrentTargetOffsetTop = mTarget.getTop();//重新测量一下当前的距离
        invalidate(); //刷新
    }

    //View.post(Runnable)方法。
    // 在post(Runnableaction)方法里，View获得当前线程（即UI线程）的Handler，
    // 然后将action对象post到Handler里。
    // 在Handler里，它将传递过来的action对象包装成一个Message（Message的callback为action），然后将其投入UI线程的消息循环中。
    // 在Handler再次处理该Message时，有一条分支（未解释的那条）就是为它所设，直接调用runnable的run方法。
    // 而此时，已经路由到UI线程里，因此，我们可以毫无顾虑的来更新UI。
    private void updatePositionTimeout() {
        removeCallbacks(cancelRunnable);
        postDelayed(cancelRunnable, RETENTION_TIME);
    }

    private void onSecondaryPointerUp(MotionEvent ev) {
        final int pointerIndex = MotionEventCompat.getActionIndex(ev);
        final int pointerId = MotionEventCompat.getPointerId(ev, pointerIndex);

        if (pointerId == mActivePointerId) {
            // This was our active pointer going up. Choose a new
            // active pointer and adjust accordingly.
            final int newPointerIndex = pointerIndex == 0 ? 1 : 0;
            mLastMotionY = MotionEventCompat.getY(ev, newPointerIndex);
            mActivePointerId = MotionEventCompat.getPointerId(ev,
                    newPointerIndex);
        }
    }

    /**
     * 实现刷新条三种状态的接口
     */
    public interface OnRefreshListener {
        public void onNormal();//正常不刷新状态 ==松手回复隐藏

        public void onLoose(); //准备开始刷新状态==松手刷新

        public void onRefresh();//正在进行刷新

        public void onRefreshStop();//自定义中断停止
    }

    /**
     * 监听动画监听器
     */
    private class BaseAnimationListener implements AnimationListener {
        @Override
        public void onAnimationStart(Animation animation) {
        }

        @Override
        public void onAnimationEnd(Animation animation) {
        }

        @Override
        public void onAnimationRepeat(Animation animation) {
        }
    }


    /**
     * //开始刷新的方法
     */
    public void startRefresh() {
        removeCallbacks(cancelRunnable);
        apearRunnable.run();
        setRefreshing(true);
        mDisable = true;
        if (mListener != null) {
            mListener.onRefresh();//执行刷新方法
        }
    }

    /**
     * //停止刷新的方法
     */
    public void stopRefresh() {
        if (mListener != null && mRefreshing) {
            mListener.onRefreshStop();  //实行中断刷新方法
            postDelayed(new Runnable() {
                @Override
                public void run() {
                    hintRunnale.run();
                }
            }, KEEP_SHOW_STOP_TIME);
        }
    }

    /**
     * 完成刷新的方法
     */
    public void finishRefresh() {
        if (mRefreshing) {
            hintRunnale.run();
        }
    }

    /**
     * 设置刷新条动画时间 单位:毫秒
     */
    public void setAnimationDuration(int duration) {
        animationDuration = duration;
    }

    /**
     * 在刷新条完全滑出显示时,Drag的比值
     */
    public void setDragDamping(int dragRatio) {
        DRAG_DAMPING = dragRatio;
    }

    /**
     * 滑动刷新条触发事件的距离
     */
    public void setTriggerDistance(int distance) {
        triggerDistance = distance;
    }

    public void setMaxRefreshTime(int maxTime) {
        cancelRunnable.run();
//        stopRefresh();
    }

    /**
     * 设置不到刷新距离,放弃刷新时延迟时间
     */
    public void setRetentionTime(int retention) {
        RETENTION_TIME = retention;
    }

    /**
     * 设置动画渐变差值
     */
    public void setAnimationGradient(float gradient){
        ANIMATION_GRADIENT = gradient;
    }

    /**
     * 设置在自定义中断刷新的情况下,停留显示状态的时间
     */
    public void setShowStopTime(int stopTime){
        KEEP_SHOW_STOP_TIME = stopTime;
    }
}
