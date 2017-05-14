package com.answer.blog.Data;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by Answer on 2017/5/13.
 */

public class Article implements Parcelable{
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

    public Article(){}

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

    //以下是Parcelable接口的方法
    @Override
    public int describeContents(){
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flag){
        dest.writeInt(id);
        dest.writeString(title);
        dest.writeString(author);
        dest.writeString(content);
        dest.writeString(time);
        dest.writeString(last_time);
    }

    public static final Parcelable.Creator<Article> CREATOR = new Creator<Article>(){
        @Override
        public Article createFromParcel(Parcel source){
            Article article = new Article();
            article.setId(source.readInt());
            article.setTitle(source.readString());
            article.setAuthor(source.readString());
            article.setContent(source.readString());
            article.setTime(source.readString());
            article.setLast_time(source.readString());
            return article;
        }

        @Override
        public Article[] newArray(int size){
            return new Article[size];
        }
    };

}
