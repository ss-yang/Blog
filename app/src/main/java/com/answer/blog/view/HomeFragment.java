package com.answer.blog.view;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
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

public class HomeFragment extends BaseFragment implements SwipeRefreshLayout.OnRefreshListener{
    private ArticleAdapter articleAdapter;
    private ArticleManager articleManager;
    private SwipeRefreshLayout refreshLayout;
    private RecyclerView recyclerView;
    private View view;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.content_main,container,false);
        initLayout(view);
        initArticleList(view);
        Log.d("TAG","HomeFragment onCreate");
        return view;
    }

    @Override
    public void onStart() {
        super.onStart();
        initArticleList(view);
        Log.d("TAG","HomeFragment onStart");
    }

    @Override
    public void onResume() {
        super.onResume();
        initArticleList(view);
        Log.d("TAG","HomeFragment onResume");
    }

    @Override
    public void onRefresh() {
        Log.d("TAG","home onfresh");
        //refresh here
        new Thread(new Runnable() {
            @Override
            public void run() {
                try{
                    Thread.sleep(1000);
                    // 网络请求
                    DataRequester.requestArticleList();

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
    }



    private void initLayout(View view){
        refreshLayout = (SwipeRefreshLayout)view.findViewById(R.id.swipe_refresh);
        refreshLayout.setColorSchemeColors(getResources().getColor(R.color.colorPrimary));
        refreshLayout.setOnRefreshListener(this);
    }

    public void initArticleList(View view){
        recyclerView = (RecyclerView)view.findViewById(R.id.recycle_view_main);
        articleManager = MainActivity.articleManager;
        articleAdapter = new ArticleAdapter(articleManager.getArticleList());

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
