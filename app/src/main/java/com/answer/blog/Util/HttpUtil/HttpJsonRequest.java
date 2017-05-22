package com.answer.blog.Util.HttpUtil;

import android.util.Log;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.answer.blog.View.MainActivity;

import org.json.JSONObject;

/**
 * Created by Answer on 2017/5/22.
 */

public class HttpJsonRequest {
    private static JSONObject result;

    public HttpJsonRequest(){}

    public static void jsonRequest(String url){
        Log.d("TAG","URL:"+url);

        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(url,null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                result = response;
                Log.d("TAG","onResponse -> "+response.toString());

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
}
