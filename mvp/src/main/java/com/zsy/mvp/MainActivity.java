package com.zsy.mvp;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.TextView;

import com.google.gson.Gson;
import com.zsy.mvp.net.BaseObjectBean;
import com.zsy.mvp.net.LoginBean;
import com.zsy.mvp.net.Net;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class MainActivity extends Activity {


    @BindView(R.id.tv_show)
    TextView tvShow;
    @BindView(R.id.bt_retrofit)
    Button btRetrofit;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
    }


    @OnClick(R.id.bt_retrofit)
    public void onViewClicked() {
        BaseObjectBean objectBean = new BaseObjectBean<LoginBean>();
        objectBean.setErrorCode(101);
        objectBean.setErrorMsg("错误信息");
        LoginBean result = new LoginBean();
        result.setEmail("zhao@163.com");
        result.setId(11);
        result.setIcon("头像地址URL");
        result.setUsername("用户名称");
        result.setPassword("123456");
        objectBean.setResult(result);
        Log.e("NETNET",new Gson().toJson(objectBean));
    }
}
