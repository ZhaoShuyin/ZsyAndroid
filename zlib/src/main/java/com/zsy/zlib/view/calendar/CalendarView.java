package com.zsy.zlib.view.calendar;


import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;


import com.zsy.zlib.R;

import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;

/**
 * zsy
 */
public class CalendarView extends View {
    enum DayStatus {
        NORMAL, START, MIDDEL, STOP, INVALID
    }

    // 列的数量
    private static final int NUM_COLUMNS = 7;
    // 行的数量
    private static final int NUM_ROWS = 6;

    //以选日期数据
    private int lastDay = -1;

    // 单个日期背景颜色
    private int mBgColor = Color.parseColor("#FDFDFD");
    // 区间颜色
    private int middleColor = Color.parseColor("#ff6632");
    // 默认颜色
    private int normalColor = Color.parseColor("#000000");
    //
    private int invalidColor = Color.parseColor("#dddddd");
    // 选中颜色
    private int selectColor = Color.WHITE;
    // 天数字体大小
    private int textSize = 14;

    private Paint mPaint;//画笔

    private Map<String, Integer> curDays = new HashMap<>();
    private int curYear, curMonth, curlDate;//年月日
    private int startDay = -1;//年月日
    private int stopDay = -1;//年月日
    private int mColumnSize, mRowSize;//动态计算列宽 , 动态计算行宽
//    private Map<String, Integer> days = new HashMap<>();


    private int mMonthDays; // 当月一共有多少天

    private int mFristWeekDay;// 当月第一天位于周几
    // 开始的背景Bitmap图片
    private Bitmap startBitmap;
    //停止的背景Bitmap图片
    private Bitmap stopBitmap;


    public CalendarView(Context context) {
        super(context);
        init();
    }

    public CalendarView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public CalendarView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init() {
        // 获取手机屏幕参数
        DisplayMetrics mMetrics = getResources().getDisplayMetrics();
        textSize = (int) (textSize * mMetrics.scaledDensity);
        // 创建画笔
        mPaint = new Paint();
        // 获取当前日期
        initDate();
        startBitmap = BitmapFactory.decodeResource(getResources(), R.drawable.pic_ks);
        stopBitmap = BitmapFactory.decodeResource(getResources(), R.drawable.pic_js);
    }

    private void initDate() {
        Calendar calendar = Calendar.getInstance();
        curYear = calendar.get(Calendar.YEAR);
        curMonth = calendar.get(Calendar.MONTH);
        curlDate = calendar.get(Calendar.DATE);
        lastDay = getDayTag(curYear, curMonth, curlDate);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        int width = MeasureSpec.getSize(widthMeasureSpec);
        int height = MeasureSpec.getSize(heightMeasureSpec);
        int size = Math.min(width / NUM_COLUMNS, width / NUM_COLUMNS);
        startBitmap = getBitmap(startBitmap, size, size);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        initSize();
        mPaint.setColor(mBgColor);
        canvas.drawRect(0, 0, canvas.getWidth(), canvas.getHeight(), mPaint);

        mPaint.setTextSize(textSize);
        String dayStr;
        mMonthDays = getMonthDays(curYear, curMonth);
        mFristWeekDay = getFirstDayWeek(curYear, curMonth) - 1;

        float tvHeight = mPaint.ascent() + mPaint.descent();//描绘文本上坡度减下坡度(文本高度)
        float tvWidth = mPaint.measureText("11");//默认为两位数字宽度
        float offsetH = mRowSize / 2 - tvHeight / 2;
        float offsetW = (mColumnSize - tvWidth) / 2;
        //
        mPaint.setColor(normalColor);
        for (int day = 1; day <= mMonthDays; day++) {
            dayStr = String.valueOf(day);
            int row = (day + mFristWeekDay) / 7;//计算在第几行
            int column = (day + mFristWeekDay) % 7;//计算在第几列
            int startY = (int) (mRowSize * row + offsetH);
            int startX = (int) (mColumnSize * column + offsetW);
            String key = getKey(row, column);
            int dayTime = getDayTag(curYear, curMonth, day);
            curDays.put(key, dayTime);

            DayStatus status = getStatus(dayTime);
            switch (status) {
                case NORMAL:
                    mPaint.setColor(normalColor);
                    break;
                case START:
                    mPaint.setColor(selectColor);
//                        canvas.drawBitmap(startBitmap, startX - (startBitmap.getWidth() / 3), startY - (startBitmap.getHeight()/2 ), mPaint);
                    canvas.drawBitmap(startBitmap, mColumnSize * column, mRowSize * row, mPaint);
                    break;
                case MIDDEL:
                    mPaint.setColor(middleColor);
                    break;
                case STOP:
                    mPaint.setColor(selectColor);
                    canvas.drawBitmap(stopBitmap, startX - (stopBitmap.getWidth() / 3), startY - (stopBitmap.getHeight() / 2), mPaint);
                case INVALID:
                    mPaint.setColor(invalidColor);
                    break;
            }
            // 绘制天数
            canvas.drawText(dayStr, startX, startY, mPaint);
        }
    }

    private DayStatus getStatus(int day) {
        if (day > lastDay) {
            return DayStatus.INVALID;
        }
        if (startDay == -1 && stopDay == -1) {
            return DayStatus.NORMAL;
        }
        if (day == startDay) {
            return DayStatus.START;
        }
        if (day == stopDay) {
            return DayStatus.STOP;
        }
        if (stopDay == -1) {
            return DayStatus.NORMAL;
        } else {
            if (day < startDay || day > stopDay) {
                return DayStatus.NORMAL;
            } else {
                return DayStatus.MIDDEL;
            }
        }
    }

    private int downX = 0, downY = 0;

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        int eventCode = event.getAction();
        switch (eventCode) {
            case MotionEvent.ACTION_DOWN:
                downX = (int) event.getX();
                downY = (int) event.getY();
                break;
            case MotionEvent.ACTION_MOVE:
                break;
            case MotionEvent.ACTION_UP:
                int upX = (int) event.getX();
                int upY = (int) event.getY();
                if (Math.abs(upX - downX) < 10 && Math.abs(upY - downY) < 10) {
                    dealonClick((upX + downX) / 2, (upY + downY) / 2);
                }
                break;
        }
        return true;
    }

    /**
     * 点击事件
     */
    private void dealonClick(int x, int y) {
        int row = y / mRowSize;
        int column = x / mColumnSize;
        String key = getKey(row, column);
        int day = curDays.get(key);
        if (day > lastDay) {
            return;
        }
        //全为默认状态
        if (startDay == -1 && stopDay == -1) {
            startDay = day;
            invalidate();
            return;
        }
        if (startDay != -1 && stopDay != -1) {
            if (day == startDay) {
                startDay = stopDay;
                stopDay = -1;
                invalidate();
                return;
            }
            if (day == stopDay) {
                stopDay = -1;
                invalidate();
                return;
            }
            if (day < startDay) {
                startDay = day;
                invalidate();
                return;
            }
            if (day > startDay & day < stopDay) {

            }
            if (day > stopDay) {
                stopDay = day;
                invalidate();
                return;
            }
        }
        //已有开始日期
        if (stopDay == -1) {
            if (day == startDay) {
                startDay = -1;
                invalidate();
                return;
            }
            if (day < startDay) {
                stopDay = startDay;
                startDay = day;
                invalidate();
                return;
            }
            if (day > startDay) {
                stopDay = day;
                invalidate();
            }
        }
    }


    private void initSize() {
        mColumnSize = getWidth() / NUM_COLUMNS;
        mRowSize = getHeight() / NUM_ROWS;
    }


    public void setLastMonth() {
        int year = curYear;
        int month = curMonth;
        int day = curlDate;
        // 如果是1月份，则变成12月份
        if (month == 0) {
            year = curYear - 1;
            month = 11;
        } else if (getMonthDays(year, month) == day) {
            //　如果当前日期为该月最后一点，当向前推的时候，就需要改变选中的日期
            month = month - 1;
            day = getMonthDays(year, month);
        } else {
            month = month - 1;
        }
        curYear = year;
        curMonth = month;
        curlDate = day;
        invalidate();
    }

    public void setNextMonth() {
        int year = curYear;
        int month = curMonth;
        int day = curlDate;
        // 如果是12月份，则变成1月份
        if (month == 11) {
            year = curYear + 1;
            month = 0;
        } else if (getMonthDays(year, month) == day) {
            //　如果当前日期为该月最后一点，当向前推的时候，就需要改变选中的日期
            month = month + 1;
            day = getMonthDays(year, month);
        } else {
            month = month + 1;
        }
        curYear = year;
        curMonth = month;
        curlDate = day;
        invalidate();
    }

    public String getDate() {
        String data;
        if ((curMonth + 1) < 10) {
            data = curYear + "-0" + (curMonth + 1);
        } else {
            data = curYear + "-" + (curMonth + 1);
        }
        return data;
    }


    public void restore() {
        startDay = -1;
        stopDay = -1;
        invalidate();
    }

    /**
     * 获取当前展示的日期
     *
     * @return 格式：20160606
     */
    private String getSelData(int year, int month, int date) {
        String monty, day;
        month = (month + 1);

        // 判断月份是否有非0情况
        if ((month) < 10) {
            monty = "0" + month;
        } else {
            monty = String.valueOf(month);
        }

        // 判断天数是否有非0情况
        if ((date) < 10) {
            day = "0" + (date);
        } else {
            day = String.valueOf(date);
        }
        return year + monty + day;
    }


    @Override
    protected void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        recyclerBitmap(stopBitmap);
        recyclerBitmap(startBitmap);
    }


    public static int getMonthDays(int year, int month) {
        month++;
        switch (month) {
            case 1:
            case 3:
            case 5:
            case 7:
            case 8:
            case 10:
            case 12:
                return 31;
            case 4:
            case 6:
            case 9:
            case 11:
                return 30;
            case 2:
                if (((year % 4 == 0) && (year % 100 != 0)) || (year % 400 == 0)) {
                    return 29;
                } else {
                    return 28;
                }
            default:
                return -1;
        }
    }

    public static int getFirstDayWeek(int year, int month) {
        Calendar calendar = Calendar.getInstance();
        calendar.set(year, month, 1);
        return calendar.get(Calendar.DAY_OF_WEEK);
    }

    private String getKey(int r, int c) {
        return String.valueOf(r) + String.valueOf(c);
    }

    public int getDayTag(int y, int m, int d) {
        return y * 10000 + m * 100 + d;
    }

    private Bitmap getBitmap(Bitmap bitmap, int newWidth, int newHeight) {
        int width = bitmap.getWidth();
        int height = bitmap.getHeight();
        float scaleWidth = ((float) newWidth) / width;
        float scaleHeight = ((float) newHeight) / height;
        Matrix matrix = new Matrix();
        matrix.postScale(scaleWidth, scaleHeight);
        Bitmap bt = Bitmap.createBitmap(bitmap, 0, 0, width, height, matrix, true);
        bitmap.recycle();
        return bt;
    }

    /**
     * 释放资源
     */
    private void recyclerBitmap(Bitmap bitmap) {
        if (bitmap != null && !bitmap.isRecycled()) {
//            bitmap.recycle();
        }
    }

}
