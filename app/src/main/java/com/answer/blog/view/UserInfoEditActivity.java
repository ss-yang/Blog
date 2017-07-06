package com.answer.blog.view;

import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.answer.blog.R;

public class UserInfoEditActivity extends AppCompatActivity implements View.OnClickListener{
    private Button btnChangeAvatar;
    private Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_info_edit);
        initView();
        initToolbar();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.btn_change_avatar:{
                Toast.makeText(getBaseContext(),"change avatar here",Toast.LENGTH_SHORT);
                break;
            }
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case android.R.id.home :{
                finish();
            }
        }
        return super.onOptionsItemSelected(item);
    }

    private void initView(){
        btnChangeAvatar = (Button)findViewById(R.id.btn_change_avatar);
        btnChangeAvatar.setOnClickListener(this);
    }

    /**
     * 初始化Toolbar
     */
    private void initToolbar(){
        toolbar = (Toolbar) findViewById(R.id.toolbar_user_info);
        setSupportActionBar(toolbar);
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);//显示导航按钮（返回）
            actionBar.setDisplayShowTitleEnabled(false);
            toolbar.setTitle(MainActivity.user.getId());
        }
    }
}
