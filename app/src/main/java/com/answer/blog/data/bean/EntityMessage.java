package com.answer.blog.data.bean;

import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by Answer on 2017/6/11.
 */

public class EntityMessage {

    /**
     * code : 200
     * message : success
     * data : [{"id":"24","author":"yang","content":"welll","time":"2017-06-11 20:16:35"},{"id":"21","author":"yang","content":"hi","time":"2016-12-19 15:49:16"}]
     */

    @SerializedName("data")
    private List<MessageBean> messageList;

    public List<MessageBean> getMessageList() {
        return messageList;
    }

    public void setMessageList(List<MessageBean> messageList) {
        this.messageList = messageList;
    }

    public static class MessageBean {
        /**
         * id : 24
         * author : yang
         * content : welll
         * time : 2017-06-11 20:16:35
         */

        private String id;
        private String author;
        private String content;
        private String time;

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
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
    }
}
