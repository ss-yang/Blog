package com.answer.blog.View;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.annotation.TargetApi;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;

import com.answer.blog.R;
import com.answer.blog.Util.HttpUtil.HttpPostUtil;

import org.json.JSONException;
import org.json.JSONObject;


public class LoginActivity extends AppCompatActivity implements View.OnClickListener{
    private String userId;
    private String password;

    private AutoCompleteTextView mUserIdView;
    private EditText mPasswordView;
    private View mProgressView;
    private View mLoginFormView;
    private Button mSignIn;

    private UserLoginTask mAuthTask = null;

    private boolean loginSuccess;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        mUserIdView = (AutoCompleteTextView)findViewById(R.id.userId);
        mPasswordView = (EditText)findViewById(R.id.password);
        mSignIn = (Button)findViewById(R.id.btn_sign_in);
        mSignIn.setOnClickListener(this);
        mProgressView = findViewById(R.id.login_progress);
        mLoginFormView = findViewById(R.id.login_form);

    }

    @Override
    public void onClick(View v) {
        attemptLogin();
        MainActivity.user.setLogin(true);
    }


    private void attemptLogin() {
        if (mAuthTask != null) {
            return;
        }

        // Reset errors.
        mUserIdView.setError(null);
        mPasswordView.setError(null);

        // Store values at the time of the login attempt.
        String userId= mUserIdView.getText().toString();
        String password = mPasswordView.getText().toString();

        boolean cancel = false;
        View focusView = null;

        // Check for a valid password, if the user entered one.
        if (!TextUtils.isEmpty(password) && !isPasswordValid(password)) {
            mPasswordView.setError(getString(R.string.error_invalid_password));
            focusView = mPasswordView;
            cancel = true;
        }

        // Check for a valid userId address.
        if (TextUtils.isEmpty(userId)) {
            mUserIdView.setError(getString(R.string.error_field_required));
            focusView = mUserIdView;
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
            mAuthTask = new UserLoginTask(userId, password);
            mAuthTask.execute((Void) null);
        }
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

    private class UserLoginTask extends AsyncTask<Void,Void,Boolean> {
        private String userId;
        private String password;


        public UserLoginTask(String userId, String password) {
            this.userId = userId;
            this.password = password;
        }

        private boolean loginServer() {
            Log.d("TAG","front loginServer");
            HttpPostUtil.loginPost(userId, password, getString(R.string.url_login), new HttpPostUtil.VolleyCallback() {
                @Override
                public void onSuccess(JSONObject result) {
                    try {
                        if (result.get("code").toString().equals("200")) {
                            loginSuccess = true;
                            Log.d("TAG","onSuccess loginSuccess:"+loginSuccess);
                        }
                    }catch (JSONException e){
                        e.printStackTrace();
                    }
                }
            });

            Log.d("TAG","end loginServer");
            return loginSuccess;
        }

        @Override
        protected Boolean doInBackground(Void... params) {
            boolean success;
            try {
                // Simulate network access.
                Thread.sleep(3000);
                success = loginServer();
            } catch (InterruptedException e) {
                e.printStackTrace();
                return false;
            }

            // register the new account here.
            // ......
            Log.d("TAG", "doInBackground :" + success);
            return success;
        }

        @Override
        protected void onPostExecute(Boolean aBoolean) {
            mAuthTask = null;
            showProgress(false);

            Log.d("TAG", "onPostExecute aBoolean:" + aBoolean.toString());
            if (aBoolean) {
                Intent intent = new Intent();
                intent.putExtra("userId", userId);
                setResult(RESULT_OK, intent);
                finish();
            } else {
                mPasswordView.setError(getString(R.string.error_incorrect_password));
                mPasswordView.requestFocus();
            }
        }


    }

}
