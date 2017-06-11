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

import org.json.JSONException;
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
    public static void requestArticleList(final VolleyCallback callback){
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(BlogConst.url_home, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                callback.onSuccess(response);
            }},
                new Response.ErrorListener(){
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Log.d("TAG","error -> "+error.getMessage());
                    }
                }
        );
        MainActivity.mQueue.add(jsonObjectRequest);
    }

    /**
     * 请求我的文章列表
     */
    public static void requestMyArticleList(){
        StringRequest stringRequest = new StringRequest(BlogConst.url_my_article, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try{
                    JSONObject jsonObject = new JSONObject(response);
                    if (jsonObject.get("code").toString().equals("200")){
                        EntityArticle entityArticle;
                        Gson gson = new Gson();
                        entityArticle = gson.fromJson(response, EntityArticle.class);
                        MainActivity.articleManager.setMyArticleList(entityArticle.getArticles());
                    }
                }catch (JSONException e){
                    e.printStackTrace();
                }
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
        MainActivity.mQueue.add(stringRequest);
    }

    /**
     * 请求文章评论列表
     */
    public static void requestArticleCommentList(String id, final VolleyCallback callback){
        String url = BlogConst.url_get_article_comment + "?id=" + id; //构造请求url
        StringRequest commentRequest = new StringRequest(url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try{
                    JSONObject jsonObject = new JSONObject(response);
                    if (jsonObject.get("code").toString().equals("200")){
                        callback.onSuccess(jsonObject);
                    }
                }catch (JSONException e){
                    e.printStackTrace();
                }
            }},
                new Response.ErrorListener(){
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Log.d("TAG","error -> "+error.getMessage());
                    }
                }
        );
        MainActivity.mQueue.add(commentRequest );
    }

    /**
     * 请求文章评论列表
     */
    public static void requestUserMessage(final VolleyCallback callback){
        String url = BlogConst.url_message;
        StringRequest commentRequest = new StringRequest(url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try{
                    JSONObject jsonObject = new JSONObject(response);
                    if (jsonObject.get("code").toString().equals("200")){
                        callback.onSuccess(jsonObject);
                    }
                }catch (JSONException e){
                    e.printStackTrace();
                }
            }},
                new Response.ErrorListener(){
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Log.d("TAG","error -> "+error.getMessage());
                    }
                }
        ){
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                Map<String,String> params = new HashMap<String, String>();
                params.put("Content-Type","application/x-www.form-urlencoded");
                params.put("Cookie", MainActivity.user.getCookieId());
                return params;
            }
        };
        MainActivity.mQueue.add(commentRequest );
    }
}
