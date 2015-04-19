package com.xiaobo.collegedesign.internetbooks.Activity;

import android.app.Activity;
import android.app.Dialog;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import com.xiaobo.collegedesign.internetbooks.R;

import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnClick;

public class RegisterActivity extends Activity {

    @InjectView(R.id.activity_register_username)
    EditText activity_register_username;
    @InjectView(R.id.activity_login_password)
    EditText activity_login_password;
    @InjectView(R.id.activity_register_insure_password)
    EditText activity_register_insure_password;

    private Dialog dialog;

    @OnClick({R.id.activity_register_button, R.id.activity_register_to_login})
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.activity_register_button:
                break;
            case R.id.activity_register_to_login:
                setResult(801);
                RegisterActivity.this.finish();
                break;
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        ButterKnife.inject(this);

        dialog = new Dialog(RegisterActivity.this, R.style.MyDialog);
        dialog.setContentView(R.layout.dialog);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        ButterKnife.reset(this);
    }

}
