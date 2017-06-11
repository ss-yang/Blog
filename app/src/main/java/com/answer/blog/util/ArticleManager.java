package com.answer.blog.util;

import com.answer.blog.data.bean.EntityArticle;
import com.answer.blog.util.httpUtil.HttpGetUtil;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Answer on 2017/5/13.
 * 文章管理类，提供增删改功能
 */

public class ArticleManager {
    private List<EntityArticle.ArticleBean> articleList;
    private List<EntityArticle.ArticleBean> myArticleList;
    public static final int ALL_ARTICLE = 0;
    public static final int MY_ARTICLE = 1;

    public ArticleManager() {
        articleList = new ArrayList<>();
        articleList.clear();
        myArticleList = new ArrayList<>();
        myArticleList.clear();
    }

    public void add(EntityArticle.ArticleBean article){
        articleList.add(article);
    }

    public void delete(String id){
        // 直接请求服务端删除再拉取服务端数据，还是请求服务端删除的同时删除list里面的 。。？

    }

    public List<EntityArticle.ArticleBean> getArticleList() {
        return articleList;
    }

    public void setArticleList(List<EntityArticle.ArticleBean> articleList) {
        this.articleList = articleList;
    }

    public List<EntityArticle.ArticleBean> getMyArticleList() {
        return myArticleList;
    }

    public void setMyArticleList(List<EntityArticle.ArticleBean> myArticleList) {
        this.myArticleList = myArticleList;
    }

    /**
     * 测试数据
     */
    private void initTest(){
        int size = 10;
        for(int i=0;i<size;i++){
//            if(i%2 == 0)
//                myArticle.add(article);
//            articleList.add(article);
        }
    }



}
