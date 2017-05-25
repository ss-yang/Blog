package com.answer.blog.util.httpUtil;

import android.util.Log;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.answer.blog.BlogConst;
import com.answer.blog.data.bean.EntityArticle;
import com.answer.blog.view.MainActivity;
import com.google.gson.Gson;

import org.json.JSONObject;

/**
 * Created by Answer on 2017/5/22.
 */

public class DataRequester {

    public DataRequester(){}

    public static void requestArticleList(){

        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(BlogConst.urlHome, null, new Response.Listener<JSONObject>() {
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
}
