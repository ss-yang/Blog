package com.answer.blog.View;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.CollapsingToolbarLayout;
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
import android.support.v7.widget.Toolbar;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.TextPaint;
import android.text.method.LinkMovementMethod;
import android.text.style.ClickableSpan;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.toolbox.Volley;
import com.answer.blog.Data.User;
import com.answer.blog.R;
import com.answer.blog.Util.ArticleManager;
import com.answer.blog.Util.Implement.DataTransfer;
import com.answer.blog.Util.HttpUtil.DataRequester;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener,DataTransfer {

    public static ArticleManager articleManager;
    public static User user;

    public static RequestQueue mQueue;

    private TabLayout mTablayout;
    private ViewPager mViewpager;
    private String mTitles[] = {"首页","我的文章"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mQueue = Volley.newRequestQueue(getBaseContext());
        initLayout();
        articleManager = new ArticleManager();
        initTabView();
        user = new User();
        showUser(user);
        DataRequester requester=new DataRequester(this);
        requester.requestArticleList();
    }

    @Override
    public void setView(String str){
        // 测试：在activity获取onResponse的数据。（实现了一个自定义接口）
        Toast.makeText(MainActivity.this,str,Toast.LENGTH_LONG).show();
    }


    @Override
    protected void onResume() {
        super.onResume();
        showUser(user);

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
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
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

        } else if (id == R.id.nav_share) {

        } else if (id == R.id.nav_about) {

        }
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    public void initLayout(){
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        CollapsingToolbarLayout toolbarLayout = (CollapsingToolbarLayout)findViewById(R.id.collapsing_tool_bar_main);
        setSupportActionBar(toolbar);
        toolbarLayout.setTitle(getTitle());

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                switch (v.getId()){
                    case R.id.fab:{
                        startActivity(new Intent(MainActivity.this,NewArticle.class));
                        break;
                    }
                    default:
                        break;
                }
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
                    return new HomeFragment();
                }
                else if (position == 1)
                    return new MyArticleFragment();
                return new HomeFragment();
            }

            @Override
            public CharSequence getPageTitle(int position) {
                return mTitles[position];
            }
        });

        mTablayout.setupWithViewPager(mViewpager);
    }

    private void showUser(User user){
        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        View headerView = navigationView.getHeaderView(0);
        TextView tv_login_quit = (TextView)headerView.findViewById(R.id.tv_login_quit);
        TextView tv_nickName = (TextView)headerView.findViewById(R.id.tv_nickname);
        String login_quit = "登录";
        if(user.isLogin()){
            login_quit = "退出登录";
        }
        SpannableString spannableString = new SpannableString(login_quit);
        if(!user.isLogin()){
            spannableString.setSpan(new ClickableSpan() {
                @Override
                public void onClick(View view) {
                    Intent intent=new Intent(MainActivity.this,LoginActivity.class);
//                    startActivity(intent);
                    startActivityForResult(intent,0);
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
        tv_nickName.setText(user.getId());
    }

}
