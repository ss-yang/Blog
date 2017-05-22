package com.answer.blog.Util.HttpUtil;

import android.util.Log;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.answer.blog.View.MainActivity;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by Answer on 2017/5/21.
 */

public class HttpPostUtil {

    public static void loginPost(final String userId, final String password, String url, final VolleyCallback callback){
        StringRequest stringRequest= new StringRequest(Request.Method.POST,url,
                new Response.Listener<String>() {
                 @Override
                    public void onResponse(String s) {
                        try{
                            JSONObject jsonObject = new JSONObject(s);
                            callback.onSuccess(jsonObject);
                        }catch (JSONException e){
                            Log.d("TAG",e.getMessage());
                        }

                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.e("TAG", "error -> "+error.getMessage(), error);
            }
        }){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String,String> params = new HashMap<String, String>();
                params.put("id", userId);
                params.put("password", password);
                return params;
            }

            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                Map<String,String> params = new HashMap<String, String>();
                params.put("Content-Type","application/x-www.form-urlencoded");
                return params;
            }
        };
        MainActivity.mQueue.add(stringRequest);
    }
    public interface VolleyCallback{
        void onSuccess(JSONObject result);
    }
}
