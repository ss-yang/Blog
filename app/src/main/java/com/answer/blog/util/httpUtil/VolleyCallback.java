package com.answer.blog.util.httpUtil;

import org.json.JSONObject;

/**
 * Created by Answer on 2017/6/11.
 * Volley的onResponse回调接口
 */

public interface VolleyCallback {
    void onSuccess(JSONObject response);
}
