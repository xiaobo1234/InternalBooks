package com.xiaobo.collegedesign.internetbooks.Activity;

import android.app.Activity;
import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.widget.EditText;

import com.xiaobo.collegedesign.internetbooks.Application.ChenApiManager;
import com.xiaobo.collegedesign.internetbooks.Application.MainApiManager;
import com.xiaobo.collegedesign.internetbooks.Model.Entity.User;
import com.xiaobo.collegedesign.internetbooks.R;
import com.xiaobo.collegedesign.internetbooks.Utils.EncodeUtils;
import com.xiaobo.collegedesign.internetbooks.Utils.ToastUtils;

import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnClick;
import io.realm.Realm;
import rx.android.concurrency.AndroidSchedulers;
import rx.functions.Action1;

/**
 * Created by xiaobo on 14/12/15.
 */
public class LoginActivity extends Activity {

    @InjectView(R.id.activity_login_username) EditText activity_login_username;
    @InjectView(R.id.activity_login_password) EditText activity_login_password;

    @OnClick(R.id.activity_login_register) void activity_login_register() {
        startActivityForResult(new Intent(LoginActivity.this, RegisterActivity.class), 800);
    }
    @OnClick(R.id.activity_login_cancel) void activity_login_cancel() {
        LoginActivity.this.finish();
    }

    private String imagePath = "";
    private String gender = "";
    private String name = "";

    private Dialog activity_login_dialog;
    private static boolean isLogining = false;
    private Realm realm;
    private int Oauth_type;
    private long lastOauthTime;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        ButterKnife.inject(this);

        realm = Realm.getInstance(this.getApplicationContext());

        activity_login_dialog = new Dialog(LoginActivity.this, R.style.MyDialog);
        activity_login_dialog.setContentView(R.layout.dialog);
        activity_login_dialog.dismiss();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        ButterKnife.reset(this);
    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        if (activity_login_dialog.isShowing()) {
            return true;
        }
        return super.dispatchTouchEvent(ev);
    }

    private void login()
    {
        String username = activity_login_username.getText().toString().trim();
        String password = activity_login_password.getText().toString().trim();

        if(username.equals(""))
        {
            ToastUtils.setToast(this, "请输入电话号码");
        }
        else if(password.equals(""))
        {
            ToastUtils.setToast(this,"请输入密码");
        }
        else{
            userLogin(username, EncodeUtils.MD5(EncodeUtils.MD5(password)), new MainApiManager.FialedInterface() {
                @Override
                public void onSuccess(Object object) {

//                    RegisterBackData registerBackData = (RegisterBackData) object;
//                    mshared = getActivity().getSharedPreferences("usermessage", 0);
//                    editor = mshared.edit();
//                    editor.putString("token", registerBackData.access_token.token);
//                    editor.putString("key", registerBackData.access_token.key);
//                    editor.putString("id", registerBackData.user_id);
//                    editor.commit();
//                    handler.obtainMessage(Constant.MSG_SUCCESS).sendToTarget();

                }

                @Override
                public void onFailth(int code) {
//                    ErrorUtils.setError(code, this);
                }

                @Override
                public void onOtherFaith() {
                    ToastUtils.setToast(LoginActivity.this, "发生错误");
                }

                @Override
                public void onNetworkError() {
                    ToastUtils.setToast(LoginActivity.this, "网络错误");
                }
            });
        }

    }

    private void userLogin(String phone_number,String password, final MainApiManager.FialedInterface fialedInterface)
    {
        ChenApiManager.Login(phone_number, password).observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Action1<User>() {
                    @Override
                    public void call(User user) {
                        fialedInterface.onSuccess(user);
                    }
                }, new Action1<Throwable>() {
                    @Override
                    public void call(Throwable throwable) {
//                        ErrorUtils.SetThrowable(throwable,fialedInterface);
                    }
                });
    }
}
