package com.xiaobo.collegedesign.internetbooks.Application;

import retrofit.RestAdapter;

/**
 * Created by chen on 14-7-24.
 */
public class MainApiManager {

    public static String path = "http://back-end.sohuapps.com/v1";
    public static final RestAdapter restAdapter = new RestAdapter.Builder()
            .setEndpoint(path)
            .build();


    public static abstract interface FialedInterface{
        public abstract void onSuccess(Object object);
        public abstract void onFailth(int code);
        public abstract void onOtherFaith();
        public abstract void onNetworkError();
    }
}
