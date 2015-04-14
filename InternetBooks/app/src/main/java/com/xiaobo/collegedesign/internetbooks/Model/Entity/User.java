package com.xiaobo.collegedesign.internetbooks.Model.Entity;

import io.realm.RealmObject;

/**
 * Created by Xiaobo on 2015-04-12.
 */
public class User extends RealmObject {

    //用户名
    private String user_name;

    //用户性别
    private String user_gender;

    //用户年龄
    private String user_age;

    public String getUser_name() {
        return user_name;
    }

    public void setUser_name(String user_name) {
        this.user_name = user_name;
    }

    public String getUser_gender() {
        return user_gender;
    }

    public void setUser_gender(String user_gender) {
        this.user_gender = user_gender;
    }

    public String getUser_age() {
        return user_age;
    }

    public void setUser_age(String user_age) {
        this.user_age = user_age;
    }
}
