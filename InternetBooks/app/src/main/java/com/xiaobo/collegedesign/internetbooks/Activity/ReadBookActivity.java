package com.xiaobo.collegedesign.internetbooks.Activity;

import java.io.IOException;
import java.util.List;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnTouchListener;
import android.view.Window;
import android.view.WindowManager;

import com.xiaobo.collegedesign.internetbooks.Model.Entity.BookInfo;
import com.xiaobo.collegedesign.internetbooks.Model.Entity.ReadInfo;
import com.xiaobo.collegedesign.internetbooks.R;
import com.xiaobo.collegedesign.internetbooks.Utils.ATLog;
import com.xiaobo.collegedesign.internetbooks.Utils.DensityUtil;
import com.xiaobo.collegedesign.internetbooks.Utils.ToastUtils;
import com.xiaobo.collegedesign.internetbooks.Views.BookPageFactory;
import com.xiaobo.collegedesign.internetbooks.Views.MyDialog;
import com.xiaobo.collegedesign.internetbooks.Views.PageWidget;

import io.realm.Realm;
import io.realm.RealmResults;

public class ReadBookActivity extends Activity {
    /** Called when the activity is first created. */
    private PageWidget mPageWidget;
    private Bitmap mCurPageBitmap, mNextPageBitmap;
    private Canvas mCurPageCanvas, mNextPageCanvas;
    private BookPageFactory pagefactory;

    private Realm realm;
    private BookInfo bookInfo;

    private boolean isFirstIn;
    private float xTouch, yTouch;
    private long touchTime;

    private MyDialog dialog;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);

        isFirstIn = true;

        realm = Realm.getInstance(this.getApplicationContext());

        mPageWidget = new PageWidget(this);
        setContentView(mPageWidget);

        mCurPageBitmap = Bitmap.createBitmap(DensityUtil.getWindowWidth(this), DensityUtil.getWindowHeight(this), Bitmap.Config.ARGB_8888);
        mNextPageBitmap = Bitmap.createBitmap(DensityUtil.getWindowWidth(this), DensityUtil.getWindowHeight(this), Bitmap.Config.ARGB_8888);

        mCurPageCanvas = new Canvas(mCurPageBitmap);
        mNextPageCanvas = new Canvas(mNextPageBitmap);
        pagefactory = new BookPageFactory(DensityUtil.getWindowWidth(this), DensityUtil.getWindowHeight(this));

        pagefactory.setBgBitmap(BitmapFactory.decodeResource(
                this.getResources(), R.drawable.bg));

        mPageWidget.setBitmaps(mCurPageBitmap, mCurPageBitmap);

        try {
            bookInfo = realm.where(BookInfo.class)
                    .equalTo("book_name", getIntent().getStringExtra("book_name"))
                    .equalTo("book_id", getIntent().getIntExtra("book_id", 0)).findFirst();
            pagefactory.openbook(bookInfo.getBook_path());
            pagefactory.onDraw(mCurPageCanvas);
        } catch (IOException e1) {
            // TODO Auto-generated catch block
            e1.printStackTrace();
            ToastUtils.setToast(this, "该电子书不存在,请先下载");
        }

        mPageWidget.setOnTouchListener(new OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent e) {
                // TODO Auto-generated method stub

                boolean ret = false;
                if (v == mPageWidget) {
                    if (e.getAction() == MotionEvent.ACTION_DOWN) {
                        mPageWidget.abortAnimation();
                        mPageWidget.calcCornerXY(e.getX(), e.getY());

                        pagefactory.onDraw(mCurPageCanvas);
                        if (mPageWidget.DragToRight()) {
                            try {
                                pagefactory.prePage();
                            } catch (IOException e1) {
                                // TODO Auto-generated catch block
                                e1.printStackTrace();
                            }
                            if(pagefactory.isfirstPage()) return false;
                            pagefactory.onDraw(mNextPageCanvas);
                        } else {
                            try {
                                pagefactory.nextPage();
                            } catch (IOException e1) {
                                // TODO Auto-generated catch block
                                e1.printStackTrace();
                            }
                            if(pagefactory.islastPage()) return false;
                            pagefactory.onDraw(mNextPageCanvas);
                        }
                        mPageWidget.setBitmaps(mCurPageBitmap, mNextPageBitmap);
                    }

//                    ToastUtils.setToast(ReadBookActivity.this, "Read_place=" + pagefactory.getRead_place());

                    ret = mPageWidget.doTouchEvent(e);
                    return ret;
                }
                return false;
            }

        });
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_MENU) {
            dialog = new MyDialog(ReadBookActivity.this, bookInfo, pagefactory.getRead_place());
//            dialog.getContext().setTheme();
            dialog.show();
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (isFirstIn) {
            isFirstIn = false;
            if (null != realm.where(ReadInfo.class).equalTo("book_name", getIntent().getStringExtra("book_name")).findAll()) {
                List<ReadInfo> readInfos = realm.where(ReadInfo.class)
                        .equalTo("book_name", getIntent().getStringExtra("book_name"))
                        .equalTo("book_id", getIntent().getIntExtra("book_id", 0))
                        .equalTo("bookmark_name", getIntent().getStringExtra("bookmark_name")).findAll();
                ReadInfo readInfo = null;
                if (null != readInfos && readInfos.size() > 0) {
                    readInfo = readInfos.get(readInfos.size() - 1);
                }
                pagefactory.setPageTo((null != readInfo ? readInfo.getBookmark_place() : 0), mCurPageCanvas);
//                ATLog.e("阅读位置", "readInfo.getTarget_place==" + (null != readInfo ? readInfo.getBookmark_place() : 0));
            }
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
        ReadInfo readInfo = new ReadInfo();
        readInfo.setBook_id(bookInfo.getBook_id());
        readInfo.setBook_name(bookInfo.getBook_name());
        readInfo.setBookmark_place(pagefactory.getRead_place());
        readInfo.setBookmark_name("最新阅读书签");
        ATLog.e("阅读位置", "pagefactory.getRead_place==" + pagefactory.getRead_place() + "pagefactory.getM_mbBufLen==" + pagefactory.getM_mbBufLen());
        realm.beginTransaction();
        RealmResults realmResults = realm.where(ReadInfo.class)
                .equalTo("book_name", bookInfo.getBook_name())
                .equalTo("book_id", bookInfo.getBook_id())
                .equalTo("bookmark_name", "最新阅读书签").findAll();
        if (null != realmResults && realmResults.size() > 0) {
            realmResults.clear();
        }
        realm.copyToRealm(readInfo);
        realm.commitTransaction();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (null != dialog && dialog.isShowing()) {
            dialog.dismiss();
        }
    }
}
