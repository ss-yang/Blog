package com.answer.blog.Util;

import android.content.Context;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.answer.blog.Data.Article;
import com.answer.blog.R;

import java.util.List;

/**
 * Created by Answer on 2017/5/13.
 */

public class ArticleAdapter extends RecyclerView.Adapter<ArticleAdapter.ViewHolder> implements View.OnClickListener {
    private Context mContext;
    private List<Article> articleList;
    private OnMyItemClickListener myItemClickListener;

    public ArticleAdapter(List<Article> articleList){
        this.articleList = articleList;
    }

    static class ViewHolder extends RecyclerView.ViewHolder{
        CardView cardView;
        ImageView iv_article;
        TextView title,author,date;
        public ViewHolder(View view){
            super(view);
            cardView = (CardView)view;
            iv_article = (ImageView)view.findViewById(R.id.iv_article);
            title = (TextView)view.findViewById(R.id.tv_title);
            author = (TextView)view.findViewById(R.id.tv_author);
            date = (TextView)view.findViewById(R.id.tv_date);
        }
    }

    /**
     * 自定义一个接口，实现RecycleView的点击事件
     */
    public static interface OnMyItemClickListener {
        void onItemClick(View view , int position);
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent,int viewType){
        if(mContext == null){
            mContext = parent.getContext();
        }
        View view = LayoutInflater.from(mContext).inflate(R.layout.article_item,parent,false);
        //在这里为每个item view添加点击事件
        view.setOnClickListener(this);
        return new ViewHolder(view);
    }

    @Override
    public void onClick(View v) {
        if (myItemClickListener != null) {
            //注意这里使用getTag方法获取position
            myItemClickListener.onItemClick(v,(int)v.getTag());
        }
    }

    public void setOnItemClickListener(OnMyItemClickListener listener) {
        this.myItemClickListener = listener;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder,int position){
        Article article = articleList.get(position);
        holder.title.setText(article.getTitle());
        holder.author.setText(article.getAuthor());
        holder.date.setText(article.getTime());
        holder.cardView.setTag(position);//上面的onClick用(int)v.getTag()获取位置，所以这里需要设置

    }

    @Override
    public int getItemCount(){
        return articleList.size();
    }


}
