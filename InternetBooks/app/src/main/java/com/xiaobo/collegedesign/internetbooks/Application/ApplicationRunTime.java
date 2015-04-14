package com.xiaobo.collegedesign.internetbooks.Application;

import android.app.Application;
import android.content.Context;

import com.xiaobo.collegedesign.internetbooks.Utils.ATLog;
import com.xiaobo.collegedesign.internetbooks.Utils.DensityUtil;
import com.xiaobo.collegedesign.internetbooks.Utils.ShareUtils;

import io.realm.Realm;

/**
 * Created by chenhong on 14/12/15.
 */
public class ApplicationRunTime extends Application {
    private static Context context;

    public final static String ActivityStatus = "ActivityStatus";

    private static ApplicationRunTime instance;

    public static int windowWidth;

    public enum ActivityStatuses {
    };

    public void onCreate(){
        super.onCreate();
        ApplicationRunTime.context = getApplicationContext();
        instance = this;

        windowWidth = DensityUtil.getWindowWidth(ApplicationRunTime.context);

        ATLog.e("ssss", windowWidth + "");

        int realmVersion = ShareUtils.getVersion();

        //数据库迁移
        if(realmVersion != 1)
        {
            Realm.deleteRealmFile(getApplicationContext());
            ShareUtils.saveVersionData(1);
        }
    }

    public static Context getAppContext() {
        return ApplicationRunTime.context;
    }


    public static ApplicationRunTime getInstance() {
        return instance;
    }

    @Override
    protected void attachBaseContext(Context base) {
        super.attachBaseContext(base);
    }
}