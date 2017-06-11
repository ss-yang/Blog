package com.answer.blog.util;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Answer on 2017/6/7.
 */

public class MessageManager<T> {
    private List<T> list;

    public MessageManager() {
        list = new ArrayList<>();
        list.clear();
    }

    public List<T> getCommentsList() {
        return list;
    }

    public void setCommentsList(List<T> t_list) {
        this.list = t_list;
    }
}
