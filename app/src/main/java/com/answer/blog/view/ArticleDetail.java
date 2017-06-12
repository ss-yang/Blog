package com.answer.blog.view;

import android.content.DialogInterface;
import android.content.pm.ApplicationInfo;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.AppCompatTextView;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.answer.blog.R;
import com.answer.blog.data.bean.EntityArticle;
import com.answer.blog.data.bean.EntityComment;
import com.answer.blog.util.CommentAdapter;
import com.answer.blog.util.MessageManager;
import com.answer.blog.util.httpUtil.DataRequester;
import com.answer.blog.util.httpUtil.HttpGetUtil;
import com.answer.blog.util.httpUtil.HttpPostUtil;
import com.answer.blog.util.httpUtil.VolleyCallback;
import com.google.gson.Gson;

import org.json.JSONObject;

import java.util.List;
import java.util.Random;

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
        initFloatButton();
        showArticle();
    }

    public boolean onCreateOptionsMenu(Menu menu){
        if(MainActivity.user.isLogin())
            getMenuInflater().inflate(R.menu.article_detail,menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        switch (id){
            case R.id.article_detail_edit:{
                Toast.makeText(this,"功能正在开发...",Toast.LENGTH_SHORT).show();
                break;
            }
            case R.id.article_detail_delete:{
                HttpGetUtil.requestDeleteArticle("article", article.getId(), new VolleyCallback() {
                    @Override
                    public void onSuccess(JSONObject result) {
                        //
                        finish();
                    }
                });
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
     * 初始化FloatingActionButton
     */
    private void initFloatButton(){
        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab_article_detail);
        fab.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                // 弹出悬浮输入框（不跳转），输入评论内容后，点击提交按钮，发送Post请求。
                inputDialog();
            }
        });
    }

    /**·
     * 创建inputDialog、显示，并发送请求（提交评论）
     */
    private void inputDialog() {
        final AlertDialog.Builder builder = new AlertDialog.Builder(this);

        LayoutInflater layoutInflater = LayoutInflater.from(this);
        final View inflator = layoutInflater.inflate(R.layout.message_dialog, null);
        final EditText editText = (EditText)inflator.findViewById(R.id.editText_msg_dlg);
        editText.setFocusable(true);

        final AlertDialog alertDialog = new AlertDialog.Builder(this)
                .setTitle(getString(R.string.titlt_comment))
                .setView(inflator)
                .setPositiveButton("发送", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        String content = editText.getText().toString(); // 获取到评论内容
                        HttpPostUtil.newMessage(article.getId(), content, new VolleyCallback() {
                            @Override
                            public void onSuccess(JSONObject response) {
                                // 插入评论成功，重新请求服务端的评论数据并显示
                                DataRequester.requestArticleCommentList(article.getId(), new VolleyCallback() {
                                    @Override
                                    public void onSuccess(JSONObject result) {
                                        EntityComment entityComment;
                                        Gson gson = new Gson();
                                        entityComment = gson.fromJson(result.toString(), EntityComment.class);
                                        messageManager.setCommentsList(entityComment.getComments());// 存储数据
                                        showComments();
                                    }
                                });
                            }
                        });
                    }
                })
                .create();
        Window window = alertDialog.getWindow();
        window.setGravity(Gravity.BOTTOM);
        alertDialog.show();
    }

    /**
     * 获取Activity传来的文章并显示
     */
    private void showArticle(){
        AppCompatTextView content = (AppCompatTextView)findViewById(R.id.content_detail);
        ImageView img = (ImageView)findViewById(R.id.article_detail_image);//标题背景的图片
        Random random = new Random();
        int resId = getResIdByName("bg" + String.valueOf(random.nextInt(26) + 1));
        img.setBackgroundResource(resId);
        article =  getIntent().getParcelableExtra("article_data");
        content.setText(article.getContent());
        toolbar.setTitle(article.getTitle());
        toolbar.setSubtitle(article.getAuthor()+" - "+article.getTime());
        messageManager = new MessageManager<EntityComment.CommentBean>();
        DataRequester.requestArticleCommentList(article.getId(), new VolleyCallback() {
            @Override
            public void onSuccess(JSONObject result) {
                EntityComment entityComment;
                Gson gson = new Gson();
                entityComment = gson.fromJson(result.toString(), EntityComment.class);
                messageManager.setCommentsList(entityComment.getComments());// 存储数据
                showComments();
            }
        });
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

    /**
     * 通过文件名获取resource id
     * @param name 资源文件名
     * @return
     */
    public int getResIdByName(String name)
    {
        ApplicationInfo appInfo = getApplicationInfo();
        return getResources().getIdentifier(name, "drawable", appInfo.packageName);
    }

    public MessageManager getMessageManager() {
        return messageManager;
    }

    public void setMessageManager(MessageManager messageManager) {
        this.messageManager = messageManager;
    }
}
