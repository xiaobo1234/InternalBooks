package com.xiaobo.collegedesign.internetbooks.Activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

import com.xiaobo.collegedesign.internetbooks.Model.Entity.BookInfo;
import com.xiaobo.collegedesign.internetbooks.R;
import com.xiaobo.collegedesign.internetbooks.Utils.ToastUtils;

import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnClick;
import io.realm.Realm;

public class BookInfoActivity extends Activity {

    @InjectView(R.id.book_info_book_name) TextView book_info_book_name;
    @InjectView(R.id.book_info_book_author) TextView book_info_book_author;
    @InjectView(R.id.book_info_book_date) TextView book_info_book_date;
    @InjectView(R.id.book_info_book_describe) TextView book_info_book_describe;

    @OnClick(R.id.book_info_read) void book_info_read() {
        Intent intent = new Intent();
        intent.setClass(this, ReadBookActivity.class);
        intent.putExtra("book_name", book_name);
        intent.putExtra("book_id", book_id);
        intent.putExtra("bookmark_name", "最新阅读书签");
        startActivity(intent);
    }
    @OnClick(R.id.book_info_history) void book_info_history() {
        Intent intent = new Intent();
        intent.setClass(this, ReadInfoActivity.class);
        intent.putExtra("book_name", book_name);
        intent.putExtra("book_id", book_id);
        startActivity(intent);
    }
    @OnClick(R.id.book_info_back) void book_info_back() {
        finish();
    }

    private String book_name;
    private int book_id;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_book_info);
        ButterKnife.inject(this);

        book_name = getIntent().getStringExtra("book_name");
        book_id = getIntent().getIntExtra("book_id", 0);
        if (!"".equals(book_name)) {
            setViews();
        }
    }

    private void setViews() {
        BookInfo bookInfo = Realm.getInstance(this.getApplicationContext()).where(BookInfo.class).equalTo("book_name", book_name).findFirst();

        book_info_book_name.setText(bookInfo.getBook_name());
        book_info_book_author.setText(bookInfo.getBook_author());
        book_info_book_date.setText(bookInfo.getBook_date());
        book_info_book_describe.setText(bookInfo.getBook_describe());
    }
}
