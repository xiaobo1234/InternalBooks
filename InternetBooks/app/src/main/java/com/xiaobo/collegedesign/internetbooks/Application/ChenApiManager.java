package com.xiaobo.collegedesign.internetbooks.Application;

import com.xiaobo.collegedesign.internetbooks.Model.Entity.User;

import java.util.List;

import rx.Observable;
import rx.Observer;
import rx.Subscriber;
import rx.Subscription;
import rx.schedulers.Schedulers;
import rx.subscriptions.Subscriptions;

/**
 * Created by chen on 14-7-19.
 */
public class ChenApiManager extends MainApiManager{

    private static final ChenApiInterface.ApiManagerLogin LoginapiManager = restAdapter.create(ChenApiInterface.ApiManagerLogin.class);
    public static Observable<User> Login(final String phone_number, final String password) {
        return Observable.create(new Observable.OnSubscribe<User>() {
            @Override
            public void call(Subscriber<? super User> subscriber) {
                try {
                    subscriber.onNext(LoginapiManager.login(phone_number, password));
                    subscriber.onCompleted();
                } catch (Exception e) {
                    subscriber.onError(e);
                }
            }
        }).subscribeOn(Schedulers.newThread());
    }
}
