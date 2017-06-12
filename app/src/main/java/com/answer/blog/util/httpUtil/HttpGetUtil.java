package com.answer.blog.util.httpUtil;

import android.util.Log;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.answer.blog.BlogConst;
import com.answer.blog.view.MainActivity;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by Answer on 2017/6/11.
 */

public class HttpGetUtil {

    /**
     * 请求文章评论列表
     */
    public static void requestDeleteArticle(String name, String id, final VolleyCallback callback){
        String url = BlogConst.url_delete + "?name=" + name + "&id="+ id; //构造请求url
        StringRequest commentRequest = new StringRequest(url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Log.d("TAG","onResponse comments -> "+response);
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
     * 请求搜索结果
     */
    public static void requestSearch(String forsearch, final VolleyCallback callback){
        String url = BlogConst.url_search + "?forsearch=" + forsearch; //构造请求url
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
}
