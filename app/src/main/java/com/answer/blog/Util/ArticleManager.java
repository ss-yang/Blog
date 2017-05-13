package com.answer.blog.Util;

import com.answer.blog.Data.Article;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Answer on 2017/5/13.
 * 文章管理类，提供增删改功能
 */

public class ArticleManager {
    private List<Article> articleList;

    public ArticleManager() {
        articleList = new ArrayList<>();
        articleList.clear();
        initTest();
    }

    public void add(Article article){
        articleList.add(article);
    }

    public void delete(int id){
        articleList.remove(id);
    }

    public List<Article> getArticleList() {
        return articleList;
    }

    /**
     * 测试数据
     */
    private void initTest(){
        int size = 10;
        for(int i=0;i<size;i++){
            Article article = new Article(i,"标题"+i,"作者"+i,"内容"+i,"时间"+i,"修改时间"+i);
            articleList.add(article);
        }
    }
}
