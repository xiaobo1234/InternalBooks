package com.xiaobo.collegedesign.internetbooks.Model.Entity;

import io.realm.RealmObject;

/**
 * Created by Xiaobo on 2015-04-12.
 */
public class BookInfo extends RealmObject {

    private int book_id;

    //书名
    private String book_name;

    //作者
    private String book_author;

    //简介
    private String book_describe;

    //日期
    private String book_date;

    //大小
    private String book_size;

    //大小
    private String book_path;

    public int getBook_id() {
        return book_id;
    }

    public void setBook_id(int book_id) {
        this.book_id = book_id;
    }

    public String getBook_name() {
        return book_name;
    }

    public void setBook_name(String book_name) {
        this.book_name = book_name;
    }

    public String getBook_author() {
        return book_author;
    }

    public void setBook_author(String book_author) {
        this.book_author = book_author;
    }

    public String getBook_describe() {
        return book_describe;
    }

    public void setBook_describe(String book_describe) {
        this.book_describe = book_describe;
    }

    public String getBook_date() {
        return book_date;
    }

    public void setBook_date(String book_date) {
        this.book_date = book_date;
    }

    public String getBook_size() {
        return book_size;
    }

    public void setBook_size(String book_size) {
        this.book_size = book_size;
    }

    public String getBook_path() {
        return book_path;
    }

    public void setBook_path(String book_path) {
        this.book_path = book_path;
    }
}
