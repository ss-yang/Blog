package com.answer.blog.view;

import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.AppCompatTextView;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.Toast;

import com.answer.blog.R;
import com.answer.blog.data.bean.EntityArticle;
import com.answer.blog.data.bean.EntityComment;
import com.answer.blog.util.CommentAdapter;
import com.answer.blog.util.MessageManager;
import com.answer.blog.util.httpUtil.DataRequester;
import com.google.gson.Gson;

import org.json.JSONObject;

import java.util.List;

public class ArticleDetail extends AppCompatActivity {

    EntityArticle.ArticleBean article;
    RecyclerView recyclerViewComment;
    CommentAdapter commentAdapter;
    private MessageManager messageManager;

    Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_article_detail);
        initToolbar();
        showArticle();
    }

    public boolean onCreateOptionsMenu(Menu menu){
        getMenuInflater().inflate(R.menu.article_detail,menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        switch (id){
            case R.id.publish:{
                Toast.makeText(this,"developing....",Toast.LENGTH_SHORT).show();
                break;
            }
            case android.R.id.home :{
                finish();
            }
        }
        return super.onOptionsItemSelected(item);
    }



    /**
     * 初始化Toolbar
     */
    private void initToolbar(){
        toolbar = (Toolbar) findViewById(R.id.toolbar_article_detail);
        setSupportActionBar(toolbar);
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);//显示导航按钮（返回）
            actionBar.setDisplayShowTitleEnabled(false);
        }
    }

    /**
     * 获取Activity传来的文章并显示
     */
    private void showArticle(){
        AppCompatTextView content = (AppCompatTextView)findViewById(R.id.content_detail);
        ImageView img = (ImageView)findViewById(R.id.app_bar_image);//标题背景的图片
        article =  getIntent().getParcelableExtra("article_data");
        content.setText(article.getContent());
        toolbar.setTitle(article.getTitle());
        toolbar.setSubtitle(article.getAuthor()+"  "+article.getTime());
        messageManager = new MessageManager();
        DataRequester.requestArticleCommentList(article.getId(), new DataRequester.VolleyCallback() {
            @Override
            public void onSuccess(JSONObject result) {
                EntityComment entityComment;
                Gson gson = new Gson();
                entityComment = gson.fromJson(result.toString(), EntityComment.class);
                messageManager.setCommentsList(entityComment.getComments());// 存储数据
                showComments();
            }
        });
        Log.d("TAG","---msg: "+messageManager.getCommentsList().size());
//        showComments();
    }

    /**
     * 显示评论
     */
    private void showComments(){
        recyclerViewComment = (RecyclerView)findViewById(R.id.recycle_view_comment);
        List<EntityComment.CommentBean> list = messageManager.getCommentsList();
        if(list.size() > 0) {
            commentAdapter = new CommentAdapter(list);
            recyclerViewComment.setLayoutManager(new LinearLayoutManager(this));//线性
            recyclerViewComment.setAdapter(commentAdapter);
        }
    }

    public MessageManager getMessageManager() {
        return messageManager;
    }

    public void setMessageManager(MessageManager messageManager) {
        this.messageManager = messageManager;
    }
}
