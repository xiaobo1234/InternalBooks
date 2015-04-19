package com.xiaobo.collegedesign.internetbooks.Activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import com.xiaobo.collegedesign.internetbooks.Adapters.MainBookAdapter;
import com.xiaobo.collegedesign.internetbooks.Model.Entity.BookInfo;
import com.xiaobo.collegedesign.internetbooks.Model.Entity.ReadInfo;
import com.xiaobo.collegedesign.internetbooks.R;
import com.xiaobo.collegedesign.internetbooks.Utils.FileUtils;
import com.xiaobo.collegedesign.internetbooks.Utils.ToastUtils;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnClick;
import io.realm.Realm;

public class MainActivity extends Activity {

    @InjectView(R.id.main_manage_books) RelativeLayout main_manage_books;
    @InjectView(R.id.main_books_grid) GridView main_books_grid;
    @InjectView(R.id.main_user_enter_img) ImageView main_user_enter_img;

    @InjectView(R.id.main_manage_hint_layout) RelativeLayout main_manage_hint_layout;
    @InjectView(R.id.main_manage_hint_inside_layout) LinearLayout main_manage_hint_inside_layout;
    @InjectView(R.id.book_delete_hint_layout) RelativeLayout book_delete_hint_layout;
    @InjectView(R.id.book_delete_hint_inside_layout) LinearLayout book_delete_hint_inside_layout;

    @OnClick(R.id.main_user_enter) void main_user_enter() {
        //TODO 登录事宜
        Intent intent = new Intent();
        intent.setClass(MainActivity.this, LoginActivity.class);
        startActivity(intent);
    }
    @OnClick(R.id.main_manage_hint_layout) void main_manage_hint_layout() {
        hideManageHint();
    }
    @OnClick(R.id.main_manage_search_book) void main_manage_search_book() {
        hideManageHint();
    }
    @OnClick(R.id.main_manage_add_book) void main_manage_add_book() {
        hideManageHint();
    }
    @OnClick(R.id.book_delete_hint_layout) void book_delete_hint_layout() {
        hideDeleteHint();
    }
    @OnClick(R.id.book_delete_sure) void book_delete_sure() {
        deleteBook();
        hideDeleteHint();
    }
    @OnClick(R.id.book_delete_cancel) void book_delete_cancel() {
        hideDeleteHint();
    }

    private MainBookAdapter mainBookAdapter;
    private List<BookInfo> list;
    private Realm realm;
    private int selected;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.inject(this);

        realm = Realm.getInstance(this.getApplicationContext());
        if (null == realm.where(BookInfo.class).findAll() || realm.where(BookInfo.class).findAll().size() <= 0) {
            BookInfo bookInfo = new BookInfo();
            bookInfo.setBook_id(99999999);
            bookInfo.setBook_name("论语");
            bookInfo.setBook_author("孔子弟子及再传弟子");
            bookInfo.setBook_date("春秋时期");
            bookInfo.setBook_describe("语录体散文集，主要记载孔子及其弟子的言行。");
            bookInfo.setBook_path(FileUtils.getAppFolderPath(this.getApplicationContext()) + File.separator + "TheAnalectsofConfucius.txt");
            realm.beginTransaction();
            realm.copyToRealm(bookInfo);
            realm.commitTransaction();
            try {
                FileUtils.saveFile(this.getResources().getAssets().open("TheAnalectsofConfucius.txt"), FileUtils.getAppFolderPath(this.getApplicationContext()) + File.separator + "TheAnalectsofConfucius.txt");
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        //设置列表
        setBooks();

        clickEvents();

        main_manage_books.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showHint();
            }
        });
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        ButterKnife.reset(this);
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (KeyEvent.KEYCODE_BACK == keyCode && main_manage_hint_layout.getVisibility() == View.VISIBLE) {
            hideManageHint();
            return true;
        }
        if (KeyEvent.KEYCODE_BACK == keyCode && book_delete_hint_layout.getVisibility() == View.VISIBLE) {
            hideDeleteHint();
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }

    private void clickEvents() {
        main_books_grid.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent();
                intent.setClass(MainActivity.this, BookInfoActivity.class);
                intent.putExtra("book_name", list.get(position).getBook_name());
                intent.putExtra("book_id", list.get(position).getBook_id());
                startActivity(intent);
            }
        });
        main_books_grid.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                if (list.get(position).getBook_id() == 99999999 && list.get(position).getBook_name().equals("论语")) {
                    ToastUtils.setToast(MainActivity.this, "对不起，此书内置，不能删除");
                    return true;
                }
                selected = position;
                book_delete_hint_layout.setVisibility(View.VISIBLE);
                book_delete_hint_inside_layout.setVisibility(View.VISIBLE);
                book_delete_hint_inside_layout.startAnimation(AnimationUtils.loadAnimation(MainActivity.this, R.anim.bottom_in));
                return true;
            }
        });
    }

    //显示添加按钮
    private void showHint() {
        main_manage_hint_layout.setVisibility(View.VISIBLE);
        Animation in = AnimationUtils.loadAnimation(MainActivity.this, R.anim.bottom_in);
        main_manage_hint_inside_layout.startAnimation(in);
    }

    //收回添加按钮
    private void hideManageHint() {
        Animation out = AnimationUtils.loadAnimation(MainActivity.this, R.anim.bottom_out);
        main_manage_hint_inside_layout.startAnimation(out);
        main_manage_hint_layout.setVisibility(View.GONE);
    }

    //收回删除按钮
    private void hideDeleteHint() {
        Animation out = AnimationUtils.loadAnimation(MainActivity.this, R.anim.bottom_out);
        out.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {
            }

            @Override
            public void onAnimationEnd(Animation animation) {
                book_delete_hint_inside_layout.setVisibility(View.GONE);
                book_delete_hint_layout.setVisibility(View.GONE);
            }

            @Override
            public void onAnimationRepeat(Animation animation) {
            }
        });
        book_delete_hint_inside_layout.startAnimation(out);
    }

    /**
     * 设置书架列表
     * */
    private void setBooks() {
        if (null == mainBookAdapter) {
            this.list = new ArrayList<>();
            list.addAll(realm.where(BookInfo.class).findAll());
            mainBookAdapter = new MainBookAdapter(MainActivity.this, this.list);
            main_books_grid.setAdapter(mainBookAdapter);
        }else {
            this.list.clear();
            list.addAll(realm.where(BookInfo.class).findAll());
            mainBookAdapter.notifyDataSetChanged();
        }
    }

    private void deleteBook() {
        realm.beginTransaction();
        realm.where(BookInfo.class)
                .equalTo("book_name", list.get(selected).getBook_name())
                .equalTo("book_id", list.get(selected).getBook_id())
                .findAll().clear();
        realm.where(ReadInfo.class)
                .equalTo("book_name", list.get(selected).getBook_name())
                .equalTo("book_id", list.get(selected).getBook_id())
                .findAll().clear();
        realm.commitTransaction();

        setBooks();
    }
}
