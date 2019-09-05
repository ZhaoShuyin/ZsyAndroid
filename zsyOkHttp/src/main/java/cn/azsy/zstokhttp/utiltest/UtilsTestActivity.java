package cn.azsy.zstokhttp.utiltest;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.afollestad.materialdialogs.MaterialDialog;

import cn.azsy.zstokhttp.R;
import cn.azsy.zstokhttp.utils.mobiledata.HardwareData;
import cn.azsy.zstokhttp.utils.mobiledata.MobileData;
import cn.azsy.zstokhttp.utils.mobiledata.MobileStatus;
import cn.azsy.zstokhttp.utils.utilclass.DialogUtils;

/**
 * Created by zsy on 2017/6/25.
 */

public class UtilsTestActivity extends AppCompatActivity {


    private TextView show;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_utils);
        show = (TextView) findViewById(R.id.utils_show);
    }

    public void reDefalut(View view) {
        show.setText("恢复默认");
    }

    public void onBt1(View view) {
        String data = HardwareData.getHardwareData();
        show.setText(data);
    }

    public void onBt2(View view) {
        show.setText(HardwareData.getAndroidId(this));
    }

    public void onBt3(View view) {
        show.setText(HardwareData.getBlueToothAddress(this));
    }

    public void onBt4(View view) {
        show.setText(HardwareData.getIMEI(this));
    }

    public void onBt5(View view) {
        show.setText(HardwareData.getMacAdress(this));
    }

    public void onBt6(View view) {
        show.setText(HardwareData.getSimSerNumber(this));
    }

    public void onBt7(View view) {
        show.setText("屏幕宽度" + MobileData.getPrintWidth(this));
    }

    public void onBt8(View view) {
        show.setText("屏幕高度度" + MobileData.getPrintHeight(this));
    }

    public void onBt9(View view) {
        show.setText("100的dp值 == " + MobileData.getDpPx(this, 100));
    }

    public void onBt10(View view) {
        show.setText("100的sp值 == " + MobileData.getSpPx(this, 100));
    }

    public void onBt11(View view) {
        show.setText("系统版本 == " + MobileData.getAndroidVersion());
    }

    public void onBt12(View view) {
        show.setText("APP内存限制 == " + MobileStatus.getSimpleMemoryData());
    }

    public void onBt13(View view) {
        String s = MobileStatus.getSystemTime();
        show.setText(s);
    }

    public void onBt14(View view) {
        String s = MobileStatus.getPhoneNumber(this);
        show.setText(s);
    }

    public void onBt15(View view) {
        String s = MobileStatus.getSDTotalSizeString(this);
        show.setText(s);
    }

    public void onBt16(View view) {
        String s = MobileStatus.getSDUseableSizeString(this);
        show.setText(s);
    }

    public void onBt17(View view) {
        show.setText("网络连接  == "+MobileStatus.judgeNetConnect(this));
    }

    public void onBt18(View view) {
        show.setText("网络可用  == "+MobileStatus.judgeNetAble(this));
    }

    public void onBt19(View view) {
        show.setText("3G网络 == "+MobileStatus.judge3G(this));
    }

    public void onBt20(View view) {
        show.setText("Wifi网络 == "+MobileStatus.judgeWifi(this));
    }

    public void onBt21(View view) {
        show.setText("定位打开 == "+MobileStatus.judgeGPS(this));
    }
    public void onBt22(View view) {
        show.setText("Wifi网络 == "+MobileStatus.judgeWifi(this));
    }
    public void onBt23(View view) {
        show.setText("运行内存大小 == "+MobileStatus.getMemoryString(this));
    }
    public void onBt24(View view) {
        show.setText("SIM序列号 == "+MobileStatus.getSimSerNumber(this));
    }


    public void onBtA(View view){
        startActivity(new Intent(this,UtilsTestActivity2.class));
    }
    public void onBtB(View view){
        DialogUtils.showMyListDialog(this, "表头", R.array.gankHeadLineTypes, new DialogUtils.OnDialogListCallback() {
            @Override
            public void onSelection(MaterialDialog dialog, View itemView, int position, CharSequence text) {
                Toast.makeText(UtilsTestActivity.this, "position=="+position, Toast.LENGTH_SHORT).show();
            }
        });
    }
}
