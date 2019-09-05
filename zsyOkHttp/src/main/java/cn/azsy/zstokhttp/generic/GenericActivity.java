package cn.azsy.zstokhttp.generic;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Toast;

import org.json.JSONException;
import org.json.JSONObject;

import cn.azsy.zstokhttp.R;

/**
 * Created by zsy on 2017/8/8.
 */

public class GenericActivity extends AppCompatActivity {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_generic);
    }
    public void abv(View view){

        OkHttpUtils.ResultCallback<String> resultCallback = new OkHttpUtils.ResultCallback<String>() {
            @Override
            public void onSuccess(String response) {
                String access = null;
                String openId = null;
                try {
                    JSONObject jsonObject = new JSONObject(response);
                    access = jsonObject.getString("access_token");//接口调用凭证
                    openId = jsonObject.getString("openid");//授权用户唯一标识
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
            @Override
            public void onFailure(Exception e) {
                Toast.makeText(GenericActivity.this, "微信授权失败,请稍后重试", Toast.LENGTH_SHORT).show();
            }
        };
        OkHttpUtils.get("http://www.bai.com", resultCallback);





    }
}
