package com.example.android_tkpm.models;

public class Notify {
    private String title, body, createdAt;
    private int type;

    public Notify() {}

    public Notify(String title, String body, String createdAt, int type) {
        this.title = title;
        this.body = body;
        this.createdAt = createdAt;
        this.type = type;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getBody() {
        return body;
    }

    public void setBody(String body) {
        this.body = body;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public String getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(String createdAt) {
        this.createdAt = createdAt;
    }
}
