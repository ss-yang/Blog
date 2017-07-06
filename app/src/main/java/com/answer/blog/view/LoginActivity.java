package com.answer.blog.view;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.annotation.TargetApi;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.View;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;

import com.answer.blog.BlogConst;
import com.answer.blog.R;
import com.answer.blog.util.httpUtil.DataRequester;
import com.answer.blog.util.httpUtil.HttpPostUtil;

import org.json.JSONException;
import org.json.JSONObject;


public class LoginActivity extends AppCompatActivity implements View.OnClickListener{
    private String userId;
    private String password;

    private AutoCompleteTextView mUserIdView;
    private EditText mPasswordView, mPassword2View;
    private View mProgressView;
    private View mLoginFormView;
    private Button btnSignIn,btnSignUp;

//    private UserLoginTask mAuthTask = null;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        mUserIdView = (AutoCompleteTextView)findViewById(R.id.userId);
        mPasswordView = (EditText)findViewById(R.id.password);
        mPassword2View = (EditText)findViewById(R.id.password2);
        mPassword2View.setVisibility(View.GONE);
        btnSignIn = (Button)findViewById(R.id.btn_sign_in);
        btnSignIn.setOnClickListener(this);
        btnSignUp = (Button)findViewById(R.id.btn_sign_up);
        btnSignUp.setOnClickListener(this);
        mProgressView = findViewById(R.id.login_progress);
        mLoginFormView = findViewById(R.id.login_form);

        //DEBUG
        mUserIdView.setText("admin");
        mPasswordView.setText("admin");

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.btn_sign_in:{
                if(btnSignIn.getText().equals(getString(R.string.action_sign_in))) {
                    attemptLogin();
                }else {
                    attemptRegister();
                }
                break;
            }
            case R.id.btn_sign_up:{
                if(btnSignIn.getText().equals(getString(R.string.action_sign_in))) {
                    btnSignIn.setText(getString(R.string.action_sign_up));
                    btnSignUp.setText("返回");
                    mPassword2View.setVisibility(View.VISIBLE);
                }else {
                    btnSignIn.setText(getString(R.string.action_sign_in));
                    btnSignUp.setText(getString(R.string.action_sign_up));
                    mPassword2View.setVisibility(View.GONE);
                }
                break;
            }
        }
    }

    private void attemptRegister() {
        // Store values at the time of the login attempt.
        userId= mUserIdView.getText().toString();
        password = mPasswordView.getText().toString();
        String password2 = mPassword2View.getText().toString();

        boolean cancel = false;
        View focusView = null;

        // Check for a valid userId.
        if (TextUtils.isEmpty(userId)) {
            mUserIdView.setError(getString(R.string.error_userId_required));
            focusView = mUserIdView;
            cancel = true;
        }

        if (TextUtils.isEmpty(password)) {
            mPasswordView.setError(getString(R.string.error_password_required));
            focusView = mPasswordView;
            cancel = true;
        }

        if (!isPasswordValid(password)) {
            mPasswordView.setError(getString(R.string.error_invalid_password));
            focusView = mPasswordView;
            cancel = true;
        }

        if(!password2.equals(password)){
            mPassword2View.setError(getString(R.string.error_inconformity_password));
            focusView = mPassword2View;
            cancel = true;
        }

        if (cancel) {
            // There was an error; don't attempt login and focus the first
            // form field with an error.
            focusView.requestFocus();
        } else {
            // Show a progress spinner, and kick off a background task to
            // perform the user login attempt.
            showProgress(true);
            loginServer(BlogConst.url_register);
        }
    }


    private void attemptLogin() {
        // Reset errors.
        mUserIdView.setError(null);
        mPasswordView.setError(null);

        // Store values at the time of the login attempt.
        userId= mUserIdView.getText().toString();
        password = mPasswordView.getText().toString();

        boolean cancel = false;
        View focusView = null;

        // Check for a valid userId address.
        if (TextUtils.isEmpty(userId)) {
            mUserIdView.setError(getString(R.string.error_userId_required));
            focusView = mUserIdView;
            cancel = true;
        }

        // Check for a valid password, if the user entered one.
        if (!TextUtils.isEmpty(password) && !isPasswordValid(password)) {
            mPasswordView.setError(getString(R.string.error_invalid_password));
            focusView = mPasswordView;
            cancel = true;
        }else if(TextUtils.isEmpty(password)) {
            mPasswordView.setError(getString(R.string.error_password_required));
            focusView = mPasswordView;
            cancel = true;
        }

        if (cancel) {
            // There was an error; don't attempt login and focus the first
            // form field with an error.
            focusView.requestFocus();
        } else {
            // Show a progress spinner, and kick off a background task to
            // perform the user login attempt.
            showProgress(true);
            loginServer(BlogConst.url_login);
        }
    }

    private void loginServer(String url) {
        HttpPostUtil.loginPost(userId, password, url, new HttpPostUtil.VolleyCallback() {
            @Override
            public void onSuccess(JSONObject result) {
                try {
                    if (result.get("code").toString().equals("200")) {
                        MainActivity.user.setId(userId);
                        MainActivity.user.setLogin(true);
                        MainActivity.user.setAvatarPath(result.get("data").toString());
                        DataRequester.requestMyArticleList();
                        finish();
                    }else {
                        mPasswordView.setError(result.get("message").toString());
                        mPasswordView.requestFocus();
                        showProgress(false);
                    }
                }catch (JSONException e){
                    e.printStackTrace();
                }
            }
        });
    }


    /**
     * 密码合法性检测
     */
    private boolean isPasswordValid(String password){
        return password.length() >= 4 ;
    }

    /**
     * Shows the progress UI and hides the login form.
     */
    @TargetApi(Build.VERSION_CODES.HONEYCOMB_MR2)
    private void showProgress(final boolean show) {
        // On Honeycomb MR2 we have the ViewPropertyAnimator APIs, which allow
        // for very easy animations. If available, use these APIs to fade-in
        // the progress spinner.
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB_MR2) {
            int shortAnimTime = getResources().getInteger(android.R.integer.config_shortAnimTime);

            mLoginFormView.setVisibility(show ? View.GONE : View.VISIBLE);
            mLoginFormView.animate().setDuration(shortAnimTime).alpha(
                    show ? 0 : 1).setListener(new AnimatorListenerAdapter() {
                @Override
                public void onAnimationEnd(Animator animation) {
                    mLoginFormView.setVisibility(show ? View.GONE : View.VISIBLE);
                }
            });

            mProgressView.setVisibility(show ? View.VISIBLE : View.GONE);
            mProgressView.animate().setDuration(shortAnimTime).alpha(
                    show ? 1 : 0).setListener(new AnimatorListenerAdapter() {
                @Override
                public void onAnimationEnd(Animator animation) {
                    mProgressView.setVisibility(show ? View.VISIBLE : View.GONE);
                }
            });
        } else {
            // The ViewPropertyAnimator APIs are not available, so simply show
            // and hide the relevant UI components.
            mProgressView.setVisibility(show ? View.VISIBLE : View.GONE);
            mLoginFormView.setVisibility(show ? View.GONE : View.VISIBLE);
        }
    }

}
