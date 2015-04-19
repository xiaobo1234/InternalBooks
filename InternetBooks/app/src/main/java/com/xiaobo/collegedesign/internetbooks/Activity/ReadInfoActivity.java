package com.xiaobo.collegedesign.internetbooks.Activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.KeyEvent;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.AdapterView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;

import com.xiaobo.collegedesign.internetbooks.Adapters.ReadInfoAdapter;
import com.xiaobo.collegedesign.internetbooks.Model.Entity.ReadInfo;
import com.xiaobo.collegedesign.internetbooks.R;

import java.util.List;

import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnClick;
import io.realm.Realm;

public class ReadInfoActivity extends Activity {

    @InjectView(R.id.read_info_list) ListView read_info_list;
    @InjectView(R.id.read_info_hint_layout) RelativeLayout read_info_hint_layout;
    @InjectView(R.id.read_info_hint_inside_layout) LinearLayout read_info_hint_inside_layout;

    @OnClick(R.id.read_info_back) void read_info_back() {
        this.finish();
    }
    @OnClick(R.id.read_info_sure) void read_info_sure() {
        deleteReadInfo();
        dismissHintViews();
    }
    @OnClick(R.id.read_info_cancel) void read_info_cancel() {
        dismissHintViews();
    }
    @OnClick(R.id.read_info_hint_layout) void read_info_hint_layout() {
        dismissHintViews();
    }

    private ReadInfoAdapter readInfoAdapter;
    private Realm realm;
    private String book_name;
    private int book_id;
    private List<ReadInfo> list;
    private int selected;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_read_info);
        ButterKnife.inject(this);

        book_name = getIntent().getStringExtra("book_name");
        book_id = getIntent().getIntExtra("book_id", 0);
        realm = Realm.getInstance(this.getApplicationContext());
        list = realm.where(ReadInfo.class).equalTo("book_name", book_name).equalTo("book_id", book_id).findAll();
        readInfoAdapter = new ReadInfoAdapter(ReadInfoActivity.this, list);
        read_info_list.setAdapter(readInfoAdapter);
        read_info_list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent();
                intent.setClass(ReadInfoActivity.this, ReadBookActivity.class);
                intent.putExtra("book_name", book_name);
                intent.putExtra("book_id", book_id);
                intent.putExtra("bookmark_name", list.get(position).getBookmark_name());
                startActivity(intent);
            }
        });
        read_info_list.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                selected = position;
                read_info_hint_layout.setVisibility(View.VISIBLE);
                read_info_hint_inside_layout.setVisibility(View.VISIBLE);
                read_info_hint_inside_layout.startAnimation(AnimationUtils.loadAnimation(ReadInfoActivity.this, R.anim.bottom_in));
                return true;
            }
        });
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK && read_info_hint_layout.getVisibility() == View.VISIBLE) {
            dismissHintViews();
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }

    /**
     * 删除书签
     * */
    private void deleteReadInfo() {
        realm.beginTransaction();
        realm.where(ReadInfo.class)
                .equalTo("book_name", book_name)
                .equalTo("book_id", book_id)
                .equalTo("bookmark_name", list.get(selected).getBookmark_name())
                .equalTo("bookmark_place", list.get(selected).getBookmark_place())
                .findAll().clear();
        realm.commitTransaction();
    }

    /**
     * 隐藏删除书签布局
     * */
    private void dismissHintViews() {
        Animation out = AnimationUtils.loadAnimation(ReadInfoActivity.this, R.anim.bottom_out);
        out.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {
            }

            @Override
            public void onAnimationEnd(Animation animation) {
                read_info_hint_inside_layout.setVisibility(View.GONE);
                read_info_hint_layout.setVisibility(View.GONE);
            }

            @Override
            public void onAnimationRepeat(Animation animation) {
            }
        });
        read_info_hint_inside_layout.startAnimation(out);

        list = realm.where(ReadInfo.class).equalTo("book_name", book_name).equalTo("book_id", book_id).findAll();
        readInfoAdapter.notifyDataSetChanged();
    }
}
