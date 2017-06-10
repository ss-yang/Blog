package com.answer.blog.data.bean;

import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by Answer on 2017/6/7.
 */

public class EntityComment {

    @SerializedName("data")
    private List<CommentBean> comments;

    public List<CommentBean> getComments() {
        return comments;
    }

    public void setComments(List<CommentBean> comments) {
        this.comments = comments;
    }

    public static class CommentBean {
        /**
         * id : 11
         * content : wanghere
         * author : wang
         * time : 2016-12-12 23:34:50
         */

        private String id;
        private String content;
        private String author;
        private String time;

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
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
    }
}
