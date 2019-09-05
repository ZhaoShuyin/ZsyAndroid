package com.zsy.android.layout.fragment.samlpe;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.AttributeSet;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.widget.TextView;

import com.zsy.android.R;

/**
 * Title: ZsyGit
 * <p>
 * Description:Fragment生命周期
 * </p>
 *
 * @author Zsy
 * @date 2019/5/27 11:22
 */
@SuppressLint("ValidFragment")
public class PeriodFragment extends Fragment {

    String TAG = "PeriodFragment";
    String flag;

    /**
     * onAttach
     * onCreate
     * onCreateView
     * onViewCreated
     * onActivityCreated
     * onStart
     * onResume
     * onPause
     * onDestroyView
     * onDestroy
     * onDetach
     *
     * @param flag Bundle bundle = getArguments()
     */
    public PeriodFragment(String flag) {
        this.flag = flag;
        Log.e(TAG, flag + "- PeriodFragment: 创建");
    }

    @Override
    public void onInflate(Context context, AttributeSet attrs, Bundle savedInstanceState) {
        super.onInflate(context, attrs, savedInstanceState);
        Log.e(TAG, flag + "- onInflate: ");
    }

    @Override
    public Animation onCreateAnimation(int transit, boolean enter, int nextAnim) {
        Log.e(TAG, "onCreateAnimation: ");
        return super.onCreateAnimation(transit, enter, nextAnim);
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        Log.e(TAG, "onConfigurationChanged: ");
        super.onConfigurationChanged(newConfig);
    }

    @Override
    public void onAttachFragment(Fragment childFragment) {
        super.onAttachFragment(childFragment);
        Log.e(TAG, "onAttachFragment: " );
    }

    @Override
    public void onHiddenChanged(boolean hidden) {
        super.onHiddenChanged(hidden);
        Log.e(TAG, flag + "- onHiddenChanged: hidden== " + hidden);
    }

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        Log.e(TAG, flag + "- setUserVisibleHint: isVisibleToUser== " + isVisibleToUser);
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        Log.e(TAG, flag + "- onAttach: ");
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.e(TAG, flag + "- onCreate: ");
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        Log.e(TAG, flag + "- onCreateView: ");
        View inflate = inflater.inflate(R.layout.fragment_layout, container, false);
        return inflate;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        TextView textView = view.findViewById(R.id.tv_fragment);
        textView.setText(flag);
        Log.e(TAG, flag + "- onViewCreated: ");
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        Log.e(TAG, flag + "- onActivityCreated: ");
    }

    @Override
    public void onStart() {
        super.onStart();
        Log.e(TAG, flag + "- onStart: ");
    }

    @Override
    public void onResume() {
        super.onResume();
        Log.e(TAG, flag + "- onResume: ");
    }

    @Override
    public void onPause() {
        super.onPause();
        Log.e(TAG, flag + "- onPause: ");
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        Log.e(TAG, flag + "- onDestroyView: ");
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        Log.e(TAG, flag + "- onDestroy: ");
    }

    @Override
    public void onDetach() {
        super.onDetach();
        Log.e(TAG, flag + "- onDetach: ");
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        Log.e(TAG, flag + "- onActivityResult: ");
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        Log.e(TAG, flag + "- onRequestPermissionsResult: ");
    }
}
