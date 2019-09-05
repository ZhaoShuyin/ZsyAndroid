package com.zsy.zlib.view;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.drawable.Drawable;
import android.support.annotation.Nullable;
import android.util.AttributeSet;

import com.zsy.zlib.R;


/**
 * Title: ZsyApplication
 * <p>
 * Description:自定义drawable大小的Textview
 * </p>
 *
 * @author Zsy
 * @date 2019/4/28 9:19
 */

public class DrawTextView extends android.support.v7.widget.AppCompatTextView {
    public DrawTextView(Context context) {
        this(context, null);
    }

    public DrawTextView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init(context, attrs);
    }

    private void init(Context context, AttributeSet attrs) {
        TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.DrawTextView);
        int leftW = (int) typedArray.getDimension(R.styleable.DrawTextView_drawleft_width, 0);
        int leftH = (int) typedArray.getDimension(R.styleable.DrawTextView_drawleft_height, 0);
        int topW = (int) typedArray.getDimension(R.styleable.DrawTextView_drawtop_width, 0);
        int topH = (int) typedArray.getDimension(R.styleable.DrawTextView_drawtop_height, 0);
        int rightW = (int) typedArray.getDimension(R.styleable.DrawTextView_drawright_width, 0);
        int rightH = (int) typedArray.getDimension(R.styleable.DrawTextView_drawright_height, 0);
        int bottomW = (int) typedArray.getDimension(R.styleable.DrawTextView_drawbottom_width, 0);
        int bottomH = (int) typedArray.getDimension(R.styleable.DrawTextView_drawbottom_height, 0);
        Drawable[] drawables = getCompoundDrawables();
        //左
        if (leftW != 0 && leftH != 0 && drawables[0] != null) {
            drawables[0].setBounds(0, 0, leftW, leftH);
        }
        //上
        if (topW != 0 && topH != 0 && drawables[1] != null) {
            drawables[1].setBounds(0, 0, topW, topH);
        }
        //右
        if (rightW != 0 && rightH != 0 && drawables[2] != null) {
            drawables[2].setBounds(0, 0, rightW, rightH);
        }
        //下
        if (bottomW != 0 && bottomH != 0 && drawables[3] != null) {
            drawables[3].setBounds(0, 0, bottomW, bottomH);
        }
        setCompoundDrawables(drawables[0], drawables[1], drawables[2], drawables[3]);
        typedArray.recycle();
    }


}
