package com.xiaobo.collegedesign.internetbooks.Model.Entity;

import io.realm.RealmObject;

/**
 * Created by Xiaobo on 2015-04-13.
 */
public class ReadInfo extends RealmObject {

    private String book_name;

    private int book_id;

    private long target_place;

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

    public long getTarget_place() {
        return target_place;
    }

    public void setTarget_place(long target_place) {
        this.target_place = target_place;
    }
}
