package zsy.myapp.view;

/**
 * Title zsy.myapp.view
 *
 * @author Zsy
 * @date 2019/9/6.
 */

public enum Color {
    RED("红", 0xffff0000), BLACK("黑", 0xff555555);
    String name;
    int colorValue;

    Color(String name, int color) {
        this.name = name;
        this.colorValue = color;
    }
}
