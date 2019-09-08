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
import android.widget.Toast;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Title: 红黑树
 * https://www.jianshu.com/p/e136ec79235c             (30张图带你彻底理解红黑树)
 * https://www.cnblogs.com/newobjectcc/p/11293689.html(数据结构之红黑树-动图演示(上))
 * https://www.cnblogs.com/newobjectcc/p/11295652.html(数据结构之红黑树-动图演示(下))
 * 性质1：节点为黑色或者红色。
 * 性质2：根节点是黑色。
 * 性质3：每个叶子节点（NIL）是黑色。
 * 性质4：每个红色结点的两个子结点一定都是黑色。
 * 性质5：任意结点到每个叶子结点的路径都包含数量相同的黑结点
 * 红黑树的生长是自底向上的
 *
 * @author Zsy
 * @date on 2019/9/5.
 */
public class RBTreeView extends View {
    private String TAG = "RBTreeView";
    private static final int RED = 0xffff0000, BLACK = 0xff555555;
    private int mW;             //控件宽度
    private int mH;             //控件高度
    private int radius;         //原点半径
    private float tH;           //文字高度
    private TextPaint textPaint;//文字画笔
    private Paint nPaint;       //节点画笔
    private Paint lPaint;       //连接线画笔
    public Node drawBoot;//从此节点开始绘制
    private Node bootNode;      //根节点
    private List<Node> pathNodes = new ArrayList<>(); //路径节点集合
    private Node comNode;       //比较节点
    private Path mPath;         //插入,删除,查找路径
    private String show;        //操作状态显示文字

    Map<Integer, Node> nodeMap = new HashMap<>();

    public void test(int number) {
        Node node = nodeMap.get(number);
        if (node != null) {
            node.empty();
        }
        invalidate();
    }

    public void test2(int number) {
        Node node = nodeMap.get(number);
        if (node != null) {
            node.turnColor();
        }
        invalidate();
    }

    public void test3(int form, int to) {
      /*  Node f = nodeMap.get(form);
        Node t = nodeMap.get(to);
        if (f != null & t != null) {
            rotatLeft(f, t);
        } else {
            Toast.makeText(getContext(), "左旋为空", Toast.LENGTH_SHORT).show();
        }
        invalidate();*/
        int i = drawBoot.blackNumebr(drawBoot.left, 0);
        Log.e(TAG, "test3: 左侧节点长度==" + i);
    }

    public void test4(int form, int to) {
       /* Node f = nodeMap.get(form);
        Node t = nodeMap.get(to);
        if (f != null & t != null) {
            rotatRight(f, t);
        } else {
            Toast.makeText(getContext(), "右旋为空", Toast.LENGTH_SHORT).show();
        }
        invalidate();*/
        int i = drawBoot.blackNumebr(drawBoot.right, 0);
        Log.e(TAG, "test3: 右侧节点长度==" + i);
    }

    public RBTreeView(Context context) {
        this(context, null);
    }

    public RBTreeView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public RBTreeView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    /**
     * 初始化参数
     */
    private void init() {
        drawBoot = null;
        textPaint = new TextPaint();   //文字画笔
        textPaint.setColor(0xffffffff);
        nPaint = new Paint();          //红点画笔
        nPaint.setColor(RED);
        lPaint = new Paint();          //连接线画笔
        lPaint.setStrokeWidth(10);
        lPaint.setColor(0xffdddddd);
    }

    /**
     * 添加节点的平衡性检测
     */
    private void addBalance(Node addNode, boolean right) {
        Node uNode = addNode.getUNode();
        if (uNode == null && addNode.boot.boot != null) {
            addNode.boot.turnBlack();
            addNode.boot.boot.turnRed();
            rotatRight(addNode.boot, addNode.boot.boot);
            invalidate();
            return;
        }
        if (addNode.boot.color == Color.BLACK) {      //添加节点的父节点为黑色,直接添加
            invalidate();
        } else {                                            //添加节点的父节点为红色,进行检测
            if (uNode == null || uNode.isBlack()) {         //叔节点为黑色
                if (addNode.isLeftest()) {                  //2.1 左直线   ---> 一层叔节点变色,左终子节点一层右旋
                    Log.e(TAG, "上游红色,情况二 左终点 从" + addNode.value + "向上检测");
//                    formatUp(addNode, right);
//                    addNode.boot.turnBlack();
//                    addNode.boot.boot.turnRed();
//                    rotatLeft(addNode.boot,addNode.boot.boot);
                } else if (addNode.isRightest()) {          //2.2 右直线   ---> 一层叔节点变色,左终子节点一层右旋
                    Log.e(TAG, "上游红色,情况二 右终点 从" + addNode.value + "向上检测");
//                    formatUp(addNode, right);

                } else {                                    //3.父节点红色,叔节点黑色,非直线 ---> 一层左旋,一层叔节点变色,左终子节点一层右旋
                    Log.e(TAG, "上游红色,情况三 ");

                }
            } else {                                        //1.叔节点红色 ---> 向上递归变色(根节点变红色,左旋)
                Log.e(TAG, "上游红色,情况一 从" + addNode.value + "向上检测");
                formatUp(addNode.boot, right);
            }
            invalidate();
        }
    }

    /**
     * 格式化树结构,自下向上检测平衡
     */
    private void formatUp(final Node formatNode, boolean right) {
        if (formatNode == null) {
            return;
        }
      /*  if (formatNode == drawBoot && formatNode.isRed()) {//递归到根节点(根节点为红色)开始另一侧
            formatDown(formatNode.getAnother(right));
            if (right) {
                drawBoot = formatNode.right;
                rotatLeft(formatNode.right, formatNode);       //开始左旋
            } else {
                drawBoot = formatNode.left;
                rotatRight(formatNode.left, formatNode);        //开始右旋
            }
            invalidate();
            return;
        } else {

        }*/
        if (formatNode.boot != null) {
            formatNode.turnColor(); //递归父节点转换颜色
            Node another = formatNode.boot.getAnother(right);
//            Node uNode = formatNode.getUNode();
            if (another != null && formatNode.isColor(another)) {
                formatDown(another);
            }
            Log.e(TAG, "向上检测 切换颜色 " + formatNode.boot.value + " 为 " + formatNode.boot.getColorStr());
            formatUp(formatNode.boot, right);
        } else {
            Log.e(TAG, "向上检测到根节点了: ");
//            if (!right){
//                formatNode.right.setColor(formatNode.left.getColor());
//            }else{
//                formatNode.left.setColor(formatNode.right.getColor());
//            }
        }
    }

    private void formatDown(Node formatNode) {
        if (formatNode == null) {
            return;
        }
        formatNode.trunColor();
        Log.e(TAG, "向下递归换色: " + formatNode + " " + formatNode.getColorStr());
        formatDown(formatNode.left);
        formatDown(formatNode.right);
    }


    /**
     * 添加元素
     */
    public void addElement(int number) {
        if (bootNode == null) {     //第一个元素(根节点)
            bootNode = new Node(null, number);
            bootNode.x = mW >> 1;
            bootNode.y = radius;
            bootNode.color = Color.BLACK;
            bootNode.interval = radius * 2;
            drawBoot = bootNode;
            nodeMap.put(number, bootNode);
            invalidate();
            return;
        }
        comNode = drawBoot;
        mPath = new Path();
        pathNodes.clear();
        add(number);
    }

    //递归比较添加元素(根节点之外的节点)
    private void add(int number) {
        if (number < comNode.value) {
            if (comNode.left == null) {
                Node node = comNode.getLeftChild(number, mW);
                nodeMap.put(number, node);
                pathNodes.add(node);
                addBalance(node, false);     //添加元素,属性检测
            } else {
                comNode = comNode.left;
                pathNodes.add(comNode);
                add(number);
            }
        } else if (number > comNode.value) {
            if (comNode.right == null) {
                Node node = comNode.getRightChild(number, mW);
                nodeMap.put(number, node);
                pathNodes.add(node);
                addBalance(node, true);     //添加元素,属性检测
            } else {
                comNode = comNode.right;
                pathNodes.add(comNode);
                add(number);
            }
        }
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

    /**
     * 坐标格式化
     */
    private void locate(Node node) {
        if (node == null) {
            return;
        }
        int difX;
        if (node.boot == null) {  //根节点
            difX = 0;
        } else if (node.boot.boot == null) {//第二级节点
            difX = mW / 4;
        } else {
            difX = Math.abs(node.boot.boot.x - node.boot.x) / 2;
        }
        if (node.boot == null) {
            node.x = mW / 2;
            node.y = radius;
            node.interval = radius * 2;
        } else {
            if (node == node.boot.left) {
                node.x = node.boot.x - difX;
            } else {
                node.x = node.boot.x + difX;
            }
            node.interval = (int) (node.boot.interval * 1.2f);
            node.y = node.boot.y + node.interval;
        }
        locate(node.left);
        locate(node.right);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        if (drawBoot != null) {
            drawBoot.boot = null;
            drawBoot.x = mW / 2;
            drawBoot.y = radius;
            drawBoot.interval = radius * 2;
            locate(drawBoot);           //格式化坐标
            drawNode(canvas, drawBoot); //绘制基本结构
        }
        if (mPath != null) {
            canvas.drawPath(mPath, nPaint);
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
        if (node == null) {
            return;
        }
        if (node.boot != null) {
            int fromX = node.boot.x;
            int fromY = node.boot.y;
            canvas.drawLine(fromX, fromY + radius, node.x, node.y, lPaint);
        }
        //绘制圆
        nPaint.setColor(node.color.colorValue);
        canvas.drawCircle(node.x, node.y, radius, nPaint);
        //绘制文字
        String text = String.valueOf(node.value);
        float tW = textPaint.measureText(text);
        canvas.drawText(text, node.x - tW / 2, node.y + tH / 3, textPaint);
        if (node.left != null) {
            drawNode(canvas, node.left);
        }
        if (node.right != null) {
            drawNode(canvas, node.right);
        }
        //绘制Nil节点
        drawNuiNode(canvas, node);
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
        if (node.boot == null) {
            difX = mW / 4;
        } else {
            difX = Math.abs(node.boot.x - node.x) / 2;
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
     * 右旋操作
     */
    private void rotatRight(Node from, Node to) {
        Log.e(TAG, "右旋: " + (from == null ? "null" : from.value) + " to " + (to == null ? "null" : to.value));
        if (from == null || to == null) {
            return;
        }
        if (from.boot != to) {
            return;
        }
        //替换始点节点的上游节点
        Node upstream = to.boot;  //上游节点
        if (upstream == null) {
            drawBoot = from;
        } else {
            if (to == upstream.left) {
                upstream.left = from;
                from.boot = upstream;
            } else {
                upstream.right = from;
                from.boot = upstream;
            }
        }
        //终点上游为始节点
        to.boot = from;
        //转换两节点左右节点
        to.left = from.right;
        if (from.right != null) {
            from.right.boot = to;
        }
        //始点右节点为终结点
        from.right = to;
    }

    /**
     * 左旋操作
     */
    private void rotatLeft(Node from, Node to) {
        Log.e(TAG, "左旋: " + (from == null ? "null" : from.value) + " to " + (to == null ? "null" : to.value));
        if (from == null || to == null) {
            return;
        }
        if (from.boot != to) {
            return;
        }
        //替换始点节点的上游节点
        Node upstream = to.boot;  //上游节点
        if (upstream == null) {
            drawBoot = from;
        } else {
            if (to == upstream.left) {
                upstream.left = from;
                from.boot = upstream;
            } else {
                upstream.right = from;
                from.boot = upstream;
            }
        }
        //终点上游为始节点
        to.boot = from;
        //转换两节点左右节点
        to.right = from.left;
        if (from.left != null) {
            from.left.boot = to;
        }
        //始点右节点为终结点
        from.left = to;
    }

}
