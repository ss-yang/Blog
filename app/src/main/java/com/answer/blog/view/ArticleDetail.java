package com.answer.blog.view;

import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.AppCompatTextView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.Toast;

import com.answer.blog.R;
import com.answer.blog.data.bean.EntityArticle;

public class ArticleDetail extends AppCompatActivity {

    EntityArticle.ArticleBean article;

    Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_article_detail);
        initToolbar();
        showArticle();
        Log.d("TAG","onCreate");
    }

    public boolean onCreateOptionsMenu(Menu menu){
        getMenuInflater().inflate(R.menu.article_detail,menu);
        return true;
    }

//    @Override
//    public void onBackPressed() {
//        super.onBackPressed();
//        Log.d("TAG","onBackPressed");
//        finish();
//    }

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
    }
}
