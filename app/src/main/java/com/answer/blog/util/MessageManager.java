package com.answer.blog.util;

import com.answer.blog.data.bean.EntityComment;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Answer on 2017/6/7.
 */

public class MessageManager {
    private List<EntityComment.CommentBean> commentsList;

    public MessageManager() {
        commentsList = new ArrayList<>();
        commentsList.clear();
    }

    public List<EntityComment.CommentBean> getCommentsList() {
        return commentsList;
    }

    public void setCommentsList(List<EntityComment.CommentBean> commentsList) {
        this.commentsList = commentsList;
    }
}
