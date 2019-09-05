package com.zsy.android.layout.coordinate;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;

import com.zsy.android.R;

/**
 * Title: ZsyAndroid
 * <p>
 * Description:使用<CoordinatorLayout>和<AppBarLayout>实现悬浮置顶等效果
 * </p>
 * 参考http://www.jcodecraeer.com/plus/view.php?aid=10428
 *
 * @author Zsy
 * @date 2019/6/25 15:24
 */

public class CoordinateActivity extends Activity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_coordinate_layout);
    }
}
