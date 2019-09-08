package zsy.myapp.view;

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
    Color color = Color.RED;      //节点颜色(默认添加为红色)

    public Node() {
    }

    public Node(Node boot, int value) {
        this.boot = boot;
        this.value = value;
    }

    //创建左子节点
    public Node getLeftChild(int number, int mW) {
        int difX;
        if (this.boot == null) {
            difX = mW / 4;
        } else {
            difX = Math.abs(boot.x - this.x) / 2;
        }
        Node node = new Node(this, number);
        node.x = this.x - difX;
        node.interval = (int) (this.interval * 1.2f);
        node.y = this.y + node.interval;
        this.left = node;
        return node;
    }

    //创建右子节点
    public Node getRightChild(int number, int mW) {
        int difX;
        if (this.boot == null) {
            difX = mW / 4;
        } else {
            difX = Math.abs(boot.x - this.x) / 2;
        }
        Node node = new Node(this, number);
        node.x = this.x + difX;
        node.interval = (int) (this.interval * 1.2f);
        node.y = this.y + node.interval;
        this.right = node;
        return node;
    }

    //本节点置空
    public void empty() {
        if (boot != null) {
            if (this == boot.left) {
                boot.left = null;
            } else {
                boot.right = null;
            }
        }
    }

    //找到前驱节点
    public Node getPrev() {
        if (this.left == null) {
            return null;
        }
        return prev(this.left);
    }

    private Node prev(Node node) {
        if (node.left == null & node.right == null) {
            return node;
        }
        if (node.right == null) {
            return SubsequentNode(node.left);
        } else {
            return SubsequentNode(node.right);
        }
    }

    //找到后继节点
    public Node getSub() {
        if (this.right == null) {
            return null;
        }
        return SubsequentNode(this.right);
    }

    private Node SubsequentNode(Node node) {
        if (node.left == null & node.right == null) {
            return node;
        }
        if (node.left == null) {
            return SubsequentNode(node.right);
        } else {
            return SubsequentNode(node.left);
        }
    }

   /* public void turnBoot() {
        RBTreeView.bootNode = this;
        boot = null;
    }*/

    public boolean isBoot() {
        return boot == null;
    }

    //是否是黑色
    public boolean isBlack() {
        return color == Color.BLACK;
    }

    public void turnBlack() {
        color = Color.BLACK;
    }

    //是否是红色
    public boolean isRed() {
        return color == Color.RED;
    }


    public void turnRed() {
        color = Color.RED;
    }

    public void turnColor() {
        if (color == Color.RED) {
            color = Color.BLACK;
        } else {
            color = Color.RED;
        }
    }

    public boolean isColor(Node node) {
        if (node == null){
            return false;
        }
        return this.color == node.color;
    }

    //获取叔节点
    public Node getUNode() {
        if (this.boot == null || this.boot.boot == null) {
            return null;
        }
        if (this.boot == this.boot.boot.left) {
            return this.boot.boot.right;
        } else {
            return this.boot.boot.left;
        }
    }

    public Node getAnother(boolean r) {
        return r ? left : right;
    }

    //判断是否是最左侧直线子节点
    public boolean isLeftest() {
        return isLeftLine(this);
    }

    private boolean isLeftLine(Node node) {
        if (node.boot == null) {
            return true;
        }
        if (node == node.boot.left) {
            return isLeftLine(node.boot);
        } else {
            return false;
        }
    }

    //判断是否是最右侧直线子节点
    public boolean isRightest() {
        return isRightLine(this);
    }

    private boolean isRightLine(Node node) {
        if (node.boot == null) {
            return true;
        }
        if (node == node.boot.right) {
            return isRightLine(node.boot);
        } else {
            return false;
        }
    }

    public void setColor(Color c) {
        this.color = c;
    }

    public Color getColor() {
        return color;
    }

    public String getColorStr() {
        return color.name;
    }

    public void trunColor() {
        if (color == Color.RED) {
            color = Color.BLACK;
        } else {
            color = Color.RED;
        }
    }

    public boolean isBalance() {
        int left = blackNumebr(this.left, 0);
        int right = blackNumebr(this.right, 0);
        return left == right;
    }

    public int blackNumebr(Node node, int number) {
        if (node == null) {
            return number;
        }
        int lNumber = blackNumebr(node.left, node.isBlack() ? number + 1 : number);
        int rNumber = blackNumebr(node.right, node.isBlack() ? number + 1 : number);
        return Math.max(lNumber, rNumber);
    }

    @Override
    public String toString() {
        return "<vaule:" + String.valueOf(value) + ",x:" + x + ",y:" + y +
                ",boot:" + (boot == null ? "null" : boot.value) + ",left:" + (left == null ? "null" : left.value) + ",right:" + (right == null ? "null" : right.value) + ">";
    }
}