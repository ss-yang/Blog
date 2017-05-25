package com.answer.blog.util;

import com.answer.blog.data.Article;
import com.answer.blog.data.bean.EntityArticle;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Answer on 2017/5/13.
 * 文章管理类，提供增删改功能
 */

public class ArticleManager {
    private List<EntityArticle.ArticleBean> articleList;
    private List<EntityArticle.ArticleBean> myArticle;
    public static final int ALL_ARTICLE = 0;
    public static final int MY_ARTICLE = 1;

    public ArticleManager() {
        articleList = new ArrayList<>();
        articleList.clear();
        myArticle = new ArrayList<>();
        myArticle.clear();
//        initTest();
    }

    public void add(EntityArticle.ArticleBean article){
        articleList.add(article);
    }

    public void delete(int id){
        articleList.remove(id);
    }

    public List<EntityArticle.ArticleBean> getArticleList() {
        return articleList;
    }

    public void setArticleList(List<EntityArticle.ArticleBean> articleList) {
        this.articleList = articleList;
    }

    public List<EntityArticle.ArticleBean> getMyArticle() {
        return myArticle;
    }

    /**
     * 测试数据
     */
    private void initTest(){
        int size = 10;
        for(int i=0;i<size;i++){
            Article article = new Article(i,"标题"+i,"作者"+i,"内容"+i,"时间"+i,"修改时间"+i);
//            if(i%2 == 0)
//                myArticle.add(article);
//            articleList.add(article);
        }
    }



}
