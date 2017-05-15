package com.answer.blog.View;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.answer.blog.Data.Article;
import com.answer.blog.R;
import com.answer.blog.Util.ArticleAdapter;
import com.answer.blog.Util.ArticleManager;
import com.answer.blog.Util.BaseFragment;
import com.answer.blog.Util.RecyclerItemClickListener;

/**
 * Created by Answer on 2017/5/15.
 */

public class HomeFragment extends BaseFragment {
    private ArticleAdapter articleAdapter;
    private ArticleManager articleManager;


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.content_main,container,false);
        initArticleList(view);
        return view;
    }

    public void initArticleList(View view){

        RecyclerView recyclerView = (RecyclerView)view.findViewById(R.id.recycle_view_main);
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
                        Article article = articleManager.getArticleList().get(position);
                        intent.putExtra("article_data",article);
                        startActivity(intent);
                    }

                    @Override
                    public void onItemLongClick(View view, int position) {
                        Toast.makeText(getActivity(), "long click:" + position, Toast.LENGTH_SHORT).show();
                    }
                }));
    }


}
