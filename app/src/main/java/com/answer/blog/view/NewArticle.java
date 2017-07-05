package com.answer.blog.view;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.PixelFormat;
import android.graphics.Rect;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.Settings;
import android.support.design.widget.TextInputEditText;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutCompat;
import android.support.v7.widget.Toolbar;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowManager;
import android.webkit.WebChromeClient;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Toast;

import com.answer.blog.R;
import com.answer.blog.data.bean.EntityArticle;
import com.answer.blog.util.httpUtil.HttpPostUtil;

public class NewArticle extends AppCompatActivity {
    private TextInputEditText title;
    private TextInputEditText content;

    private WebView mywebview;
    private WindowManager wm;
    private WindowManager.LayoutParams wmlay;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_article);
        initToolbar();
        title = (TextInputEditText)findViewById(R.id.new_title);
        content = (TextInputEditText)findViewById(R.id.new_content);

        // MarkDown的tip 悬浮窗需要权限。 SDK23以上需要弹出设置界面让用户自己选择，23以前的只需要在manifest里面加权限即可。
        if (Build.VERSION.SDK_INT >= 23) {
            if (! Settings.canDrawOverlays(NewArticle.this)) {
                Intent intent = new Intent(Settings.ACTION_MANAGE_OVERLAY_PERMISSION,
                        Uri.parse("package:" + getPackageName()));
                startActivityForResult(intent,10);
            }
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == 10) {
            if (Build.VERSION.SDK_INT >= 23) {
                if (!Settings.canDrawOverlays(this)) {
                    // SYSTEM_ALERT_WINDOW permission not granted...
                    Toast.makeText(NewArticle.this,"not granted",Toast.LENGTH_SHORT);
                }
            }
        }
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
            case R.id.preview:{
                Intent intent = new Intent(NewArticle.this, ArticleDetail.class);
                EntityArticle.ArticleBean article = new EntityArticle.ArticleBean();
                article.setTitle(title.getText().toString());
                article.setTime("");
                article.setContent(content.getText().toString());
                article.setAuthor("");
                article.setId("preview"); //预览时ArticleDetail根据这个值判断来源。
                article.setLastTime("");
                intent.putExtra("article_data",article);
                startActivity(intent);
                break;
            }
            case R.id.md_tips:{
                // 弹出MD简单教程
                showMDTips();

                break;
            }
            case android.R.id.home :{
                finish();
            }
        }
        return super.onOptionsItemSelected(item);
    }

    private void showMDTips(){
        mywebview = new WebView(this);
        wm=(WindowManager)getApplicationContext().getSystemService(WINDOW_SERVICE);
        wmlay = new WindowManager.LayoutParams();
        mywebview.setBackgroundColor(Color.TRANSPARENT);
        mywebview.getSettings().setJavaScriptEnabled(true);
        mywebview.setWebChromeClient(new WebChromeClient());
        mywebview.setWebViewClient(new WebViewClient(){
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                // TODO Auto-generated method stub
                view.loadUrl(url);
                return true;
            }
        });

        mywebview.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                switch (keyCode) {
                    case KeyEvent.KEYCODE_BACK:
                        wm.removeView(mywebview);
                        return true;
                    default:
                        return false;
                }
            }
        });
        mywebview.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                int x = (int) event.getX();
                int y = (int) event.getY();
                Rect rect = new Rect();
                mywebview.getGlobalVisibleRect(rect);
                if (!rect.contains(x, y)) {
                    wm.removeView(mywebview);
                }
                return false;
            }
        });
        mywebview.requestFocus();
        mywebview.loadUrl("https://github.com/Melo618/Simple-Markdown-Guide/blob/master/README.md");             //要载入的布局网页

        wmlay.type= WindowManager.LayoutParams.TYPE_PHONE;                      //当前悬浮窗口位于phone层
        wmlay.format= PixelFormat.RGBA_8888;                      //悬浮窗口背景设为透明
        wmlay.gravity= Gravity.CENTER;
        wmlay.flags = WindowManager.LayoutParams.FLAG_NOT_TOUCH_MODAL;//属性设置
        wmlay.x = 20;
        wmlay.y = 30;
        wmlay.height = LinearLayoutCompat.LayoutParams.MATCH_PARENT;
        wmlay.width = LinearLayoutCompat.LayoutParams.MATCH_PARENT;
        wm.addView(mywebview, wmlay);
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
