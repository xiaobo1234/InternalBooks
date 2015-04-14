package com.xiaobo.collegedesign.internetbooks.Utils;

import android.content.Context;
import android.widget.Toast;

import com.xiaobo.collegedesign.internetbooks.Application.ApplicationRunTime;

/**
 * Created by chen on 14-8-16.
 */
public class ToastUtils {
//    private static Context context = ApplicationRunTime.getAppContext();
    public static void setToast(Context context, String message) {
        Toast.makeText(context, message, Toast.LENGTH_SHORT).show();
    }

    public static void setToast(Context context, int message) {
        Toast.makeText(context, message, Toast.LENGTH_SHORT).show();
    }
}
