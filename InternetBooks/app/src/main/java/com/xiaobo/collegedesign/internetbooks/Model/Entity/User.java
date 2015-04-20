package com.xiaobo.collegedesign.internetbooks.Model.Entity;

import io.realm.RealmObject;

/**
 * Created by Xiaobo on 2015-04-12.
 */
public class User extends RealmObject {


    public String name;

    public String user_id;

    public String last_login_at;

    public Access_token access_token;

    public static class Access_token{
        public String token;
        public String key;

    }
}
