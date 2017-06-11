package com.answer.blog.util;

import android.content.Context;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.answer.blog.R;
import com.answer.blog.data.bean.EntityMessage;

import java.util.List;

/**
 * Created by Answer on 2017/6/7.
 */

public class MessageAdapter extends RecyclerView.Adapter<MessageAdapter.ViewHolder>  {
    private Context mContext;
    private List<EntityMessage.MessageBean> messageList;

    public MessageAdapter(List<EntityMessage.MessageBean> messageList){
        this.messageList = messageList;
    }

    static class ViewHolder extends RecyclerView.ViewHolder{
        CardView cardView;
        ImageView iv_article;
        TextView content,author,date;
        public ViewHolder(View view){
            super(view);
            cardView = (CardView)view;
            iv_article = (ImageView)view.findViewById(R.id.iv_comment_avatar);
            content = (TextView)view.findViewById(R.id.tv_comment_content);
            author = (TextView)view.findViewById(R.id.tv_comment_author);
            date = (TextView)view.findViewById(R.id.tv_comment_date);
        }
    }

    @Override
    public MessageAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType){
        if(mContext == null){
            mContext = parent.getContext();
        }
        View view = LayoutInflater.from(mContext).inflate(R.layout.comment_item, parent,false);
        return new MessageAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(MessageAdapter.ViewHolder holder, int position){
        EntityMessage.MessageBean message = messageList.get(position);
        holder.content.setText(message.getContent());
        holder.author.setText(message.getAuthor());
        holder.date.setText(message.getTime());
        holder.cardView.setTag(position);//上面的onClick用(int)v.getTag()获取位置，所以这里需要设置
    }

    @Override
    public int getItemCount(){
        return messageList.size();
    }

}
