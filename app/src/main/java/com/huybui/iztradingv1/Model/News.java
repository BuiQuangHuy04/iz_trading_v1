package com.huybui.iztradingv1.Model;

import java.io.Serializable;

public class News implements Serializable {
    protected String thumbnail;
    protected String title;
    protected String date;
    protected String tag;
    protected String text;

    public News(String thumbnail, String title, String date, String tag, String text) {
        this.thumbnail = thumbnail;
        this.title = title;
        this.date = date;
        this.tag = tag;
        this.text = text;
    }

    public News() {
    }

    public String getThumbnail() {
        return thumbnail;
    }

    public String getTitle() {
        return title;
    }

    public String getDate() {
        return date;
    }

    public String getTag() {
        return tag;
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
                ", tag='" + tag + '\'' +
                ", text='" + text + '\'' +
                '}';
    }
}
