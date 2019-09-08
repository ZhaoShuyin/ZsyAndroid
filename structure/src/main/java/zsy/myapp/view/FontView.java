package zsy.myapp.view;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.text.TextPaint;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;

/**
 * Title zsy.myapp.view
 *
 * @author Zsy
 * @date 2019/9/5.
 */

public class FontView extends View {
    Paint paint;
    TextPaint textPaint;
    int mW;
    int mH;

    public FontView(Context context) {
        super(context);
    }

    public FontView(Context context, AttributeSet attrs) {
        super(context, attrs);
        paint = new Paint();
        paint.setColor(0x55dddddd);
        paint.setStrokeWidth(10);
        textPaint = new TextPaint();
        textPaint.setTextSize(260);
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        mW = w;
        mH = h;
        invalidate();
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        Paint.FontMetrics fontMetrics = textPaint.getFontMetrics();
        float top = fontMetrics.top;
        float ascent = fontMetrics.ascent;
        float descent = fontMetrics.descent;
        float bottom = fontMetrics.bottom;
        float leading = fontMetrics.leading;
        Log.e("FontView", "top:" + top + " ascent:" + ascent + " descent:" + descent + " bottom:" + bottom + " leading:" + leading);
        canvas.drawText("1 j E h 字", 10, mH / 2, textPaint);
        int hh = 0;
        hh = mH / 2;             //基准线
        paint.setColor(0x99dddddd);
        canvas.drawLine(0, hh, mW, hh, paint);

        hh = (int) (mH / 2 +top);                //top
        paint.setColor(0x99ffdddd);
        canvas.drawLine(0, hh, mW, hh, paint);

        hh = (int) (mH / 2 +ascent);             //ascent
        paint.setColor(0x99ddffdd);
        canvas.drawLine(0, hh, mW, hh, paint);

        hh = (int) (mH / 2 +descent);            //descent
        paint.setColor(0x99ddddff);
        canvas.drawLine(0, hh, mW, hh, paint);

        hh = (int) (mH / 2 +bottom);            //bottom
        paint.setColor(0xff000000);
        paint.setStrokeWidth(5);
        canvas.drawLine(0, hh, mW, hh, paint);
    }
}
