package com.xiaobo.collegedesign.internetbooks.Activity;

import android.app.Activity;
import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.MotionEvent;
import android.widget.EditText;

import com.xiaobo.collegedesign.internetbooks.R;

import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnClick;
import io.realm.Realm;

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
}
