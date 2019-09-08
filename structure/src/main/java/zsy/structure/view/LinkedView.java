package zsy.structure.view;

import android.animation.Animator;
import android.animation.ValueAnimator;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.text.TextPaint;
import android.util.AttributeSet;
import android.view.View;
import android.view.animation.LinearInterpolator;

/**
 * Title zsy.structure.view
 *
 * @author Zsy
 * @date 2019/9/8.
 */

public class LinkedView extends View {

    private int mW, mH, radius, tH;
    private ValueAnimator addAnimator, findAnimator, insertAnimator, deleteAnimator;
    private float aniValue;
    private int formX, formY, toX, toY;
    private Node rootNode;
    private Paint nPaint;
    private Paint fPaint;
    private Paint lPaint;
    private TextPaint textPaint;
    private String TAG = "LinkedView";
    private Node compileNode;
    private Node tempNode;
    private String show;

    public LinkedView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    private void init() {

        nPaint = new Paint();
        nPaint.setColor(0xff555555);
        fPaint = new Paint();
        fPaint.setColor(0xffff0000);
        lPaint = new Paint();
        lPaint.setColor(0x66dddddd);
        lPaint.setStrokeWidth(15);
        textPaint = new TextPaint();
        textPaint.setColor(0xffffffff);
        addAnimator = ValueAnimator.ofFloat(1f);
        findAnimator = ValueAnimator.ofFloat(1f);
        insertAnimator = ValueAnimator.ofFloat(1f);
        deleteAnimator = ValueAnimator.ofFloat(1f);
        addAnimator.setDuration(500);
        findAnimator.setDuration(500);
        insertAnimator.setDuration(500);
        deleteAnimator.setDuration(500);
        addAnimator.setInterpolator(new LinearInterpolator());
        findAnimator.setInterpolator(new LinearInterpolator());
        insertAnimator.setInterpolator(new LinearInterpolator());
        deleteAnimator.setInterpolator(new LinearInterpolator());
        addAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                aniValue = (float) animation.getAnimatedValue();
                invalidate();
            }
        });
        findAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                aniValue = (float) animation.getAnimatedValue();
                invalidate();
            }
        });
        insertAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                aniValue = (float) animation.getAnimatedValue();
                invalidate();
            }
        });
        deleteAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                aniValue = (float) animation.getAnimatedValue();
                invalidate();
            }
        });
        addAnimator.addListener(new Animator.AnimatorListener() {
            @Override
            public void onAnimationStart(Animator animation) {
            }

            @Override
            public void onAnimationEnd(Animator animation) {
                if (compileNode.link == null) {
                    compileNode.link = tempNode;
                    invalidate();
                } else {
                    formX = compileNode.x;
                    formY = compileNode.y;
                    toX = compileNode.link.x;
                    toY = compileNode.link.y;
                    compileNode = compileNode.link;
                    addAnimator.start();
                }
            }

            @Override
            public void onAnimationCancel(Animator animation) {
            }

            @Override
            public void onAnimationRepeat(Animator animation) {
            }
        });

        findAnimator.addListener(new Animator.AnimatorListener() {
            @Override
            public void onAnimationStart(Animator animation) {
            }

            @Override
            public void onAnimationEnd(Animator animation) {
                if (tempNode.key == compileNode.key) {
                    compileNode.refresh = true;
                    invalidate();
                    return;
                }
                if (compileNode.link == null) {
                    show = "没有<" + tempNode.key + ">节点";
                    invalidate();
                } else {
                    formX = compileNode.x;
                    formY = compileNode.y;
                    toX = compileNode.link.x;
                    toY = compileNode.link.y;
                    compileNode = compileNode.link;
                    findAnimator.start();
                }
            }

            @Override
            public void onAnimationCancel(Animator animation) {
            }

            @Override
            public void onAnimationRepeat(Animator animation) {
            }
        });

        insertAnimator.addListener(new Animator.AnimatorListener() {
            @Override
            public void onAnimationStart(Animator animation) {
            }

            @Override
            public void onAnimationEnd(Animator animation) {
                if (tempNode.key == compileNode.key) {
                    tempNode.link = compileNode.link;
                    compileNode.link = tempNode;
                    tempNode.refresh = true;
                    invalidate();
                    return;
                }
                if (compileNode.link == null) {
                    show = "没有<" + tempNode.key + ">节点";
                    invalidate();
                } else {
                    formX = compileNode.x;
                    formY = compileNode.y;
                    toX = compileNode.link.x;
                    toY = compileNode.link.y;
                    compileNode = compileNode.link;
                    insertAnimator.start();
                }
            }

            @Override
            public void onAnimationCancel(Animator animation) {
            }

            @Override
            public void onAnimationRepeat(Animator animation) {
            }
        });

        deleteAnimator.addListener(new Animator.AnimatorListener() {
            @Override
            public void onAnimationStart(Animator animation) {
            }

            @Override
            public void onAnimationEnd(Animator animation) {
                if (compileNode.link == null) {
                    show = "没有<" + tempNode.key + ">节点";
                    invalidate();
                    return;
                }
                if (tempNode.key == compileNode.link.key) {
                    compileNode.link = compileNode.link.link;
                    invalidate();
                    return;
                }
                formX = compileNode.x;
                formY = compileNode.y;
                toX = compileNode.link.x;
                toY = compileNode.link.y;
                compileNode = compileNode.link;
                deleteAnimator.start();
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
        radius = Math.min(mW, mH) / 25;
        textPaint.setTextSize(radius);
        Paint.FontMetrics fontMetrics = textPaint.getFontMetrics();
        tH = (int) (fontMetrics.bottom - fontMetrics.top);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        if (rootNode != null) {
            rootNode.x = radius * 2;
            rootNode.y = radius * 2;
            drawNode(canvas, rootNode, true);//向右
        }
        if (addAnimator.isRunning() | findAnimator.isRunning() | insertAnimator.isRunning() | deleteAnimator.isRunning()) {
            int x = (int) (formX + (toX - formX) * aniValue);
            int y = (int) (formY + (toY - formY) * aniValue);
            canvas.drawCircle(x, y, radius, nPaint);
        }
    }

    private void drawNode(Canvas canvas, Node node, boolean right) {
        if (node == null) {
            return;
        }
        canvas.drawCircle(node.x, node.y, radius, node.refresh ? fPaint : nPaint);
        node.refresh = false;
        String text = String.valueOf(node.key);
        float tW = textPaint.measureText(text);
        canvas.drawText(text, node.x - tW / 2, node.y + tH / 3, textPaint);
        if (node.link != null) {
            int interval = 5 * radius;
            if (right) {
                if ((node.x + interval) > mW) {
                    node.link.x = node.x;
                    node.link.y = node.y + interval; //向下
                    drawNode(canvas, node.link, false);//向左
                } else {
                    node.link.x = node.x + interval;
                    node.link.y = node.y;
                    drawNode(canvas, node.link, true);//继续向右
                }
            } else {
                if ((node.x - interval) < 0) {
                    node.link.x = node.x;
                    node.link.y = node.y + interval; //向下
                    drawNode(canvas, node.link, true);//向左
                } else {
                    node.link.x = node.x - interval;
                    node.link.y = node.y;
                    drawNode(canvas, node.link, false);//继续向右
                }
            }
            canvas.drawLine(node.x, node.y, node.link.x, node.link.y, lPaint);//绘制连接线
        }
    }

    class Node {
        int key;  //节点值
        Node link;//下游连接节点
        int x;
        int y;
        boolean refresh;

        public Node(int key) {
            this.key = key;
        }
    }

    public void addLinked(final int number) {
        if (addAnimator.isRunning() | findAnimator.isRunning() | insertAnimator.isRunning() | deleteAnimator.isRunning()){
            return;
        }
        if (rootNode == null) {
            rootNode = new Node(number);
            invalidate();
            return;
        } else {
            compileNode = rootNode;
            tempNode = new Node(number);
            formX = 0;
            formY = 0;
            toX = 0;
            toY = 0;
            addAnimator.start();
        }
    }

    public void insertNode(int number) {
        if (addAnimator.isRunning() | findAnimator.isRunning() | insertAnimator.isRunning() | deleteAnimator.isRunning()){
            return;
        }
        if (rootNode == null) {
            rootNode = new Node(number);
            invalidate();
        } else {
            compileNode = rootNode;
            tempNode = new Node(number);
            formX = 0;
            formY = 0;
            toX = 0;
            toY = 0;
            insertAnimator.start();
        }

    }

    public void findLinked(int number) {
        if (addAnimator.isRunning() | findAnimator.isRunning() | insertAnimator.isRunning() | deleteAnimator.isRunning()){
            return;
        }
        if (rootNode == null) {
            rootNode = new Node(number);
            invalidate();
        } else {
            compileNode = rootNode;
            tempNode = new Node(number);
            formX = 0;
            formY = 0;
            toX = 0;
            toY = 0;
            findAnimator.start();
        }
    }

    public void delLinked(int number) {
        if (addAnimator.isRunning() | findAnimator.isRunning() | insertAnimator.isRunning() | deleteAnimator.isRunning()){
            return;
        }
        if (rootNode == null) {
            rootNode = new Node(number);
            invalidate();
        } else {
            compileNode = rootNode;
            tempNode = new Node(number);
            formX = 0;
            formY = 0;
            toX = 0;
            toY = 0;
            deleteAnimator.start();
        }
    }


}
