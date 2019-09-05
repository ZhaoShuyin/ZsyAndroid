package zsy.myapp.view;

import android.animation.Animator;
import android.animation.ValueAnimator;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Path;
import android.text.TextPaint;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.view.animation.LinearInterpolator;

import java.util.ArrayList;
import java.util.List;

/**
 * https://www.cnblogs.com/rainple/p/9970760.html
 * https://blog.csdn.net/qq_25467397/article/details/81432330
 * https://blog.csdn.net/csdn_aiyang/article/details/84837553
 * Created by Zsy on 2019/8/31.
 */
public class BinaryTreeView extends View {

    private String TAG = "BinaryTreeView";
    private int mW;            //View宽度
    private int mH;            //View高度
    private int radius = 20;
    private Paint rPaint;
    private Paint bPaint;
    private Paint lPaint;
    private Paint pathPaint;
    private Paint traversePaint;
    private TextPaint tPaint;
    private float tH;              //文字高度
    private Node bootNode;         //根节点
    private Node addNode;          //待添加节点
    private Node deleteNode;       //待删除节点
    private Node findNode;         //待查找节点
    private Node compileNode;      //比较节点
    private ValueAnimator addAnimator;
    private ValueAnimator delAnimator;
    private ValueAnimator findAnimator;
    private float aniValue;
    private boolean hasTraverse = false;
    private Path findPath;        //查询路径
    private Path traversePath;    //遍历路径
    private List<String> tracerseList;

    public BinaryTreeView(Context context) {
        this(context, null);
    }

    public BinaryTreeView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public BinaryTreeView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    /**
     * 初始化参数
     */
    private void init() {
        rPaint = new Paint();
        rPaint.setColor(0xffff0000);   //红色节点颜色
        bPaint = new Paint();
        bPaint.setColor(0xff555555);   //黑色节点颜色
        lPaint = new Paint();
        lPaint.setColor(0xffdddddd);   //连接线颜色
        lPaint.setStrokeWidth(5);      //连接线宽度
        pathPaint = new Paint();
        pathPaint.setColor(0x55ff0000);
        pathPaint.setStyle(Paint.Style.STROKE);
        pathPaint.setStrokeWidth(10);      //连接线宽度
        tPaint = new TextPaint();
        tPaint.setColor(0xffffffff);      //节点文字颜色
        traversePaint = new Paint();
        traversePaint.setStrokeWidth(10);
        traversePaint.setColor(0xffdddddd);
        traversePaint.setStyle(Paint.Style.STROKE);
        Paint.FontMetrics fontMetrics = tPaint.getFontMetrics();
        tH = fontMetrics.descent - fontMetrics.ascent;
        addAnimator = ValueAnimator.ofFloat(1f); //添加动画
        addAnimator.setDuration(1000);
        addAnimator.setInterpolator(new LinearInterpolator());
        addAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
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
                int difX = Math.abs(compileNode.x - compileNode.boot.x) / 2; //下层级x偏差
                if (difX == 0) {
                    difX = mW / 4;                              //第二层固定偏差
                }
                if (addNode.value < compileNode.value) {         //(小于)左侧
                    if (compileNode.left == null) {
                        addNode.x = compileNode.x - difX;
                        addNode.interval = (int) (compileNode.interval * 1.2f);
                        addNode.y = compileNode.y + addNode.interval;
                        addNode.boot = compileNode;
                        compileNode.left = addNode;
                        addNode = null;
                        invalidate();
                    } else {
                        compileNode = compileNode.left;
                        addAnimator.start();
                    }
                } else if (addNode.value > compileNode.value) { //(大于)右侧
                    if (compileNode.right == null) {
                        addNode.x = compileNode.x + difX;
                        addNode.interval = (int) (compileNode.interval * 1.2f);
                        addNode.y = compileNode.y + addNode.interval;
                        addNode.boot = compileNode;
                        compileNode.right = addNode;
                        addNode = null;
                        invalidate();
                    } else {
                        compileNode = compileNode.right;
                        addAnimator.start();
                    }
                } else {                                       //(等于)
                    addNode = null;
                }
            }

            @Override
            public void onAnimationCancel(Animator animation) {
            }

            @Override
            public void onAnimationRepeat(Animator animation) {
            }
        });
        delAnimator = ValueAnimator.ofFloat(1f);
        delAnimator.setDuration(1000);
        delAnimator.setInterpolator(new LinearInterpolator());
        delAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                aniValue = (float) animation.getAnimatedValue();
                invalidate();
            }
        });
        delAnimator.addListener(new Animator.AnimatorListener() {
            @Override
            public void onAnimationStart(Animator animation) {
            }

            @Override
            public void onAnimationEnd(Animator animation) {
                if (deleteNode.value < compileNode.value) {
                    if (compileNode.left != null) {
                        compileNode = compileNode.left;
                        delAnimator.start();
                    }
                } else if (deleteNode.value > compileNode.value) {
                    if (compileNode.right != null) {
                        compileNode = compileNode.right;
                        delAnimator.start();
                    }
                } else {
                    calculateDel();         //遍历到删除几点开始计算
                }
            }

            @Override
            public void onAnimationCancel(Animator animation) {
            }

            @Override
            public void onAnimationRepeat(Animator animation) {
            }
        });
        findAnimator = ValueAnimator.ofFloat(1f);
        findAnimator.setDuration(1000);
        findAnimator.setInterpolator(new LinearInterpolator());
        findAnimator.addListener(new Animator.AnimatorListener() {
            @Override
            public void onAnimationStart(Animator animation) {
                if (findPath == null) {
                    findPath = new Path();
                    findPath.moveTo(bootNode.x, bootNode.y);
                }
            }

            @Override
            public void onAnimationEnd(Animator animation) {
                if (findNode.value < compileNode.value) {
                    if (compileNode.left != null) {
                        compileNode = compileNode.left;
                        findAnimator.start();
                    }
                } else if (findNode.value > compileNode.value) {
                    if (compileNode.right != null) {
                        compileNode = compileNode.right;
                        findAnimator.start();
                    }
                } else {
                    findPath = null;
                }
                if (findPath != null) {
                    findPath.lineTo(compileNode.x, compileNode.y);
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
    }

    /**
     * 计算删除节点
     * 前驱节点(1.只有下左节点,即为左节点,2有下右节点,下右节点)
     * 后继节点(1.只有下右节点,即为右节点,2.有下左节点,下左节点)
     */
    private void calculateDel() {
        if (compileNode == bootNode) {                                //根节点不删除
            return;
        }
        deleteNode = null;
        if (compileNode.left == null & compileNode.right == null) {//第一种没有子节点的情况
            if (compileNode == compileNode.boot.left) {
                compileNode.boot.left = null;
            }
            if (compileNode == compileNode.boot.right) {
                compileNode.boot.right = null;
            }
            invalidate();
        } else if (compileNode.right == null) {       //第二种只有左子节点
            if (compileNode == compileNode.boot.left) {
                compileNode.boot.left = compileNode.left;
            }
            if (compileNode == compileNode.boot.right) {
                compileNode.boot.right = compileNode.left;
            }
            sortNode(compileNode.boot);              //更改节点之下重新计算坐标
            invalidate();

        } else if (compileNode.left == null) {       //第三种情况只有右节点
            if (compileNode == compileNode.boot.left) {
                compileNode.boot.right = compileNode.right;
            }
            if (compileNode == compileNode.boot.right) {
                compileNode.boot.right = compileNode.right;
            }
            sortNode(compileNode.boot);             //更改节点之下重新计算坐标
            invalidate();
        } else {                                    //第四种情况,左右子节点都有,找到后继节点，将值赋给删除节点，然后将后继节点删除
            Node subsequentNode = getSubsequentNode(compileNode.right);
            //删除替换节点上层关联上层链表
            if (subsequentNode == subsequentNode.boot.left) {
                subsequentNode.boot.left = null;
            }
            if (subsequentNode == subsequentNode.boot.right) {
                subsequentNode.boot.right = null;
            }
            //后继节点插入到待删除节点位置
            if (compileNode == compileNode.boot.left) {
                subsequentNode.boot = compileNode.boot.left.boot;
                subsequentNode.left = compileNode.boot.left.left;
                subsequentNode.right = compileNode.boot.left.right;
                compileNode.boot.left = subsequentNode;
                subsequentNode.x = compileNode.x;
                subsequentNode.y = compileNode.y;
            }
            if (compileNode == compileNode.boot.right) {
                subsequentNode.boot = compileNode.boot.right.boot;
                subsequentNode.left = compileNode.boot.right.left;
                subsequentNode.right = compileNode.boot.right.right;
                compileNode.boot.right = subsequentNode;
                subsequentNode.x = compileNode.x;
                subsequentNode.y = compileNode.y;
            }
            deleteNode = null;
            sortNode(compileNode.boot);  //更改节点之下重新计算坐标
            invalidate();
        }

    }

    /**
     * 递归找到后继节点
     */
    private Node getSubsequentNode(Node node) {
        Log.e(TAG, "getSubsequentNode: " + node.value);
        if (node.left == null & node.right == null) {
            return node;
        }
        if (node.left == null) {
            return getSubsequentNode(node.right);
        } else {
            return getSubsequentNode(node.left);
        }
    }


    /**
     * 重新计算某节点之下的子节点的坐标值
     */
    private void sortNode(Node comNode) {
        int difX = Math.abs(comNode.boot.x - comNode.x) / 2;         //下层级x偏差
        if (difX == 0) {
            difX = mW / 4;                                           //第二层固定偏差
        }
        if (comNode.left != null) {
            comNode.left.x = comNode.x - difX;
            comNode.left.interval = (int) (comNode.interval * 1.2f);
            comNode.left.y = comNode.y + comNode.left.interval;
            sortNode(comNode.left);
        }
        if (comNode.right != null) {
            comNode.right.x = comNode.x + difX;
            comNode.right.interval = (int) (comNode.interval * 1.2f);
            comNode.right.y = comNode.y + comNode.right.interval;
            sortNode(comNode.right);
        }
    }

    /**
     * 添加元素
     */
    public void add(int numebr) {
        if (addAnimator.isRunning()) {
            return;
        }
        addNode = new Node();
        addNode.value = numebr;
        compileNode = bootNode;
        addAnimator.start();
    }

    /**
     * 删除元素
     */
    public void delete(int number) {
        if (delAnimator.isRunning()) {
            return;
        }
        deleteNode = new Node();
        deleteNode.value = number;
        compileNode = bootNode;
        delAnimator.start();
    }

    /**
     * 查找元素
     */
    public void find(int number) {
        if (findAnimator.isRunning()) {
            return;
        }
        findNode = new Node();
        findNode.value = number;
        compileNode = bootNode;
        findPath = new Path();
        findPath.moveTo(bootNode.x, bootNode.y);
        findAnimator.start();
    }

    /**
     * 调用先序遍历
     */
    public void prevTraverse() {
        tracerseList = new ArrayList<>();
        traversePath = new Path();
        traversePath.moveTo(mW / 2, mH - 5 * radius);
        prevIterator(bootNode);
        traversePaint.setColor(0x99ffdddd);
        hasTraverse = true;
        invalidate();
    }

    /**
     * 先序遍历指的就是先访问本节点，再访问该节点的左孩子和右孩子
     */
    private void prevIterator(final Node node) {
        if (node != null) {
            tracerseList.add(String.valueOf(node.value));
            traversePath.lineTo(node.x, node.y);
            prevIterator(node.left);
            prevIterator(node.right);
        }
    }

    /**
     * 调用中序遍历
     */
    public void middleTraverse() {
        tracerseList = new ArrayList<>();
        traversePath = new Path();
        traversePath.moveTo(mW / 2, mH - 5 * radius);
        midIterator(bootNode);
        traversePaint.setColor(0x99ddffdd);
        hasTraverse = true;
        invalidate();
    }

    /**
     * 中序遍历指的就是：先访问左孩子，再访问本节点，最后访问右孩子
     */
    private void midIterator(final Node node) {
        if (node != null) {
            midIterator(node.left);
            tracerseList.add(String.valueOf(node.value));
            traversePath.lineTo(node.x, node.y);
            midIterator(node.right);
        }
    }

    /**
     * 调用后序遍历
     */
    public void subTraverse() {
        tracerseList = new ArrayList<>();
        traversePath = new Path();
        traversePath.moveTo(mW / 2, mH - 5 * radius);
        subIterator(bootNode);
        traversePaint.setColor(0x99ddddff);
        hasTraverse = true;
        invalidate();
    }

    /**
     * 后序遍历指的就是：先访问左右孩子，最后访问本节点
     */
    private void subIterator(final Node node) {
        if (node != null) {
            subIterator(node.left);
            subIterator(node.right);
            tracerseList.add(String.valueOf(node.value));
            traversePath.lineTo(node.x, node.y);
        }
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        if (mW == 0 || mW == 0) {
            mW = MeasureSpec.getSize(widthMeasureSpec);
            mH = MeasureSpec.getSize(heightMeasureSpec);
            radius = Math.min(mW, mH) / 30;
            tPaint.setTextSize(radius);
            bootNode = new Node();
            bootNode.x = mW >>> 1;
            bootNode.y = radius;
            bootNode.value = 10;
            bootNode.interval = radius * 3;
            bootNode.boot = bootNode;
        }
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        drawNode(canvas, bootNode, -1, -1);//绘制根节点
        drawAddNode(canvas);               //绘制添加移动动画
        drawDeleteNode(canvas);            //绘制删除移动动画
        if (findPath != null) {
            canvas.drawPath(findPath, pathPaint);
        }
        if (hasTraverse && traversePath != null) {
            Log.e(TAG, ">>>>> 绘制遍历路径: ");
            canvas.drawPath(traversePath, traversePaint);
            int bottom = mH - radius * 3;
            int startX = 0;
            for (int i = 0; i < tracerseList.size(); i++) {
                startX = radius * i * 2 + radius;
                String text = tracerseList.get(i);
                //绘制圆
                canvas.drawCircle(startX, bottom, radius, bPaint);
                //绘制文字
                float tW = tPaint.measureText(text);
                canvas.drawText(text, startX - tW / 2, bottom + tH, tPaint);
            }
            hasTraverse = false;
        }
    }

    private void drawNode(Canvas canvas, Node node, int fromX, int fromY) {
        if (node == null) {
            return;
        }
        if (fromX >= 0 && fromY >= 0) {
            canvas.drawLine(fromX, fromY + radius, node.x, node.y, lPaint);
        }
        //绘制圆
        canvas.drawCircle(node.x, node.y, radius, bPaint);
        //绘制文字
        String text = String.valueOf(node.value);
        float tW = tPaint.measureText(text);
        canvas.drawText(text, node.x - tW / 2, node.y + tH, tPaint);
        if (node.left != null) {
            drawNode(canvas, node.left, node.x, node.y);
        }
        if (node.right != null) {
            drawNode(canvas, node.right, node.x, node.y);
        }
    }

    private void drawAddNode(Canvas canvas) {
        if (addNode == null || compileNode == null) {
            return;
        }
        int difx = compileNode.x - compileNode.boot.x;
        int dify = compileNode.y - compileNode.boot.y;
        int x = (int) (compileNode.boot.x + difx * aniValue);
        int y = (int) (compileNode.boot.y + dify * aniValue);
        //绘制圆
        canvas.drawCircle(x, y, radius, bPaint);
    }

    private void drawDeleteNode(Canvas canvas) {
        if (deleteNode == null || compileNode == null) {
            return;
        }
        int difx = compileNode.x - compileNode.boot.x;
        int dify = compileNode.y - compileNode.boot.y;
        int x = (int) (compileNode.boot.x + difx * aniValue);
        int y = (int) (compileNode.boot.y + dify * aniValue);
        //绘制圆
        canvas.drawCircle(x, y, radius, bPaint);
    }

    /**
     * 节点类
     */
    public class Node {
        int value;
        Node boot;      //父节点
        Node left;      //左节点
        Node right;     //右节点
        int x;          //x坐标
        int y;          //y坐标
        int interval;   //距上层间距
    }

}
