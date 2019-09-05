package com.zsy.android.api.keyboard;

import android.annotation.TargetApi;
import android.app.Activity;
import android.app.DatePickerDialog;
import android.content.Context;
import android.graphics.Typeface;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.ContextThemeWrapper;
import android.view.KeyEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.NumberPicker;
import android.widget.TextView;
import android.widget.Toast;

import com.zsy.android.R;
import com.zsy.android.utils.Lunar;
import com.zsy.android.utils.LunarUtil;

import java.util.Calendar;

/**
 * Title: ZsyAndroid
 * <p>
 * Description:键盘输入法(软键盘按钮)
 * https://blog.csdn.net/weixin_37730482/article/details/78523608
 * </p>
 *
 * @author Zsy
 * @date 2019/8/16 9:09
 */

public class KeyboardActivity extends Activity {


    private TextView textView;

    /**
     * https://blog.csdn.net/adayabetter/article/details/79976871
     * @param savedInstanceState
     */
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_keyboard);

        //设置初始隐藏软键盘及弹出软键盘控件上移(不遮挡)
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN | WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN);

        //遮挡屏幕
//        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_MASK_ADJUST);
//        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE);
        setFont();
        InputButton();
    }

    private void setFont() {
        textView = findViewById(R.id.tv_show);
        Typeface typeface=Typeface.createFromAsset(getAssets(),"font/书体坊兰亭体I.ttf");
        textView.setTypeface(typeface);
    }

    /**
     * 设置输入法的搜索,完成按钮
     */
    private void InputButton() {
        //SEARCH
        EditText editText = findViewById(R.id.et_keyboard);
        editText.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                Toast.makeText(KeyboardActivity.this, "actionId==" + actionId, Toast.LENGTH_SHORT).show();
                return false;
            }
        });
        //DONE
        final EditText editText2 = findViewById(R.id.et_keyboard2);
        editText2.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if (actionId == EditorInfo.IME_ACTION_DONE) {
                    String s = v.getText().toString();
                    Toast.makeText(KeyboardActivity.this, "s== " + s, Toast.LENGTH_SHORT).show();
                    InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                    imm.hideSoftInputFromInputMethod(editText2.getWindowToken(), 0);
                    return true;//自行处理软键盘的收起
                }
                return false;
            }
        });
    }

    /**
     * 隐藏/显示软键盘
     * InputMethodManager.RESULT_HIDDEN
     *
     * @param view
     */
    public void dilog(View view) {
        InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
//        imm.showSoftInput(view,InputMethodManager.SHOW_FORCED); //强制显示键盘
        imm.hideSoftInputFromWindow(view.getWindowToken(), 0); //强制隐藏键盘
        Toast.makeText(this, "隐藏软键盘", Toast.LENGTH_SHORT).show();
    }


    public void nongli(View view){
//        Calendar calendar=Calendar.getInstance();
//        Lunar lunar = new Lunar(calendar);
//        String animalsYear = lunar.animalsYear();//生肖
//        String cyclical = lunar.cyclical();//农历的干支甲子
//        String nongli = lunar.toString();//农历的月日
//        textView.setText(calendar.getTime().toString()+"\n"+animalsYear+"\n"+cyclical+"\n"+nongli);
        textView.setText(LunarUtil.getLunar(System.currentTimeMillis()));
    }


    //    E:  第一层: <0>android.widget.LinearLayout => -1
//    E:   第二层: <0>android.widget.LinearLayout => 16909313
//    E:     第三层: <0>android.widget.LinearLayout => 16909313
//    E:          第四层: <0>android.widget.NumberPicker => 16909732
//    E:          第四层: <1>android.widget.NumberPicker => 16909203
//    E:          第四层: <2>android.widget.NumberPicker => 16908911
//    E:     第三层: <1>android.widget.CalendarView => 16908857
//    E:          第四层: <0>android.widget.LinearLayout => -1
//    E:   第二层: <1>android.widget.CalendarView => 16908857
//    E:     第三层: <0>android.widget.LinearLayout => 16909313
//    E:          第四层: <0>android.widget.NumberPicker => 16909732
//    E:          第四层: <1>android.widget.NumberPicker => 16909203
//    E:          第四层: <2>android.widget.NumberPicker => 16908911
//    E:     第三层: <1>android.widget.CalendarView => 16908857
//    E:          第四层: <0>android.widget.LinearLayout => -1
    private void ss(DatePicker datePicker) {
        for (int i = 0; i < datePicker.getChildCount(); i++) {
            View childAt = datePicker.getChildAt(i);
            Log.e("XmlActivity", " 第一层: <" + i + ">" + childAt.getClass().getName() + " => " + childAt.getId());
            ViewGroup viewGroup = (ViewGroup) childAt;
            for (int j = 0; j < viewGroup.getChildCount(); j++) {
                View childAt1 = viewGroup.getChildAt(j);
                Log.e("XmlActivity", "  第二层: <" + j + ">" + childAt1.getClass().getName() + " => " + childAt1.getId());
                ViewGroup viewGroup2 = (ViewGroup) childAt;
                for (int j2 = 0; j2 < viewGroup2.getChildCount(); j2++) {
                    View childAt2 = viewGroup.getChildAt(j2);
                    Log.e("XmlActivity", "    第三层: <" + j2 + ">" + childAt2.getClass().getName() + " => " + childAt2.getId());

                    if (childAt2 instanceof ViewGroup) {
                        ViewGroup group = (ViewGroup) childAt2;
                        for (int k = 0; k < group.getChildCount(); k++) {
                            View childAt3 = group.getChildAt(k);
                            Log.e("XmlActivity", "         第四层: <" + k + ">" + childAt3.getClass().getName() + " => " + childAt3.getId());
                        }
                    }


                }

            }
        }
    }


    private void monthPicter() {
        final Calendar calendar = Calendar.getInstance();
        int yy = calendar.get(Calendar.YEAR);
        int mm = calendar.get(Calendar.MONTH);
        int dd = calendar.get(Calendar.DAY_OF_MONTH);

        ContextThemeWrapper context = new ContextThemeWrapper(this,
                android.R.style.Theme_Holo_Light_Dialog_NoActionBar);
        DatePickerDialog dlg = new DatePickerDialog(context, null, yy, mm, dd) {
            @Override
            protected void onCreate(Bundle savedInstanceState) {
                super.onCreate(savedInstanceState);
                LinearLayout mSpinners =
                        (LinearLayout) findViewById(
                                getContext().getResources().getIdentifier("android:id/pickers",
                                        null,
                                        null));
                if (mSpinners != null) {
                    NumberPicker mMonthSpinner = (NumberPicker) findViewById(getContext().getResources().getIdentifier("android:id/month", null, null));
                    NumberPicker mYearSpinner = (NumberPicker) findViewById(getContext().getResources().getIdentifier("android:id/year", null, null));
                    mSpinners.removeAllViews();
                    if (mMonthSpinner != null) {
                        mSpinners.addView(mMonthSpinner);
                    }
                    if (mYearSpinner != null) {
                        mSpinners.addView(mYearSpinner);
                    }
                }
                View dayPickerView = findViewById(getContext().getResources().getIdentifier("android:id/day", null, null));
                if (dayPickerView != null) {
                    dayPickerView.setVisibility(View.GONE);
                }
            }

            @Override
            public void onDateChanged(DatePicker view, int year, int month, int day) {
                super.onDateChanged(view, year, month, day);
                setTitle("请选择信用卡有效期");
            }
        };
        dlg.setTitle("请选择信用卡有效期");
        dlg.show();
    }
}
