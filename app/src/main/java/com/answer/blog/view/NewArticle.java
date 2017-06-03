package com.answer.blog.view;

import android.os.Bundle;
import android.support.design.widget.TextInputEditText;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;

import com.answer.blog.R;
import com.answer.blog.util.httpUtil.HttpPostUtil;

public class NewArticle extends AppCompatActivity {
    private TextInputEditText title;
    private TextInputEditText content;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_article);
        initToolbar();
        title = (TextInputEditText)findViewById(R.id.new_title);
        content = (TextInputEditText)findViewById(R.id.new_content);
    }


    public boolean onCreateOptionsMenu(Menu menu){
        getMenuInflater().inflate(R.menu.new_article,menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        switch (id){
            case R.id.publish:{
                HttpPostUtil.newArticle(title.getText().toString(), content.getText().toString());
                this.finish();
                break;
            }

        }
        return super.onOptionsItemSelected(item);
    }

    private void initToolbar(){
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar_new_article);
        setSupportActionBar(toolbar);
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);//显示导航按钮（返回）
        }
    }
}
