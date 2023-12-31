package com.example.workapp.data.model;


import java.io.Serializable;

public class NoticeData implements Serializable {
    private String title = "";
    private String content = "";
    private String date = "";

    public NoticeData() { }

    public String getTitle() {
        return title;
    }

    public String getContent() {
        return content;
    }

    public String getDate() {
        return date;
    }

    public void setTitle(String title) {
        this.title = title;
    }
    public void setContent(String content) {
        this.content = content;
    }
    public void setDate(String date) {
        this.date = date;
    }


    public void setKey(String curKey) {
    }
}