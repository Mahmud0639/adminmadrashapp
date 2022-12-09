package com.manuni.adminmadrashaapp;

public class NoticeData {
    String title, image, time, date, key;

    public NoticeData() {
    }

    public NoticeData(String title, String image, String time, String date, String key) {
        this.title = title;
        this.image = image;
        this.time = time;
        this.date = date;
        this.key = key;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }
}
