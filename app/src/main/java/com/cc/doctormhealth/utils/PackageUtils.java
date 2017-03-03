package com.cc.doctormhealth.utils;

import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;

/**
 * 获取版本号
 * 创建人：吴聪聪
 * 邮箱:cc112837@163.com
 * 创建时间：2016/3/31 11:24
*/
public class PackageUtils {

    public static String getPackageVersion(Context context){
        String version = "1.0";
        //获取PackageManager
        PackageManager packageManager = context.getPackageManager();

        try {
            //获取PackageInfo
            PackageInfo packageInfo =
                    packageManager.getPackageInfo(context.getPackageName(), PackageManager.GET_ACTIVITIES);
            //获取版本号
            version = packageInfo.versionName;

        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }

        return  version;
    }
}
