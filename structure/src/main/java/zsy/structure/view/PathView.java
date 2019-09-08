package zsy.structure.view;

import android.animation.ValueAnimator;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.PathMeasure;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.view.animation.LinearInterpolator;
import android.widget.Toast;

/**
 * Title zsy.myapp.view
 *
 * @author Zsy
 * @date 2019/9/6.
 */

public class PathView extends View {

    private String TAG = "PathView";
    private int mW;
    private int mH;
    private ValueAnimator animator;
    private Path path;
    private Paint paint;

    public PathView(Context context, AttributeSet attrs) {
        super(context, attrs);
        animator = ValueAnimator.ofFloat(1f);
        animator.setDuration(5000);
        animator.setInterpolator(new LinearInterpolator());
        paint = new Paint();
        paint.setStrokeWidth(10);
        paint.setColor(0xffff0000);
        paint.setStyle(Paint.Style.STROKE);
        setLayerType(View.LAYER_TYPE_SOFTWARE, null);
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
        canvas.drawCircle(mW / 2, mH / 2, 10, paint);
        if (path != null) {
            canvas.drawPath(path, paint);
            Log.e(TAG, "绘制路径");
            path = null;
        } else {
            Log.e(TAG, "路径为空 ");
        }
    }


    public void aniPath() {
        Log.e(TAG, "开始路径动画 ");
        path = new Path();
        path.moveTo(0, 100);
        path.lineTo(100, 100);
        path.lineTo(100, 200);
        path.lineTo(100, 200);
        path.lineTo(200, 200);
        path.lineTo(200, 100);
        path.lineTo(300, 100);
        invalidate();
        PathMeasure pathMeasure = new PathMeasure(path, false);
        float length = pathMeasure.getLength();
        Log.e(TAG, "Path 路径总长度: " + length);
        animator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {

            }
        });
        animator.start();
    }
}
