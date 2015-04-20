package com.xiaobo.collegedesign.internetbooks.Utils;

import android.util.Log;

/**
 * Created by xiaobo on 15/1/9.
 */
public class ATLog {

//    public static boolean isDebugging = ApplicationRunTime.getAppContext().getResources().getBoolean(R.bool.isDebug);
    public static boolean isDebugging = true;
    private static final String TAG = "InternetBook-------------";

    public static void e(String tag, String message){
        if (!isDebugging) {
            return;
        }
        Log.e(new StringBuilder(TAG).append(tag).toString(),message);
    }


    public static void d(String tag, String message){
        if (!isDebugging) {
            return;
        }
        Log.d(new StringBuilder(TAG).append(tag).toString(),message);
    }

    public static void i(String tag, String message){
        if (!isDebugging) {
            return;
        }
        Log.i(new StringBuilder(TAG).append(tag).toString(),message);
    }

    public static void v(String tag, String message){
        if (!isDebugging) {
            return;
        }
        Log.v(new StringBuilder(TAG).append(tag).toString(),message);
    }


    public static void w(String tag, String message){
        if (!isDebugging) {
            return;
        }
        Log.w(new StringBuilder(TAG).append(tag).toString(),message);
    }

}
