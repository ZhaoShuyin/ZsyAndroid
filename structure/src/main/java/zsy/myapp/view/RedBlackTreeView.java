package zsy.myapp.view;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.Rect;
import android.text.TextPaint;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;

import java.util.ArrayList;
import java.util.List;

/**
 * Title zsy.myapp.view
 *
 * @author Zsy
 * @date 2019/9/7.
 */

public class RedBlackTreeView extends View {

    private static final int REDCOLOR = 0xffff0000;
    private static final int BLACKCOLOR = 0xff555555;
    private String TAG = "RBTreeView";
    private int mW;             //控件宽度
    private int mH;             //控件高度
    private int radius;         //原点半径
    private float tH;           //文字高度
    private TextPaint textPaint;//文字画笔
    private Paint nPaint;       //节点画笔
    private Paint lPaint;       //连接线画笔
    private Paint pathPaint;    //路径画笔
    private List<Node> pathNodes = new ArrayList<>(); //路径节点集合
    private Path mPath;         //插入,删除,查找路径
    private String show;        //操作状态显示文字

    private final Node nil = new Node(-1); //叶子节点
    private Node treeRoot = null;              //根节点


    public RedBlackTreeView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    private void init() {
        textPaint = new TextPaint();   //文字画笔
        textPaint.setColor(0xffffffff);
        nPaint = new Paint();          //红点画笔
        lPaint = new Paint();          //连接线画笔
        lPaint.setStrokeWidth(10);
        lPaint.setColor(0xffdddddd);
        pathPaint = new Paint();
        pathPaint.setStyle(Paint.Style.STROKE);
        pathPaint.setStrokeWidth(10);
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        if (mW == 0 || mH == 0) {
            mW = w;
            mH = h;
            radius = Math.min(mW, mH) / 30;
            textPaint.setTextSize(radius);
            Paint.FontMetrics fontMetrics = textPaint.getFontMetrics();
            tH = fontMetrics.bottom - fontMetrics.top;
        }
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        if (treeRoot != null) {
            treeRoot.x = mW / 2;
            treeRoot.y = radius;
            treeRoot.interval = radius * 2;
            locate(treeRoot);           //格式化坐标
            drawNode(canvas, treeRoot); //绘制基本结构
        }
        if (mPath != null) {
            Log.e(TAG, "=======绘制==========路径===========");
            canvas.drawPath(mPath, pathPaint);
            mPath = null;
        }
        for (int i = 0; pathNodes.size() != 0 && i < pathNodes.size(); i++) {
            Node node = pathNodes.get(i);
            int cx = radius * i * 2 + radius;
            int height = mH - this.radius * 5;
            nPaint.setColor(0xffddddff);
            canvas.drawCircle(cx, height, radius, nPaint);
            String text = String.valueOf(node.key);
            float tW = textPaint.measureText(text);
            canvas.drawText(text, cx - tW / 2, height + tH / 3, textPaint);
        }
        if (show != null) {
            TextPaint textPaint = new TextPaint();
            textPaint.setTextSize(radius * 2);
            float tW = textPaint.measureText(show);
            canvas.drawText(show, mW / 2 - tW / 2, mH - radius * 2, textPaint);
        }
    }

    /**
     * 绘制结构树
     */
    private void drawNode(Canvas canvas, Node node) {
        if (node == null || node == nil) {
            return;
        }
        if (node.parent != null) {
            int fromX = node.parent.x;
            int fromY = node.parent.y;
            canvas.drawLine(fromX, fromY + radius, node.x, node.y, lPaint);
        }
        //绘制圆
        nPaint.setColor(node.color ? BLACKCOLOR : REDCOLOR);
        canvas.drawCircle(node.x, node.y, radius, nPaint);
        //绘制文字
        String text = String.valueOf(node.key);
        float tW = textPaint.measureText(text);
        canvas.drawText(text, node.x - tW / 2, node.y + tH / 3, textPaint);
        Log.e(TAG, "绘制节点: " + node.key);
        drawNode(canvas, node.left);
        drawNode(canvas, node.right);
        //绘制Nil节点
//        drawNuiNode(canvas, node);
    }

    /**
     * 绘制末端节点
     */
    private void drawNuiNode(Canvas canvas, Node node) {
        if (node == null) {
            return;
        }
        if (node.left != null & node.right != null) {
            return;
        }
        nPaint.setColor(0xff555555);
        int difX;
        if (node.parent == null) {
            difX = mW / 4;
        } else {
            difX = Math.abs(node.parent.x - node.x) / 2;
        }
        String text = "N";
        float tW = textPaint.measureText(text);
        int nY = (int) (node.y + node.interval * 1.5f);
        Rect rect = new Rect();
        int l = radius / 3 * 2;
        if (node.left == null) {
            int lX = node.x - difX;
            rect.left = lX - l;
            rect.right = lX + l;
            rect.top = nY - l;
            rect.bottom = nY + l;
            canvas.drawLine(node.x, node.y + radius, lX, nY, lPaint);
            canvas.drawRect(rect, nPaint);
            canvas.drawText(text, lX - tW / 2, nY + tH / 3, textPaint);
        }
        if (node.right == null) {
            int rX = node.x + difX;
            rect.left = rX - l;
            rect.right = rX + l;
            rect.top = nY - l;
            rect.bottom = nY + l;
            canvas.drawLine(node.x, node.y + radius, rX, nY, lPaint);
            canvas.drawRect(rect, nPaint);
            canvas.drawText(text, rX - tW / 2, nY + tH / 3, textPaint);
        }

    }

    /**
     * 坐标格式化
     */
    private void locate(Node node) {
        if (node == null) {
            return;
        }
        int difX;
        if (node.parent == null) {  //根节点
            difX = 0;
        } else if (node.parent.parent == null) {//第二级节点
            difX = mW / 4;
        } else {
            difX = Math.abs(node.parent.parent.x - node.parent.x) / 2;
        }
        if (node.parent == null) {
            node.x = mW / 2;
            node.y = radius;
            node.interval = radius * 2;
        } else {
            if (node == node.parent.left) {
                node.x = node.parent.x - difX;
            } else {
                node.x = node.parent.x + difX;
            }
            node.interval = (int) (node.parent.interval * 1.2f);
            node.y = node.parent.y + node.interval;
        }
        locate(node.left);
        locate(node.right);
    }

    /**
     * 结点类
     */
    private class Node {
        public int key;        //节点值
        Node left;      //左子节点
        Node right;     //右子节点
        Node parent;    //父节点
        boolean color = true;
        int x;          //x坐标
        int y;          //y坐标
        int interval;   //距上层间距

        public Node(int key) {
            this.key = key;
        }
    }

    /**
     * 左旋
     */
    private void leftRotate(Node to) {
        Node form = to.right;     //左旋起点
        to.right = form.left;     //起点坐支置为终点右支
        if (form.left != nil) {
            form.left.parent = to;//切换分支设置父节点
        }
        form.parent = to.parent;  //设置上游分支
        if (to.parent == null) {
            treeRoot = form;
        } else if (to == to.parent.left) {
            to.parent.left = form;
        } else {
            to.parent.right = form;
        }
        form.left = to;
        to.parent = form;
    }

    /**
     * 右旋
     */
    private void rightRotate(Node to) {
        Node form = to.left;
        to.left = form.right;
        if (form.right != nil) {
            form.right.parent = to;
        }
        form.parent = to.parent;
        if (to.parent == null) {
            treeRoot = form;
        } else if (to == to.parent.left) {
            to.parent.left = form;
        } else {
            to.parent.right = form;
        }
        form.right = to;
        to.parent = form;
    }

    /**
     * 插入
     */
    public void addNode(int key) {
        Node addNode = new Node(key);
        if (treeRoot == null) {
            treeRoot = addNode;
            treeRoot.left = nil;
            treeRoot.right = nil;
            return;
        }
        Node upstream = null;
        Node compile = treeRoot;
        while (compile != nil) {
            upstream = compile;
            if (key < compile.key) {       //小值置于左侧
                compile = compile.left;
            } else if (key > compile.key) {//大值置于右侧
                compile = compile.right;
            } else {                       //重复结点不插入
                return;
            }
        }
        addNode.parent = upstream;
        if (addNode.key < compile.key) {
            upstream.left = addNode;
        } else {
            upstream.right = addNode;
        }
        addNode.left = nil;
        addNode.right = nil;
        addNode.color = false;  //默认红色
        addBalance(addNode);
        invalidate();
    }

    /**
     * 插入修复
     */
    private void addBalance(Node node) {
        //遍历父节点直至为黑色时
        while (node.parent != null && node.parent.color == false) {
            if (node.parent == node.parent.parent.left) {
                Node y = node.parent.parent.right;
                if (y.color == false) {
                    node.parent.color = true;
                    y.color = true;
                    node.parent.parent.color = false;
                    node = node.parent.parent;
                } else {
                    if (node == node.parent.right) {
                        node = node.parent;
                        leftRotate(node);
                    }
                    node.parent.color = true;
                    node.parent.parent.color = false;
                    rightRotate(node.parent.parent);
                }
            } else {
                Node y = node.parent.parent.left;
                if (y.color == false) {
                    node.parent.color = true;
                    y.color = true;
                    node.parent.parent.color = false;
                    node = node.parent.parent;
                } else {
                    if (node == node.parent.left) {
                        node = node.parent;
                        rightRotate(node);
                    }
                    node.parent.color = true;
                    node.parent.parent.color = false;
                    leftRotate(node.parent.parent);
                }
            }
        }
        treeRoot.color = true;    //根节点必须为黑色
    }

    /**
     * 移植
     * 用v子树替换u子树
     */
    private void transplant(Node u, Node v) {
        if (u.parent == nil) {
            treeRoot = v;
        } else if (u == u.parent.left) {
            u.parent.left = v;
        } else {
            u.parent.right = v;
        }
        v.parent = u.parent;
    }

    /**
     * 查找以x为根的最小结点
     */
    private Node minimum(Node x) {
        while (x.left != nil) {
            x = x.left;
        }
        return x;
    }

    public void find(int number) {
        pathNodes.clear();
        search(number, true);
        int size = pathNodes.size();
        if (size != 0) {
            mPath = new Path();
            pathPaint.setColor(0x9900ffff);
            for (int i = 0; i < size; i++) {
                Node node = pathNodes.get(i);
                if (i == 0) {
                    mPath.moveTo(node.x, node.y);
                } else {
                    mPath.lineTo(node.x, node.y);
                }
            }
            show = "查询 <" + number + "> 次数: " + (size - 1);
            pathNodes.clear();
            invalidate();
        }
    }

    /**
     * 查找结点的key值为key的结点
     */
    private Node search(int key, boolean withPath) {
        Node compile = treeRoot;
        if (withPath) {
            pathNodes.add(compile);
        }
        while (compile != nil && key != compile.key) {
            if (key < compile.key) {
                compile = compile.left;
            } else {
                compile = compile.right;
            }
            if (withPath) {
                pathNodes.add(compile);
            }
        }
        return compile;
    }

    public void deleteNode(int number) {
        pathNodes.clear();
        delete(number, true);
        int size = pathNodes.size();
        if (size != 0) {
            mPath = new Path();
            pathPaint.setColor(0x99ffdddd);
            for (int i = 0; i < size; i++) {
                Node node = pathNodes.get(i);
                if (i == 0) {
                    mPath.moveTo(node.x, node.y);
                } else {
                    mPath.lineTo(node.x, node.y);
                }
            }
            show = "删除<" + number + "> ";
            pathNodes.clear();
            invalidate();
        }
    }

    /**
     * 删除结点
     */
    public void delete(int key, boolean withPath) {
        Node z = search(key, withPath);
        if (z == nil) {
            return;
        }
        Node y = z;
        Node x;
        boolean yOriginalColor = y.color;
        if (z.left == nil) {
            x = z.right;
            transplant(z, z.right);
        } else if (z.right == nil) {
            x = z.left;
            transplant(z, z.left);
        } else {
            y = minimum(z.right);
            yOriginalColor = y.color;
            x = y.right;
            if (y.parent == z) {
                x.parent = y;
            } else {
                transplant(y, y.right);
                y.right = z.right;
                y.right.parent = y;
            }
            transplant(z, y);
            y.left = z.left;
            y.left.parent = y;
            y.color = z.color;
        }
        if (yOriginalColor == true) {
            deleteBalance(x);
        }
    }

    /**
     * 删除修复
     */
    private void deleteBalance(Node x) {
        while (x != treeRoot && x.color == true) {
            if (x == x.parent.left) {
                Node w = x.parent.right;
                if (w.color == false) {
                    w.color = true;
                    x.parent.color = false;
                    leftRotate(x.parent);
                    w = x.parent.right;
                }
                if (w.left.color == true && w.right.color == true) {
                    w.color = false;
                    x = x.parent;
                } else {
                    if (w.right.color == true) {
                        w.left.color = true;
                        w.color = false;
                        rightRotate(w);
                        w = x.parent.right;
                    }
                    w.color = x.parent.color;
                    x.parent.color = true;
                    w.right.color = true;
                    leftRotate(x.parent);
                    x = treeRoot;
                }
            } else {
                Node w = x.parent.left;
                if (w.color == false) {
                    w.color = true;
                    x.parent.color = false;
                    leftRotate(x.parent);
                    w = x.parent.left;
                }
                if (w.left.color == true && w.right.color == true) {
                    w.color = false;
                    x = x.parent;
                } else {
                    if (w.left.color == true) {
                        w.right.color = true;
                        w.color = false;
                        leftRotate(w);
                        w = x.parent.left;
                    }
                    w.color = x.parent.color;
                    x.parent.color = true;
                    w.left.color = true;
                    rightRotate(x.parent);
                    x = treeRoot;
                }
            }
        }
        x.color = true;
    }


    /**
     * 先序遍历
     */
    public void preOrder() {
        pathNodes.clear();
        preOrder(treeRoot);
        int size = pathNodes.size();
        if (size != 0) {
            mPath = new Path();
            pathPaint.setColor(0x99ffdddd);
            for (int i = 0; i < size; i++) {
                Node node = pathNodes.get(i);
                if (i == 0) {
                    mPath.moveTo(node.x, node.y);
                } else {
                    mPath.lineTo(node.x, node.y);
                }
            }
            show = "先序遍历先本-再左-再右";
            invalidate();
        }
    }

    private void preOrder(Node traverse) {
        if (traverse != nil) {
            pathNodes.add(traverse);
            preOrder(traverse.left);
            preOrder(traverse.right);
        }
    }

    /**
     * 中序遍历
     */
    public void inOrder() {
        pathNodes.clear();
        inOrder(treeRoot);
        int size = pathNodes.size();
        if (size != 0) {
            mPath = new Path();
            pathPaint.setColor(0x99ddddff);
            for (int i = 0; i < size; i++) {
                Node node = pathNodes.get(i);
                if (i == 0) {
                    mPath.moveTo(node.x, node.y);
                } else {
                    mPath.lineTo(node.x, node.y);
                }
            }
            show = "中序遍历先左-再本-再右";
            invalidate();
        }
    }

    private void inOrder(Node traverse) {
        if (traverse != nil) {
            inOrder(traverse.left);
            pathNodes.add(traverse);
            inOrder(traverse.right);
        }
    }

    /**
     * 后序遍历
     */
    public void postOrder() {
        postOrder(treeRoot);
        pathNodes.clear();
        inOrder(treeRoot);
        int size = pathNodes.size();
        if (size != 0) {
            mPath = new Path();
            pathPaint.setColor(0x99ddffdd);
            for (int i = 0; i < size; i++) {
                Node node = pathNodes.get(i);
                if (i == 0) {
                    mPath.moveTo(node.x, node.y);
                } else {
                    mPath.lineTo(node.x, node.y);
                }
            }
            show = "后序遍历先左-再又-再本";
            invalidate();
        }
    }
    //后序遍历先左-再又-再本
    private void postOrder(Node traverse) {
        if (traverse != nil) {
            postOrder(traverse.left);
            postOrder(traverse.right);
            pathNodes.add(traverse);
        }
    }

}
