package com.answer.blog.util.httpUtil;

import android.util.Log;

import com.android.volley.AuthFailureError;
import com.android.volley.NetworkResponse;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.answer.blog.BlogConst;
import com.answer.blog.view.MainActivity;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by Answer on 2017/5/21.
 */

public class HttpPostUtil {

    public interface VolleyCallback{
        void onSuccess(JSONObject result);
    }

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

            @Override
            protected Response<String> parseNetworkResponse(NetworkResponse response) {
                for (String s : response.headers.keySet()) {
                    if (s.contains("Set-Cookie")) {
                        String mCookie = response.headers.get(s);
                        Log.d("TAG","loginPost parseNetworkResponse: "+mCookie);
                        MainActivity.user.setCookieId(mCookie);
                        break;
                    }
                }
                return super.parseNetworkResponse(response);
            }
        };
        MainActivity.mQueue.add(stringRequest);
    }

    public static void newArticle(final String title, final String content){
        StringRequest stringRequest= new StringRequest(Request.Method.POST, BlogConst.url_new_article,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String s) {
                        Log.d("TAG","response:"+s);
                        try{
                            JSONObject jsonObject = new JSONObject(s);
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
                params.put("title", title);
                params.put("editcontent", content);
                return params;
            }

            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                Map<String,String> params = new HashMap<String, String>();
                params.put("Content-Type","application/x-www.form-urlencoded");
                params.put("Cookie", MainActivity.user.getCookieId());
                return params;
            }
        };
        MainActivity.mQueue.add(stringRequest);
    }

    public static void newMessage(final String id, final String content, final com.answer.blog.util.httpUtil.VolleyCallback callback){
        StringRequest stringRequest= new StringRequest(Request.Method.POST, BlogConst.url_new_comment,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String s) {
                        try{
                            callback.onSuccess(new JSONObject(s));
                        }catch (JSONException e){
                            e.printStackTrace();
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
                params.put("articleid", id);
                params.put("editcontent", content);
                return params;
            }
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                Map<String,String> params = new HashMap<String, String>();
                params.put("Content-Type","application/x-www.form-urlencoded");
                params.put("Cookie", MainActivity.user.getCookieId());
                return params;
            }
        };
        MainActivity.mQueue.add(stringRequest);
    }
}
