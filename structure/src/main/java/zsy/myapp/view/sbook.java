package zsy.myapp.view;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.Region;
import android.util.AttributeSet;
import android.view.View;

public class sbook extends View {

    Context mcontext;
    Paint mpaint;
    Path mpath;

    public sbook(Context context) {
        super(context);
        init();
    }

    public sbook(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public sbook(Context context, AttributeSet attrs, int defstyle) {
        super(context, attrs, defstyle);
        init();
    }

    private void init() {
        mpaint = new Paint();
        mpaint.setAntiAlias(true);
        mpaint.setStrokeWidth(6);
        mpaint.setTextSize(16);
        mpaint.setTextAlign(Paint.Align.RIGHT);
        mpath = new Path();
    }

    @Override
    protected void onDraw(Canvas canvas) {
        canvas.drawColor(Color.GRAY);
        canvas.save();
        canvas.translate(10, 10);
        drawScene(canvas);

        canvas.restore();
        canvas.save();
        canvas.translate(160, 10);
        canvas.clipRect(10, 10, 90, 90);
        canvas.clipRect(30, 30, 70, 70, Region.Op.XOR);

        drawScene(canvas);
//        canvas.restore();
//        canvas.save();
//        canvas.translate(10, 160);
//        mpath.reset();
//        mpath.cubicTo(0, 0, 100, 0, 100, 100);
//        mpath.cubicTo(100, 100, 0, 100, 0, 0);
//        canvas.clipPath(mpath, Region.Op.REPLACE);
//
//        drawScene(canvas);
//        canvas.restore();
//        canvas.save();
//        canvas.translate(160, 160);
//        canvas.clipRect(0, 0, 60, 60);
//        canvas.clipRect(40, 40, 100, 100, Region.Op.UNION);
//
//        drawScene(canvas);
//        canvas.restore();
//        canvas.save();
//        canvas.translate(10, 310);
//        canvas.clipRect(0, 0, 60, 60);
//        canvas.clipRect(40, 40, 100, 100, Region.Op.XOR);
//
//        drawScene(canvas);
//        canvas.restore();
//        canvas.save();
//        canvas.translate(160, 310);
//        canvas.clipRect(0, 0, 60, 60);
//        canvas.clipRect(40, 40, 100, 100, Region.Op.REVERSE_DIFFERENCE);
//        drawScene(canvas);
//        canvas.restore();
    }

    private void drawScene(Canvas canvas) {
        canvas.clipRect(0, 0, 100, 100);
        canvas.drawColor(Color.WHITE);
        mpaint.setColor(Color.RED);
        canvas.drawLine(0, 0, 100, 100, mpaint);
        mpaint.setColor(Color.GREEN);
        canvas.drawCircle(30, 70, 30, mpaint);
        mpaint.setColor(Color.BLUE);
        canvas.drawText("clipping", 100, 30, mpaint);
    }
}