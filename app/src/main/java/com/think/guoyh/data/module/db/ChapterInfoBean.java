package com.think.guoyh.data.module.db;

/**
 * Created by newbiechen on 17-5-10.
 */

public class ChapterInfoBean {

    private String title;
    private String content;
    private int words;
    public ChapterInfoBean(){

    }
    public ChapterInfoBean(String title, String content, int words) {
        this.title = title;
        this.content = content;
        this.words = words;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getBody() {
        return content;
    }

    public void setBody(String body) {
        this.content = body;
    }

    public int getWords() {
        return words;
    }

    public void setWords(int words) {
        this.words = words;
    }
}
