package com.xiaobo.collegedesign.internetbooks.Application;

import com.xiaobo.collegedesign.internetbooks.Model.Entity.User;

import java.util.List;

import retrofit.http.GET;
import retrofit.http.POST;
import retrofit.http.Path;
import retrofit.http.Query;

/**
 * Created by chen on 14-7-28.
 */
public class ChenApiInterface {
//    public interface ApiManagerVerificationCode {
//        @GET("/users/mobile_verification_code")
//        UploadBackData getVerificationCode(@Query("phone_number") String phone_number);
//    }

    public interface ApiManagerLogin {
        @POST("/users/login")
        User login(@Query("username") String username, @Query("password") String password);
    }

}
