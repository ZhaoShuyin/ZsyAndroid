package com.zsy.zlib.view.mapscroll;

import android.animation.Animator;
import android.animation.ValueAnimator;
import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewConfiguration;
import android.view.ViewGroup;
import android.view.animation.LinearInterpolator;
import android.widget.TextView;

/**
 * ScrollTo和ScrollBy滑动的是view的显示内容，并不改变view的坐标(即：ScrollLayout里的内容)
 * ScrollBy:它是基于当前位置的相对滑动
 * x和y不是坐标点，是偏移量，坐标系是左正上正
 */

public class ScrollLayout extends ViewGroup {

    private TextView handlerView, topView;

    public ScrollLayout(Context context) {
        this(context, null);
    }

    public ScrollLayout(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public ScrollLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context);
    }

    public final static int STATUS_DEFAULT = 0;//默认半展开状态
    public final static int STATUS_EXTEND = 1;//展开状态
    public final static int STATUS_CLOSE = 2;//收缩状态
    public final static int STATUS_ING = 3;//滑动中

    private int touchSlop;//滑动反应距离
    private int slideSlop;//滑动启动展开收缩的范围
    private int offsetTop;//上滑距顶距离(大于距离开始回调颜色渐变)
    private ValueAnimator animator;//属性动画

    private int width;//整个控件宽度
    private int height;//整个控件高度

    private int statusHeight = 0;
    private int emptyY;//半展开状态的顶部预留空间(仅在onLayout直接使用)
    private int offsetExtend;//展开后的顶部Y值
    private int offsetClose;//收起后的顶部Y值
    private int offsetDefault;//半展开后的顶部Y值
    private int child_default_height;//默认半显示的列表高度

    private float dX, dY;//TouchEvent_ACTION_DOWN坐标(dX,dY)
    private float lastY;//TouchEvent最后一次坐标(lastX,lastY)
    private boolean isEventValid = true;//本次touch事件是否有效
    private boolean isMoveValid;//
    private int status;
    private int currentY, finalY;//当前滚动Y值,目标Y值

    private float factor;

    public void setHandlerViewText(String s) {
        if (handlerView != null)
            handlerView.setText(s);
    }

    public void setTopViewText(String s) {
        if (topView != null)
            topView.setText(s);
    }

    public void extend(){
        toggle(STATUS_EXTEND);
    }

    public void defalut(){
        toggle(STATUS_DEFAULT);
    }

    public void close(){
        toggle(STATUS_CLOSE);
    }

    private void init(Context context) {
        //获取比例溢出尺寸
        touchSlop = ViewConfiguration.get(context).getScaledTouchSlop();
        slideSlop = dpToPxInt(context, 45);//启发距离
        offsetTop = dpToPxInt(context, 40);//顶部预留布局高度
        statusHeight = getStatusHeight(context);
        child_default_height = dpToPxInt(getContext(), 290);//半展开高度
        animator = ValueAnimator.ofFloat(0f, 1f);
        animator.setDuration(200);
        animator.setInterpolator(new LinearInterpolator());//线性差速器
        animator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                //从0到1的渐变值变化
                factor = (float) animation.getAnimatedValue();
                //这里打印会先打印出：1，然后再从0-1打印，因为每次在start时，都会先调用stop
                //curY可为负数或正数
                float scrollY = currentY + (finalY - currentY) * factor;
                scrollTo(0, (int) scrollY);//调用View方法实现Layout的位置变化
                postInvalidate();
            }
        });

        animator.addListener(new Animator.AnimatorListener() {
            @Override
            public void onAnimationStart(Animator animation) {
            }

            //动画全部更新完成后再走此方法：addUpdateListener->onAnimationEnd
            @Override
            public void onAnimationEnd(Animator animation) {
                factor = 1;//容错处理，正常情况下addUpdateListener执行完成后factor是1
                if (handlerView != null) {
                    if (status == STATUS_CLOSE && handlerView.getVisibility() != View.VISIBLE) {
                        handlerView.setVisibility(View.VISIBLE);
                    }
                    if (status != STATUS_CLOSE && handlerView.getVisibility() == View.VISIBLE) {
                        handlerView.setVisibility(View.INVISIBLE);
                    }
                }
            }

            @Override
            public void onAnimationCancel(Animator animation) {
            }

            @Override
            public void onAnimationRepeat(Animator animation) {
            }
        });
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        width = MeasureSpec.getSize(widthMeasureSpec);
        height = MeasureSpec.getSize(heightMeasureSpec);
        emptyY = height - child_default_height;//
        offsetExtend = emptyY - offsetTop - statusHeight;//计算展开后的顶部Y值(在顶部View之下)(在沉浸式状态栏的情况下,再减状态栏高度)
        offsetClose = emptyY + offsetTop - height;//负半展开高度(即为屏幕下隐藏)
        offsetDefault = 0;//相对于初始状态为0
        //重新测量子View
        int count = getChildCount();
        for (int i = 0; i < count; i++) {
            View child = getChildAt(i);
            measureChild(child, widthMeasureSpec, heightMeasureSpec);
        }
        setMeasuredDimension(width, height);
    }

    /**
     * 对三个子View相对于父布局位置进行设置
     */
    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        int count = getChildCount();
        if (count > 0) {
            int top = emptyY;//第一个View距顶高度
            for (int i = 0; i < count; i++) {
                View child = getChildAt(i);
                int childHeight = child.getMeasuredHeight();
                //必须有三个孩子，多了不显示
                switch (i) {
                    case 0://顶部标题栏文字View
                        child.layout(0, top, width, top + childHeight);
                        top += childHeight;
                        try {
                            topView = (TextView) child;
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                        break;
                    case 1:
                        //下：需要用屏幕高度-状态栏的高度(40dp)-第一个孩子的高度
                        int bottom = top + height - offsetTop - getChildAt(0).getMeasuredHeight();
                        child.layout(0, top, width, bottom);
                        top += childHeight;
                        break;
                    case 2://底部切换显示文字View
                        child.layout(0, emptyY, width, emptyY + childHeight);
                        try {
                            handlerView = (TextView) child;
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                        handlerView.setOnClickListener(new OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                toggle(STATUS_DEFAULT);
                            }
                        });
                        break;
                }
            }
        }
    }

    /**
     * 重要方法
     * 重写dispatchTouchEvent()方法
     * 在未展开的状态下实现抽屉效果
     * 在展开的状态下,传递事件实现列表滑动
     */
    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        //手势坐标系：下正右正
        float eX = ev.getX();
        float eY = ev.getY();
        switch (ev.getAction()) {
            case MotionEvent.ACTION_DOWN:
                dX = eX;
                lastY = dY = eY;
                isMoveValid = false;
                isEventValid = true;
                //首次进入getScrollY为0，当eY点击区域在emptyY距离下边，则进入
                //当点击距离在emptyY上边则进入else
                if (getScrollY() + eY > emptyY) {
                    //当动画正在执行时，事件不往View中传递
                    if (!(factor == 0 || factor == 1)) {
                        isEventValid = false;
                    } else {
                        //当子View为非滑动事件如TextView,LinearLayoutd等会调用它们的onTouchEvent的所有事件
                        //当子View为滑动事件如ListView,RecyclerView等不会调用onTouchEvent的Down事件，而是从Move事件开始嗲用
                        super.dispatchTouchEvent(ev);
                    }
                    return true;
                }
                return false;

            case MotionEvent.ACTION_MOVE:
                //当动画正在执行时，点击滑动不起作用
                if (!isEventValid) return false;

                //下滑offset就是负数，上滑就是正数
                int difY = (int) (lastY - eY);
                lastY = eY;
                //完全展开或者关闭状态,交由子View处理事件
                if ((status == STATUS_EXTEND || status == STATUS_CLOSE) && super.dispatchTouchEvent(ev)) {
                    return true;
                }

                //如果是全新的点击事件,且大于启动值,且为竖直滑动,则标定进行处理
                if (!isMoveValid && Math.abs(eY - dY) > touchSlop && Math.abs(eY - dY) > Math.abs(eX - dX)) {
                    isMoveValid = true;
                }

                if (isMoveValid) {
                    if (getScrollY() + difY <= offsetClose) {
                        scrollTo(0, offsetClose);//下滑限定为关闭状态
                        status = STATUS_CLOSE;
                    } else if (getScrollY() + difY >= offsetExtend) {
                        scrollTo(0, offsetExtend);//上滑限定为展开状态
                        status = STATUS_EXTEND;
                        setRecyclerViewLastY(true);
                    } else {
                        //偏移量坐标  左正上正
                        scrollBy(0, difY);//正常刷新滑动
                    }
                }
                return true;
            case MotionEvent.ACTION_CANCEL:
            case MotionEvent.ACTION_UP:
                if (!isEventValid)
                    return false;
                if (isMoveValid && getScrollY() > offsetClose && getScrollY() < offsetExtend) {
                    dealUp(getScrollY());
                    isMoveValid = false;
                    return true;
                }
                break;
        }
        return super.dispatchTouchEvent(ev);
    }

    /**
     * 上滑的处理方法
     */
    private void dealUp(int scrollY) {
        switch (status) {
            case STATUS_DEFAULT:
                if (scrollY > slideSlop) {//开启展开动画
                    toggle(STATUS_EXTEND);
                } else if (scrollY < -slideSlop) {//开始收缩动画
                    toggle(STATUS_CLOSE);
                } else {
                    toggle(STATUS_DEFAULT);//还是默认半展开状态
                }
                break;
            case STATUS_EXTEND:
                //scrollY<0，说明至少滑动了offsetExtend的距离
                //偏移量scrollY是负数，所以关闭
                if (scrollY < offsetDefault) {
                    toggle(STATUS_CLOSE);
                } else if (scrollY < offsetExtend - slideSlop) {
                    toggle(STATUS_DEFAULT);
                } else {
                    //滑动的距离大于0小于slideSlop距离时，恢复到原状态
                    toggle(STATUS_EXTEND);
                }
                break;
            case STATUS_CLOSE:
                //偏移量大于0则展开
                if (scrollY > offsetDefault) {
                    toggle(STATUS_EXTEND);
                    //滑动距离大于slideSlop但小于0则滑动到初始状态
                } else if (scrollY > offsetClose + slideSlop) {
                    toggle(STATUS_DEFAULT);
                } else {
                    //滑动距离小于slideSlop距离则恢复到原状态
                    toggle(STATUS_CLOSE);
                }
                break;
        }
    }

    /**
     * 切换状态
     */
    public void toggle(int status) {
        this.status = status;
        currentY = getScrollY();//y轴上的偏移量
        switch (status) {
            case STATUS_DEFAULT:
                finalY = offsetDefault;
                break;
            case STATUS_EXTEND:
                finalY = offsetExtend;
                break;
            case STATUS_CLOSE:
                finalY = offsetClose;
                break;
        }
        start();
    }

    public void start() {
        stop();
        if (animator != null) {
            animator.start();
        }
    }

    public void stop() {
        if (animator != null) {
            animator.end();
        }
    }

    /**
     * 设置RecyclerView是否可滑动
     * 目的：恢复当从顶部下滑后再滑动到顶部，可跟随滑动
     */
    private void setRecyclerViewLastY(boolean isScroll) {
        ScrollRecyclerView.canScroll = isScroll;
    }


    /**
     * 获取状态栏高度
     */
    public static int getStatusHeight(Context context) {
        int result = 0;
        int resourceId = context.getResources().getIdentifier("status_bar_height", "dimen", "android");
        if (resourceId > 0) {
            result = context.getResources().getDimensionPixelSize(resourceId);
        }
        return result;
    }

    /**
     * 转换dp值
     */
    private int dpToPxInt(Context context, float dp) {
        if (context == null) {
            return -1;
        }
        float v = dp * context.getApplicationContext().getResources().getDisplayMetrics().density;
        return (int) (v + 0.5f);
    }

}
