package com.huybui.iztradingv1.Model;

import java.io.Serializable;

public class News implements Serializable {
    public String thumbnail;
    public String title;
    public String date;
    public String user_account;
    public String text;

    public News(String thumbnail, String title, String date, String user_account, String text) {
        this.thumbnail = thumbnail;
        this.title = title;
        this.date = date;
        this.user_account = user_account;
        this.text = text;
    }

    public News() {
    }

    public String getThumbnail() {
        return thumbnail;
    }

    public void setThumbnail(String thumbnail) {
        this.thumbnail = thumbnail;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getUser_account() {
        return user_account;
    }

    public void setUser_account(String user_account) {
        this.user_account = user_account;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    @Override
    public String toString() {
        return "News{" +
                "thumbnail='" + thumbnail + '\'' +
                ", title='" + title + '\'' +
                ", date='" + date + '\'' +
                ", user_account='" + user_account + '\'' +
                ", text='" + text + '\'' +
                '}';
    }
}
