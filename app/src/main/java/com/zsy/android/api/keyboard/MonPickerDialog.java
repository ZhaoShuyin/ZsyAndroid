package com.zsy.android.api.keyboard;

import android.app.DatePickerDialog;
import android.content.Context;
import android.os.Build;
import android.support.annotation.NonNull;
import android.support.annotation.RequiresApi;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.DatePicker;

public class MonPickerDialog extends DatePickerDialog {
    public MonPickerDialog(Context context, OnDateSetListener callBack, int year, int monthOfYear, int dayOfMonth) {
        super(context, callBack, year, monthOfYear, dayOfMonth);
        this.setTitle(year + "年" + (monthOfYear + 1) + "月");
        DatePicker datePicker = getDatePicker();
        for (int i = 0; i < datePicker.getChildCount(); i++) {
            View childAt = datePicker.getChildAt(i);
            Log.e("XmlActivity", "第一层: <" + i + ">" + childAt.getClass().getName());
            if (childAt.getClass().isAssignableFrom(ViewGroup.class)) {
                ViewGroup viewGroup = (ViewGroup) childAt;
                for (int j = 0; j < viewGroup.getChildCount(); j++) {
                    View childAt1 = viewGroup.getChildAt(j);
                    Log.e("XmlActivity", "第二层: <" + j + ">" + childAt1.getClass().getName());
                    if (childAt1.getClass().isAssignableFrom(ViewGroup.class)) {
                        ViewGroup viewGroup2 = (ViewGroup) childAt;
                        for (int j2 = 0; j2 < viewGroup2.getChildCount(); j2++) {
                            View childAt2 = viewGroup.getChildAt(j2);
                            Log.e("XmlActivity", "第三层: <" + j2 + ">" + childAt2.getClass().getName());
                        }
                    }
                }
            }
        }
//        ViewGroup childAt = (ViewGroup) datePicker.getChildAt(0);
//        Log.e("XmlActivity", "111: " + childAt.getChildCount() +" "+childAt.getClass().getName());
//        ViewGroup childAt2 = (ViewGroup) childAt.getChildAt(0);
//        Log.e("XmlActivity", "222: " + childAt2.getChildCount()+" "+childAt2.getClass().getName());
//        View childAt3 = childAt2.getChildAt(0);
//        childAt3.setBackgroundColor(0xffffdddd);
//        Log.e("XmlActivity", "childAt3: " + childAt3.getClass().getName());
//        ((ViewGroup) ((ViewGroup) this.getDatePicker()
//                .getChildAt(0))
//                .getChildAt(0))
//                .getChildAt(2)
//                .setVisibility(View.GONE);


    }


    @RequiresApi(api = Build.VERSION_CODES.N)
    public MonPickerDialog(@NonNull Context context, int themeResId) {
        super(context, themeResId);
        this.setTitle("123");
    }

    @Override
    public void onDateChanged(DatePicker view, int year, int month, int day) {
        super.onDateChanged(view, year, month, day);
        this.setTitle(year + "年" + (month + 1) + "月");
    }

}