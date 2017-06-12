package com.answer.blog.view;

import android.content.Intent;
import android.content.pm.ApplicationInfo;
import android.os.Bundle;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.GravityCompat;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.CardView;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.Toolbar;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.TextPaint;
import android.text.method.LinkMovementMethod;
import android.text.style.ClickableSpan;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.toolbox.Volley;
import com.answer.blog.R;
import com.answer.blog.data.User;
import com.answer.blog.data.bean.EntityArticle;
import com.answer.blog.util.ArticleAdapter;
import com.answer.blog.util.ArticleManager;
import com.answer.blog.util.RecyclerItemClickListener;
import com.answer.blog.util.httpUtil.DataRequester;
import com.answer.blog.util.httpUtil.HttpGetUtil;
import com.answer.blog.util.httpUtil.VolleyCallback;
import com.google.gson.Gson;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Random;


public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    public static ArticleManager articleManager;
    public static User user;
    public static RequestQueue mQueue;

    // home tab view
    private TabLayout mTablayout;
    private ViewPager mViewpager;
    private HomeFragment homeFragment;
    private MyArticleFragment myArticleFragment;
    private String mTitles[] = {"首页","我的文章"};

    // nav header view
    private NavigationView navigationView;
    private View headerView;
    private TextView tv_login_quit;
    private TextView tv_nickName;

    // search result view
    RecyclerView srRecyclerView;
    CoordinatorLayout srLayout;
    LinearLayout srLinearLayout;
    CardView srCardView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mQueue = Volley.newRequestQueue(getBaseContext());
        articleManager = new ArticleManager();
        user = new User();
        user.setDefult();
        initLayout();
        initUserView();

        DataRequester.requestMyArticleList();
        DataRequester.requestArticleList(new VolleyCallback() {
            @Override
            public void onSuccess(JSONObject response) {
                EntityArticle entityArticle;
                Gson gson = new Gson();
                entityArticle = gson.fromJson(response.toString(), EntityArticle.class);
                MainActivity.articleManager.setArticleList(entityArticle.getArticles());
                initTabView();
            }
        });

    }


    @Override
    protected void onResume() {
        super.onResume();
        initUserView();
        initTabView();
    }

    @Override
    protected void onActivityResult( int requestCode, int resultCode, Intent intent ){
        if(resultCode == RESULT_OK){
            user.setId(intent.getStringExtra("userId"));
            user.setLogin(true);
        }
    }

    @Override
    public void onBackPressed() {
        if(srLinearLayout != null){
            removeSearchResultView();
        }else {
            DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
            if (drawer.isDrawerOpen(GravityCompat.START)) {
                drawer.closeDrawer(GravityCompat.START);
            }
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        //Toolbar的搜索框
        MenuItem searchItem = menu.findItem(R.id.home_search);
        SearchView searchView = null;
        if (searchItem != null) {
            searchView = (SearchView) searchItem.getActionView();
        }
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                //处理搜索结果，网络请求
                HttpGetUtil.requestSearch(query, new VolleyCallback() {
                    @Override
                    public void onSuccess(JSONObject response) {
                        try {
                            if(response.getString("message").equals("empty")){
                                initSearchResult(true, 0);
                            }else {
                                EntityArticle entityArticle;
                                Gson gson = new Gson();
                                entityArticle = gson.fromJson(response.toString(), EntityArticle.class);
                                ArticleManager temp = new ArticleManager();// 新建临时的ArticleManager
                                temp.setArticleList(entityArticle.getArticles());
                                initSearchResult(false, temp.getArticleList().size());
                                initArticleList(srRecyclerView,temp);
                            }
                        }catch (JSONException e){
                            e.printStackTrace();
                        }
                    }
                });
                return false;
            }
            @Override
            public boolean onQueryTextChange(String s) {
                if(srLinearLayout != null) {
                    removeSearchResultView();
                    return true;
                }
                return false;
            }
        });
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        switch (id){
            case R.id.action_settings:{
                startActivity(new Intent(MainActivity.this, SettingsActivity.class));
                return true;
            }
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_home) {
            // Handle the camera action
        } else if (id == R.id.nav_category) {

        } else if (id == R.id.nav_message) {

        } else if (id == R.id.nav_settings){
            startActivity(new Intent(MainActivity.this, SettingsActivity.class));
        } else if (id == R.id.nav_share) {

        } else if (id == R.id.nav_about) {

        }
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    public void initLayout(){
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
//        CollapsingToolbarLayout toolbarLayout = (CollapsingToolbarLayout)findViewById(R.id.collapsing_tool_bar_main);
        setSupportActionBar(toolbar);
//        toolbarLayout.setTitle(getTitle());

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this,NewArticle.class));
            }
        });


        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
    }

    /**
     * 初始化搜索结果的界面。
     * 界面包括4层,除CoordinatorLayout 外，都是动态创建：
     * CoordinatorLayout srLayout
     * --------LinearLayout srLinearLayout
     * -----------------RecyclerView srRecyclerView
     * --------------------------CardView srCardView
     *
     * @param empty
     * @param size
     */
    private void initSearchResult(boolean empty, int size){
        if(empty){
            Toast.makeText(this, getString(R.string.search_result_empty), Toast.LENGTH_SHORT).show();
            return;
        }

        // 动态创建用于显示搜索结果的RecyclerView，并添加到布局中
        srRecyclerView = new RecyclerView(this);
        srLayout = (CoordinatorLayout)findViewById(R.id.main_layout);

        // 为了能调整RecyclerView的位置，加入linearLayout作为容器
        srLinearLayout = new LinearLayout(this);
        srLinearLayout.setOrientation(LinearLayout.VERTICAL);
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT,ViewGroup.LayoutParams.WRAP_CONTENT);
        params.setMargins(16,288,16,16);
        srLinearLayout.setLayoutParams(params);

        srCardView = new CardView(this);
        srCardView.setRadius(20);
        srCardView.setCardBackgroundColor(getResources().getColor(R.color.colorPrimary));
        srCardView.setPadding(12,12,12,12);
        srCardView.setRadius(28);

        Toast.makeText(this, "搜索到" + size + "条相关结果", Toast.LENGTH_SHORT).show();
        // 显示结果：将控件加载到根布局srLayout中
        srCardView.addView(srRecyclerView);
        srLinearLayout.addView(srCardView);
        srLayout.addView(srLinearLayout);
    }

    /**
     * 删除动态创建的搜索结果界面
     */
    private void removeSearchResultView(){
        srLayout.removeView(srRecyclerView);
        srLayout.removeView(srCardView);
        srLayout.removeView(srLinearLayout);
    }

    /**
     * 动态创建搜索结果控件，并加载搜索结果
     * @param articleManager
     */
    private void initArticleList(RecyclerView srRecyclerView, final ArticleManager articleManager){

        ArticleAdapter articleAdapter;// 文章适配器
        articleAdapter = new ArticleAdapter(articleManager.getArticleList());

        srRecyclerView.setLayoutManager(new LinearLayoutManager(this));//线性
        srRecyclerView.setAdapter(articleAdapter);
        srRecyclerView.addOnItemTouchListener(new RecyclerItemClickListener(this, srRecyclerView,
                new RecyclerItemClickListener.OnItemClickListener() {
                    @Override
                    public void onItemClick(View view, int position) {
                        Intent intent = new Intent(MainActivity.this,ArticleDetail.class);
                        EntityArticle.ArticleBean article = articleManager.getArticleList().get(position);
                        intent.putExtra("article_data",article);
                        Log.d("TAG","short click start article detail");
                        startActivity(intent);
                    }

                    @Override
                    public void onItemLongClick(View view, int position) {
                        Log.d("TAG","long click start article detail");
                    }
                }));
    }

    private void initTabView() {
        mTablayout = (TabLayout) findViewById(R.id.tablayout);
        mViewpager = (ViewPager) findViewById(R.id.viewpager);

        mViewpager.setAdapter(new FragmentPagerAdapter(getSupportFragmentManager()) {
            @Override
            public int getCount() {
                return mTitles.length;
            }

            @Override
            public Fragment getItem(int position) {
                if (position == 0){
                    homeFragment = new HomeFragment();
                    return homeFragment;
                }
                else if (position == 1) {
                    myArticleFragment = new MyArticleFragment();
                    return myArticleFragment;
                }
                return new HomeFragment();
            }

            @Override
            public CharSequence getPageTitle(int position) {
                return mTitles[position];
            }
        });

        mTablayout.setupWithViewPager(mViewpager);
        mTablayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                if(MainActivity.user.isLogin()) {
                    myArticleFragment.initArticleList(myArticleFragment.getView());
                }else {

                }
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {}

            @Override
            public void onTabReselected(TabLayout.Tab tab) {}
        });
    }

    private void initUserView(){
        navigationView = (NavigationView) findViewById(R.id.nav_view);
        headerView = navigationView.getHeaderView(0); // 获取headerview对象
        Random random = new Random();
        int resId = getResIdByName("bg" + String.valueOf(random.nextInt(34) + 1)); // 获取随机背景图片id，文件名格式：bgx.png
        headerView.setBackgroundResource(resId);// 设置headerview的背景
        tv_login_quit = (TextView)headerView.findViewById(R.id.tv_login_quit);
        tv_nickName = (TextView)headerView.findViewById(R.id.tv_nickname);
        setUserSpannable(tv_login_quit,tv_nickName);
        setUserAvatarClickable();
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

    /**
     * 设置nav_header的头像为可点击
     */
    private void setUserAvatarClickable(){
        ImageView avatar =  (ImageView)headerView.findViewById(R.id.iv_avatar);
        avatar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(MainActivity.user.isLogin()) {
                    startActivity(new Intent(MainActivity.this, UserCenterActivity.class));
                }else {
                    startActivity(new Intent(MainActivity.this,LoginActivity.class));
                }
            }
        });
    }

    /**
     * 设置nav_header的“登录”和“退出登录”功能
     * @param tv_login_quit
     * @param tv_nickName
     */
    private void setUserSpannable(TextView tv_login_quit,TextView tv_nickName){
        String login_quit = "登录";
        if(MainActivity.user.isLogin()){
            login_quit = "退出登录";
        }
        SpannableString spannableString = new SpannableString(login_quit);
        if(!MainActivity.user.isLogin()){
            spannableString.setSpan(new ClickableSpan() {
                @Override
                public void onClick(View view) {
                    Intent intent=new Intent(MainActivity.this,LoginActivity.class);
                    startActivity(intent);
                }

                @Override
                public void updateDrawState(TextPaint ds) {
                    super.updateDrawState(ds);
                    ds.setUnderlineText(false);
                }
            }, 0, login_quit.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        }else {
            spannableString.setSpan(new ClickableSpan() {
                @Override
                public void onClick(View view) {
                    //退出登录的逻辑
                    logout();
                }
                @Override
                public void updateDrawState(TextPaint ds) {
                    super.updateDrawState(ds);
                    ds.setUnderlineText(false);
                }
            }, 0, login_quit.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        }
        tv_login_quit.setText(spannableString);
        tv_login_quit.setMovementMethod(LinkMovementMethod.getInstance());
        tv_nickName.setText(MainActivity.user.getId());
    }

    /**
     * 退出登录
     */
    private void logout(){
        MainActivity.user.setDefult();
        Log.d("TAG","login -> "+MainActivity.user.isLogin());
        setUserSpannable(tv_login_quit,tv_nickName);
    }



}
