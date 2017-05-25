package com.answer.blog.data.bean;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by Answer on 2017/5/24.
 */

public class EntityArticle {


    /**
     * code : 200
     * message : success
     * data : [{"id":"37","title":"ceshi","content":"lsdkjflskleklgeweeee","author":"admin","time":"2017-05-22 13:57:58","last_time":null},{"id":"24","title":"jbkbkjhkgkgjk","content":"建行股份分红方法减肥","author":"yuyu","time":"2016-12-18 23:44:43","last_time":"2016-12-18 23:45:00"},{"id":"23","title":"黑客联盟","content":"很厉害解决？？？","author":"1400170190","time":"2016-12-18 23:11:56","last_time":"2016-12-18 23:12:07"}]
     */

    @SerializedName("data")
    private List<ArticleBean> articles;

    public List<ArticleBean> getArticles() {
        return articles;
    }

    public void setArticles(List<ArticleBean> article) {
        this.articles = article;
    }

    public static class ArticleBean implements Parcelable {
        /**
         * id : 37
         * title : ceshi
         * content : lsdkjflskleklgeweeee
         * author : admin
         * time : 2017-05-22 13:57:58
         * last_time : null
         */

        private String id;
        private String title;
        private String content;
        private String author;
        private String time;
        @SerializedName("last_time")
        private String lastTime;


        //以下是Parcelable接口的方法
        @Override
        public int describeContents(){
            return 0;
        }

        @Override
        public void writeToParcel(Parcel dest, int flag){
            dest.writeString(id);
            dest.writeString(title);
            dest.writeString(author);
            dest.writeString(content);
            dest.writeString(time);
            dest.writeString(lastTime);
        }

        public static final Parcelable.Creator<EntityArticle.ArticleBean> CREATOR = new Creator<EntityArticle.ArticleBean>(){
            @Override
            public EntityArticle.ArticleBean createFromParcel(Parcel source){
                EntityArticle.ArticleBean article = new EntityArticle.ArticleBean();
                article.setId(source.readString());
                article.setTitle(source.readString());
                article.setAuthor(source.readString());
                article.setContent(source.readString());
                article.setTime(source.readString());
                article.setLastTime(source.readString());
                return article;
            }

            @Override
            public EntityArticle.ArticleBean[] newArray(int size){
                return new EntityArticle.ArticleBean[size];
            }
        };



        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public String getContent() {
            return content;
        }

        public void setContent(String content) {
            this.content = content;
        }

        public String getAuthor() {
            return author;
        }

        public void setAuthor(String author) {
            this.author = author;
        }

        public String getTime() {
            return time;
        }

        public void setTime(String time) {
            this.time = time;
        }

        public Object getLastTime() {
            return lastTime;
        }

        public void setLastTime(String lastTime) {
            this.lastTime = lastTime;
        }
    }
}
