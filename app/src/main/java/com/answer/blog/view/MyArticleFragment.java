package com.answer.blog.view;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.answer.blog.R;
import com.answer.blog.data.bean.EntityArticle;
import com.answer.blog.util.ArticleAdapter;
import com.answer.blog.util.ArticleManager;
import com.answer.blog.util.BaseFragment;
import com.answer.blog.util.RecyclerItemClickListener;
import com.answer.blog.util.httpUtil.DataRequester;

/**
 * Created by Answer on 2017/5/15.
 */

public class MyArticleFragment extends BaseFragment implements SwipeRefreshLayout.OnRefreshListener {
    private ArticleAdapter articleAdapter;
    private ArticleManager articleManager;
    private SwipeRefreshLayout refreshLayout;
    private RecyclerView recyclerView;
    private View view;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.mylist_content_main,container,false);
        initLayout(view);
        Log.d("TAG","MyArticleFragment onCreate");
        return view;
    }

    @Override
    public void onStart() {
        super.onStart();
        initArticleList(view);
        Log.d("TAG","MyArticleFragment onStart");
    }

    @Override
    public void onResume() {
        super.onResume();
        Log.d("TAG","MyArticleFragment onResume");
        if(MainActivity.user.isLogin()){
            initLayout(view);
            initArticleList(view);
        }else {
            Snackbar.make(view, "还没有登录哦~", Snackbar.LENGTH_LONG).setAction("登录", new View.OnClickListener(){
                @Override
                public void onClick(View v) {
                    startActivity(new Intent(getActivity(),LoginActivity.class));
                }
            }).show();
        }
    }


    @Override
    public void onRefresh() {
        if(!MainActivity.user.isLogin()){
            refreshLayout.setRefreshing(false);
            Snackbar.make(view, "还没有登录哦~", Snackbar.LENGTH_LONG).setAction("登录", new View.OnClickListener(){
                @Override
                public void onClick(View v) {
                    startActivity(new Intent(getActivity(),LoginActivity.class));
                }
            }).show();
            return;
        }
        //refresh here
        new Thread(new Runnable() {
            @Override
            public void run() {
                try{
                    Thread.sleep(1000);
                    // 网络请求
                    DataRequester.requestMyArticleList();
                }catch (InterruptedException e){
                    e.printStackTrace();
                }
                getActivity().runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        //reload
                        initArticleList(view);
                        articleAdapter.notifyDataSetChanged();
                        refreshLayout.setRefreshing(false);
                    }
                });
            }
        }).start();
        Log.d("TAG","MyArticleFragment onRefresh");
    }



    private void initLayout(View view){
        refreshLayout = (SwipeRefreshLayout)view.findViewById(R.id.mylist_swipe_refresh);
        refreshLayout.setColorSchemeColors(getResources().getColor(R.color.colorPrimary));
        refreshLayout.setOnRefreshListener(this);
    }

    public void initArticleList(View view){
        recyclerView = (RecyclerView)view.findViewById(R.id.mylist_recycle_view_main);
        articleManager = MainActivity.articleManager;
        articleAdapter = new ArticleAdapter(articleManager.getMyArticleList());

        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));//线性
//        recyclerView.setLayoutManager(new GridLayoutManager(this,2));//宫格
//        recyclerView.setLayoutManager(new StaggeredGridLayoutManager(2, OrientationHelper.VERTICAL));//瀑布流
        recyclerView.setAdapter(articleAdapter);

        recyclerView.addOnItemTouchListener(new RecyclerItemClickListener(getActivity(), recyclerView,
                new RecyclerItemClickListener.OnItemClickListener() {
                    @Override
                    public void onItemClick(View view, int position) {
                        Intent intent = new Intent(getActivity(),ArticleDetail.class);
                        EntityArticle.ArticleBean article = articleManager.getArticleList().get(position);
                        intent.putExtra("article_data",article);
                        startActivity(intent);
                    }

                    @Override
                    public void onItemLongClick(View view, int position) {
//                        Toast.makeText(getActivity(), "long click:" + position, Toast.LENGTH_SHORT).show();
                    }
                }));
    }


}
