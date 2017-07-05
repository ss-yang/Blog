package com.answer.blog.view;

import android.content.pm.ApplicationInfo;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.answer.blog.R;
import com.answer.blog.data.bean.EntityArticle;
import com.answer.blog.data.bean.EntityMessage;
import com.answer.blog.util.MessageAdapter;
import com.answer.blog.util.MessageManager;
import com.answer.blog.util.httpUtil.DataRequester;
import com.answer.blog.util.httpUtil.VolleyCallback;
import com.google.gson.Gson;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.List;
import java.util.Random;

public class UserCenterActivity extends AppCompatActivity {

    private Toolbar toolbar;
    private ImageView background, avatar;

    private MessageManager messageManager;

    private RecyclerView recyclerViewMsg;
    private MessageAdapter messageAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_center);
        initView();
        initMessage();
    }

    private void initView() {
        initToolbar();
        background = (ImageView)findViewById(R.id.usr_center_bg);
        Random random = new Random();
        int resId = getResIdByName("bg" + String.valueOf(random.nextInt(34) + 1));
        background.setBackgroundResource(resId);
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
            toolbar.setTitle(MainActivity.user.getId());
        }
    }

    private void initMessage(){
        messageManager = new MessageManager<EntityMessage.MessageBean>();
        final TextView textView = (TextView)findViewById(R.id.usr_msg_hint);
        textView.setVisibility(View.GONE);
        DataRequester.requestUserMessage(new VolleyCallback() {
            @Override
            public void onSuccess(JSONObject response) {
                try {
                    // 如果留言板为空
                    if (response.getString("message").equals("empty")) {
                        textView.setText(response.getString("data"));
                        textView.setVisibility(View.VISIBLE);
                        return;
                    }
                }catch (JSONException e){
                    e.printStackTrace();
                }
                EntityMessage entityMessage;
                Gson gson = new Gson();
                entityMessage = gson.fromJson(response.toString(), EntityMessage.class);
                messageManager.setCommentsList(entityMessage.getMessageList());// 存储数据
                showComments();
                initData();
            }
        });
    }

    private void initData(){
        TextView tvArticleNum = (TextView)findViewById(R.id.tv_usr_center_article_num);
        TextView tvMsgNum = (TextView)findViewById(R.id.tv_usr_center_msg_num);
        List<EntityArticle.ArticleBean> articleBeanList = MainActivity.articleManager.getMyArticleList();
        List<EntityMessage.MessageBean> messageBeanList = messageManager.getCommentsList();
        tvArticleNum.setText(String.valueOf(articleBeanList.size()));
        tvMsgNum.setText(String.valueOf(messageBeanList.size()));
    }

    /**
     * 显示留言
     */
    private void showComments(){
        recyclerViewMsg = (RecyclerView)findViewById(R.id.recycle_view_user_message);
        List<EntityMessage.MessageBean> list = messageManager.getCommentsList();
        if(list.size() > 0) {
            messageAdapter = new MessageAdapter(list);
            recyclerViewMsg.setLayoutManager(new LinearLayoutManager(this));//线性
            recyclerViewMsg.setAdapter(messageAdapter);
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

    public boolean onCreateOptionsMenu(Menu menu){
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        switch (id){
            case android.R.id.home :{
                finish();
            }
        }
        return super.onOptionsItemSelected(item);
    }

}
