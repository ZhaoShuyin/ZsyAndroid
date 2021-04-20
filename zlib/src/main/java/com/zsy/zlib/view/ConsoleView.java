package com.zsy.zlib.view;

import android.content.Context;
import android.os.Build;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.text.Layout;
import android.text.StaticLayout;
import android.text.TextPaint;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.view.ViewTreeObserver;
import android.widget.FrameLayout;
import android.widget.ScrollView;
import android.widget.TextView;

import com.zsy.zlib.R;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * @Title com.zsy.zlib
 * @date 2021/4/16
 * @autor Zsy
 */
@RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
public class ConsoleView extends FrameLayout {

    private String TAG = "ConsoleView";
    private static DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

    private String getDate() {
        return dateFormat.format(new Date());
    }

    private ScrollView scrollView;
    private TextView textView;

    private int textHeight;
    private int textCount;

    private String newLine = "\n\t";
    private String interval = "  ";

    private Handler handler = new Handler();

    public ConsoleView(@NonNull Context context) {
        this(context, null);
    }

    public ConsoleView(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }


    public void init(Context context) {
        View view = View.inflate(context, R.layout.layout_console, this);
        scrollView = view.findViewById(R.id.scroll_show);
        textView = view.findViewById(R.id.tv_show);
        final ViewTreeObserver observer = view.getViewTreeObserver();
        ViewTreeObserver.OnDrawListener onDrawListener = new ViewTreeObserver.OnDrawListener() {
            @Override
            public void onDraw() {
                textHeight = textView.getLineHeight();
                textCount = textView.getLineCount();
                Log.e(TAG, "textHeight: "+textHeight+" , textCount:"+textCount );
                scrollView.scrollBy(0, textHeight * textCount);
            }
        };
        observer.addOnDrawListener(onDrawListener);
    }

    public void show(final String message) {
        handler.post(new Runnable() {
            @Override
            public void run() {
                textView.append(textCount + interval + getDate() + interval + message + newLine);
                scrollView.scrollBy(0, textHeight);
            }
        });
    }

    public void clear() {
        handler.post(new Runnable() {
            @Override
            public void run() {
                textView.setText("clear()" + newLine);
            }
        });
    }

    /**
     * 获取textview一行最大能显示几个字(需要在TextView测量完成之后)
     *
     * @param text     文本内容
     * @param paint    textview.getPaint()
     * @param maxWidth textview.getMaxWidth()/或者是指定的数值,如200dp
     */
    private int getLineMaxNumber(String text, TextPaint paint, int maxWidth) {
        if (null == text || "".equals(text)) {
            return 0;
        }
        StaticLayout staticLayout = new StaticLayout(text, paint, maxWidth, Layout.Alignment.ALIGN_NORMAL
                , 1.0f, 0, false);
        //获取第一行最后显示的字符下标
        return staticLayout.getLineEnd(0);
    }

}
