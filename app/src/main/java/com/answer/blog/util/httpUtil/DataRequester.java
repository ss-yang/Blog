package com.answer.blog.util.httpUtil;

import android.util.Log;

import com.android.volley.AuthFailureError;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.answer.blog.BlogConst;
import com.answer.blog.data.bean.EntityArticle;
import com.answer.blog.view.MainActivity;
import com.google.gson.Gson;

import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by Answer on 2017/5/22.
 */

public class DataRequester {

    public DataRequester(){}

    /**
     * 请求首页文章列表
     */
    public static void requestArticleList(){
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(BlogConst.url_home, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                Log.d("TAG","onResponse -> "+response.toString());

                EntityArticle entityArticle;
                Gson gson = new Gson();
                entityArticle = gson.fromJson(response.toString(), EntityArticle.class);
                MainActivity.articleManager.setArticleList(entityArticle.getArticles());

            }},
                new Response.ErrorListener(){
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Log.d("TAG","error -> "+error.getMessage());
                        //Toast.makeText(,"网络请求失败。",Toast.LENGTH_SHORT).show();
                    }
                }
        );
        MainActivity.mQueue.add(jsonObjectRequest);
    }

    /**
     * 请求我的文章列表
     */
    public static void requestMyArticleList(){
        StringRequest jsonObjectRequest = new StringRequest(BlogConst.url_my_article, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Log.d("TAG","onResponse -> "+response);
                EntityArticle entityArticle;
                Gson gson = new Gson();
                entityArticle = gson.fromJson(response, EntityArticle.class);
                MainActivity.articleManager.setMyArticleList(entityArticle.getArticles());

            }},
                new Response.ErrorListener(){
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Log.d("TAG","error -> "+error.getMessage());
                        //Toast.makeText(,"网络请求失败。",Toast.LENGTH_SHORT).show();
                    }
                }
        ){
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                HashMap hashMap = new HashMap();
                hashMap.put("Cookie", MainActivity.user.getCookieId());
                Log.d("TAG","cookies -> "+MainActivity.user.getCookieId());
                return hashMap;
            }
        };
        MainActivity.mQueue.add(jsonObjectRequest);
    }
}
