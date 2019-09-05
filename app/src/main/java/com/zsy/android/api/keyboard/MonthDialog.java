package com.zsy.android.api.keyboard;

import android.app.DatePickerDialog;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.ContextThemeWrapper;
import android.view.View;

/**
 * Title: ZsyAndroid
 * <p>
 * Description:
 * </p>
 *
 * @author Zsy
 * @date 2019/8/16 16:53
 */

public class MonthDialog extends DatePickerDialog {
    public MonthDialog(@NonNull Context context, @Nullable OnDateSetListener listener, int year, int month, int dayOfMonth) {
        super(context, listener, year, month, dayOfMonth);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        View dayPickerView = findViewById(getContext().getResources().getIdentifier("android:id/day", null, null));
        if (dayPickerView != null) {
            dayPickerView.setVisibility(View.GONE);
        }
    }
}
