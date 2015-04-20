package com.xiaobo.collegedesign.internetbooks.Utils;

import android.content.Context;
import android.content.SharedPreferences;

/**
 * Created by chenhong on 15/1/9.
 */
public class ShareUtils {

    public static String getKey(Context context) {
        SharedPreferences TokenShared = context.getSharedPreferences("usermessage", 0);
        return TokenShared.getString("key", "");
    }

    public static String getToken(Context context) {
        SharedPreferences TokenShared = context.getSharedPreferences("usermessage", 0);
        return TokenShared.getString("token", "");
    }

    public static void deleteTokenKey(Context context) {
        SharedPreferences Shared = context.getSharedPreferences("usermessage", 0);
        SharedPreferences.Editor editor = Shared.edit();
        editor.clear().commit();
    }

    public static String getId(Context context) {
        SharedPreferences TokenShared = context.getSharedPreferences("usermessage", 0);
        return TokenShared.getString("id", "");
    }

    public static boolean getBoolean() {
        SharedPreferences TokenShared = ApplicationRunTime.getAppContext().getSharedPreferences("usermessage", 0);
        return TokenShared.getBoolean("boolean", false);
    }

    public static String getKey() {
        SharedPreferences TokenShared = ApplicationRunTime.getAppContext().getSharedPreferences("usermessage", 0);
        return TokenShared.getString("key", "");
    }

    public static String getToken() {
        SharedPreferences TokenShared = ApplicationRunTime.getAppContext().getSharedPreferences("usermessage", 0);
        return TokenShared.getString("token", "");
    }

    public static void deleteTokenKey() {
        SharedPreferences Shared = ApplicationRunTime.getAppContext().getSharedPreferences("usermessage", 0);
        SharedPreferences.Editor editor = Shared.edit();
        editor.clear().commit();
    }

    public static String getId() {
        SharedPreferences TokenShared = ApplicationRunTime.getAppContext().getSharedPreferences("usermessage", 0);
        return TokenShared.getString("id", "");
    }



    public static void saveData(){
        SharedPreferences.Editor editor;
        SharedPreferences mshared;
        mshared = ApplicationRunTime.getAppContext().getSharedPreferences("usermessage", 0);
        editor = mshared.edit();
        editor.putBoolean("boolean", true);
        editor.commit();
    }


    public static void saveVersionData(int version ){
        SharedPreferences.Editor editor;
        SharedPreferences mshared;
        mshared = ApplicationRunTime.getAppContext().getSharedPreferences("version", 0);
        editor = mshared.edit();
        editor.putInt("version", version);
        editor.commit();
    }


    public static int getVersion(){
        SharedPreferences TokenShared = ApplicationRunTime.getAppContext().getSharedPreferences("version", 0);
        return TokenShared.getInt("version", 0);
    }
}
