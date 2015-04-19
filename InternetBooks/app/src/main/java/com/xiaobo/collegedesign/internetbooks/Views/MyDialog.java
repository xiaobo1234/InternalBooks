package com.xiaobo.collegedesign.internetbooks.Views;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.xiaobo.collegedesign.internetbooks.Model.Entity.BookInfo;
import com.xiaobo.collegedesign.internetbooks.Model.Entity.ReadInfo;
import com.xiaobo.collegedesign.internetbooks.R;
import com.xiaobo.collegedesign.internetbooks.Utils.ATLog;
import com.xiaobo.collegedesign.internetbooks.Utils.ToastUtils;

import io.realm.Realm;

/**
 * Created by Xiaobo on 2015-04-18.
 */
public class MyDialog extends Dialog {

    private EditText dialog_name_read_info;
    private TextView dialog_sure, dialog_cancel;
    private int read_place;
    private BookInfo bookInfo;
//    private LinearLayout dialog_name_read_info_layout;

    public MyDialog(Context context) {
        super(context);
    }

    public MyDialog(Context context, int theme) {
        super(context, theme);
    }

    public MyDialog(Context context, BookInfo bookInfo, int read_place) {
        super(context);
        this.bookInfo = bookInfo;
        this.read_place = read_place;
    }

    public MyDialog(Context context, boolean cancelable, OnCancelListener cancelListener) {
        super(context, cancelable, cancelListener);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.read_info_dialog);

        dialog_name_read_info = (EditText)findViewById(R.id.dialog_name_read_info);
//        dialog_name_read_info_layout = (LinearLayout) findViewById(R.id.dialog_name_read_info_layout);
        dialog_sure = (TextView)findViewById(R.id.dialog_sure);
        dialog_sure.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                doSaveAndUpload();

                MyDialog.this.dismiss();
            }
        });
        dialog_cancel = (TextView)findViewById(R.id.dialog_cancel);
        dialog_cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MyDialog.this.dismiss();
            }
        });
    }

//    public void setWindowLayout() {
//        Window window = MyDialog.this.getWindow();
//        WindowManager.LayoutParams lp = window.getAttributes();
//        lp.width = dialog_name_read_info_layout.getWidth(); // 宽度
//        lp.height = dialog_name_read_info_layout.getHeight(); // 高度
//        ATLog.e("", "lp.width=" +  lp.width + ",lp.height=" + lp.height);
//        window.setAttributes(lp);
//    }

    private String getDialog_name_read_info() {
        return dialog_name_read_info.getText().toString();
    }

    private void doSaveAndUpload() {
        String temp = getDialog_name_read_info();
        if (null == temp || temp.length() <= 0) {
            ToastUtils.setToast(getContext(), "对不起，书签名不能为空");
            return;
        }
        ReadInfo readInfo = new ReadInfo();
        readInfo.setBook_id(bookInfo.getBook_id());
        readInfo.setBook_name(bookInfo.getBook_name());
        readInfo.setBookmark_name(temp);
        readInfo.setBookmark_place(read_place);
        //TODO 上传书签

        Realm realm = Realm.getInstance(getContext());
        realm.beginTransaction();
        realm.copyToRealm(readInfo);
        realm.commitTransaction();
    }
}
