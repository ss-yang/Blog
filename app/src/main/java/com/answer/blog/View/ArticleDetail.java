package com.answer.blog.View;

import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.AppCompatTextView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import com.answer.blog.Data.Article;
import com.answer.blog.R;

public class ArticleDetail extends AppCompatActivity {

    Article article;

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
                Toast.makeText(this,"....",Toast.LENGTH_SHORT).show();

                break;
            }

        }
        return super.onOptionsItemSelected(item);
    }

    /**
     * 初始化Toolbar
     */
    private void initToolbar(){
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar_article_detail);
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
        article = (Article) getIntent().getParcelableExtra("article_data");
        AppCompatTextView title = (AppCompatTextView)findViewById(R.id.title_detail);
        AppCompatTextView content = (AppCompatTextView)findViewById(R.id.content_detail);
        title.setText(article.getTitle());
        content.setText(article.getContent());

    }
}
