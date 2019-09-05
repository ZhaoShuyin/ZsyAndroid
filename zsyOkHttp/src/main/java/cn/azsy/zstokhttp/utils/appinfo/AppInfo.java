package cn.azsy.zstokhttp.utils.appinfo;

import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.util.Log;

/**
 * Created by zsy on 2017/5/24.
 */

public class AppInfo {

    //获取应用版本信息
    public static int getAppVersionCode(Context context){
        try {
            PackageManager pm = context.getPackageManager();
            PackageInfo packageInfo = pm.getPackageInfo(context.getPackageName(), 0);
            int versionCode = packageInfo.versionCode;
            String versionName = packageInfo.versionName;
            Log.i("hahastart","versionCode == "+versionCode+" , versionName=="+versionName);
            return versionCode;
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
        return 0;
    }


}
