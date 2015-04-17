package com.xiaobo.collegedesign.internetbooks.Model.Entity;

import io.realm.RealmObject;

/**
 * Created by Xiaobo on 2015-04-13.
 */
public class ReadInfo extends RealmObject {

    private String book_name;

    private int book_id;

    private int bookmark_place;

    private String bookmark_name;

    public String getBook_name() {
        return book_name;
    }

    public void setBook_name(String book_name) {
        this.book_name = book_name;
    }

    public int getBook_id() {
        return book_id;
    }

    public void setBook_id(int book_id) {
        this.book_id = book_id;
    }

    public int getBookmark_place() {
        return bookmark_place;
    }

    public void setBookmark_place(int bookmark_place) {
        this.bookmark_place = bookmark_place;
    }

    public String getBookmark_name() {
        return bookmark_name;
    }

    public void setBookmark_name(String bookmark_name) {
        this.bookmark_name = bookmark_name;
    }
}
