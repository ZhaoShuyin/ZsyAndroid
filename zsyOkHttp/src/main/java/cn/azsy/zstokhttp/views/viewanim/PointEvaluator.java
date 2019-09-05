package cn.azsy.zstokhttp.views.viewanim;

import android.animation.FloatEvaluator;
import android.animation.IntEvaluator;
import android.animation.TypeEvaluator;

/**
 * Created by zsy on 2017/7/13.
 * 监听评估器,监听动画执行进程并作出相应回应
 */

public class PointEvaluator implements TypeEvaluator {
    FloatEvaluator floatEvaluator;
    IntEvaluator intEvaluator;
    @Override
    public Object evaluate(float fraction, Object startValue, Object endValue) {
        Point startPoint = (Point) startValue;
        Point endPoint = (Point) endValue;
        float x = startPoint.getX() + fraction * (endPoint.getX() - startPoint.getX());
        float y = startPoint.getY() + fraction * (endPoint.getY() - startPoint.getY());
        Point point = new Point(x, y);
        return point;
    }
}
