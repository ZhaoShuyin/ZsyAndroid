package zsy.structure.view;

import android.animation.Animator;
import android.animation.ValueAnimator;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.text.TextPaint;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.view.animation.AccelerateInterpolator;
import android.view.animation.DecelerateInterpolator;
import android.view.animation.LinearInterpolator;

/**
 * Title zsy.structure.view
 * 栈View
 *
 * @author Zsy
 * @date 2019/9/10.
 */

public class StackView extends View {
    private int mW, mH, Radius, radius;
    private int mCx, mCy;
    private int mBx, mBy, mClength;
    int tH;
    Paint nPaint;
    Paint lPaint;
    Paint jPaint;
    TextPaint textPaint;
    ValueAnimator loopAnimator;
    ValueAnimator addAnimator;
    ValueAnimator removeAnimator;
    float loopValue;
    float addValue;
    float removeValue;
    Node rootNode;
    Node tempNode;
    private double angel;

    class Node {
        int key;  //节点值
        Node next;//下游连接节点
        int x;
        int y;
        boolean refresh;

        public Node(int key) {
            this.key = key;
        }
    }

    public StackView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    private void init() {
        nPaint = new Paint();
        nPaint.setColor(0xff555555);
        lPaint = new Paint();
        lPaint.setColor(0xffdddddd);
        lPaint.setStrokeWidth(5);
        jPaint = new Paint();
        jPaint.setStrokeWidth(30);
        lPaint.setColor(0xff555555);
        textPaint = new TextPaint();
        textPaint.setColor(0xffffffff);
        loopAnimator = ValueAnimator.ofFloat(1f);
        loopAnimator.setRepeatCount(100);
//        loopAnimator.setRepeatCount(Integer.MAX_VALUE);
        addAnimator = ValueAnimator.ofFloat(1f);
        removeAnimator = ValueAnimator.ofFloat(1f);
        loopAnimator.setDuration(4000);
        addAnimator.setDuration(2000);
        removeAnimator.setDuration(1000);
        loopAnimator.setInterpolator(new LinearInterpolator());
        addAnimator.setInterpolator(new DecelerateInterpolator());//减速AccelerateInterpolator
        removeAnimator.setInterpolator(new AccelerateInterpolator());//加速DecelerateInterpolator
        loopAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                loopValue = 1f - (float) animation.getAnimatedValue();
                invalidate();
            }
        });
        loopAnimator.addListener(new Animator.AnimatorListener() {
            @Override
            public void onAnimationStart(Animator animation) {
            }

            @Override
            public void onAnimationEnd(Animator animation) {

            }

            @Override
            public void onAnimationCancel(Animator animation) {

            }

            @Override
            public void onAnimationRepeat(Animator animation) {
                if (rootNode != null && !removeAnimator.isRunning()) {
                    removeAnimator.start();
                }
            }
        });
        addAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                addValue = (float) animation.getAnimatedValue();
                invalidate();
            }
        });
        addAnimator.addListener(new Animator.AnimatorListener() {
            @Override
            public void onAnimationStart(Animator animation) {

            }

            @Override
            public void onAnimationEnd(Animator animation) {
                if (rootNode == null) {
                    rootNode = tempNode;
                } else {
                    tempNode.next = rootNode;
                    rootNode = tempNode;
//                    getNode(rootNode).next = tempNode;
                }
                invalidate();
            }

            @Override
            public void onAnimationCancel(Animator animation) {
            }

            @Override
            public void onAnimationRepeat(Animator animation) {
            }
        });
        removeAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                removeValue = (float) animation.getAnimatedValue();
                invalidate();
            }
        });
        removeAnimator.addListener(new Animator.AnimatorListener() {
            @Override
            public void onAnimationStart(Animator animation) {
                rootNode = rootNode.next;
            }

            @Override
            public void onAnimationEnd(Animator animation) {
                invalidate();
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
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        mW = w;
        mH = h;
        radius = Math.min(mW, mH) / 30;
        Radius = 5 * radius;
        mCx = mW / 2;
        mCy = Radius + radius * 2;
        mBx = mW / 2;
        mBy = Radius * 2 + radius * 3;
        mClength = Radius + radius;
        textPaint.setTextSize(radius);
        textPaint.setTextSize(radius);
        Paint.FontMetrics fontMetrics = textPaint.getFontMetrics();
        tH = (int) (fontMetrics.bottom - fontMetrics.top);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        canvas.drawCircle(mCx, mCy, Radius, nPaint);
        angel = (Math.PI * 2 * loopValue) + Math.PI / 2;
        float eY = (int) (Math.sin(angel) * mClength) + mCy;
        float eX = (int) (Math.cos(angel) * mClength) + mCx;
        canvas.drawLine(mCx, mCy, eX, eY, jPaint);
        if (addAnimator.isRunning()) {
            int cx = (int) (addValue * mBx);
            canvas.drawCircle(cx, mBy, radius, nPaint);
        }
        if (removeAnimator.isRunning()) {
            int cx = (int) (removeValue * (mW - mBx)) + mBx;
            canvas.drawCircle(cx, mBy, radius, nPaint);
        }
        if (rootNode != null) {
            rootNode.x = mW / 2;
            rootNode.y = mBy;
            drawNode(canvas, rootNode);
        }
    }

    private void drawNode(Canvas canvas, Node node) {
        if (node == null) {
            return;
        }
        canvas.drawCircle(node.x, node.y, radius, nPaint);
        String text = String.valueOf(node.key);
        float tW = textPaint.measureText(text);
        canvas.drawText(text, node.x - tW / 2, node.y + tH / 3, textPaint);
        if (node.next != null) {
            node.next.x = node.x;
            node.next.y = node.y + radius * 3;
            canvas.drawLine(node.x, node.y + radius, node.next.x, node.next.y, lPaint);
            drawNode(canvas, node.next);
        }
    }

    public void add(int number) {
        if (addAnimator.isRunning()) {
            return;
        }
        tempNode = new Node(number);
        addAnimator.start();
    }

    public void startLoop() {
        if (loopAnimator.isRunning()) {
            loopAnimator.cancel();
            Log.e("LinkedView", "停止旋转 ");
        } else {
            loopAnimator.start();
            Log.e("LinkedView", "开始旋转 ");
        }
    }

}
