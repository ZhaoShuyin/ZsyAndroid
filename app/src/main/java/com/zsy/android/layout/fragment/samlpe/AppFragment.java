package com.zsy.android.layout.fragment.samlpe;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

/**
 * Title: ZsyAndroid
 * <p>
 * Description:
 * </p>
 *
 * @author Zsy
 * @date 2019/8/23 10:15
 */

public class AppFragment extends Fragment {

    private TextView textView;

    public void show(String s) {
        if (textView != null) {
            textView.setText(textView.getText()+"\n"+s);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        textView = new TextView(container.getContext());
        textView.setTextSize(20);
        String tag = getTag();
        textView.setText("app-Fragment  tag ==" + tag);
        return textView;
    }
}
