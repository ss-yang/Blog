package com.answer.blog.util.httpUtil;

import android.util.Log;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.answer.blog.BlogConst;
import com.answer.blog.view.MainActivity;
import com.answer.blog.util.implement.DataTransfer;

import org.json.JSONObject;

/**
 * Created by Answer on 2017/5/22.
 */

public class DataRequester {
    private DataTransfer dataTransfer;
    public DataRequester(DataTransfer dataTransfer){
        this.dataTransfer = dataTransfer;
    }


    public void requestArticleList(){

        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(BlogConst.urlHome, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                Log.d("TAG","onResponse -> "+response.toString());





                dataTransfer.setView(response.toString());

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
