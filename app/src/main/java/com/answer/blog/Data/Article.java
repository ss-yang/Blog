package com.answer.blog.Data;

/**
 * Created by Answer on 2017/5/13.
 */

public class Article {
    private int id;
    private String title;
    private String author;
    private String content;
    private String time;
    private String last_time;

    public Article(int id, String title, String author, String content, String time, String last_time) {
        this.id = id;
        this.title = title;
        this.author = author;
        this.content = content;
        this.time = time;
        this.last_time = last_time;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getLast_time() {
        return last_time;
    }

    public void setLast_time(String last_time) {
        this.last_time = last_time;
    }
}
