package com.answer.blog.view;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.provider.MediaStore;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import com.answer.blog.BlogConst;
import com.answer.blog.R;
import com.answer.blog.util.httpUtil.UploadImageApacheHttp;

import java.io.IOException;

public class UserInfoEditActivity extends AppCompatActivity implements View.OnClickListener{
    private Button btnChangeAvatar;
    private Toolbar toolbar;
    private ImageView avatar;

    private boolean selectedFile;
    private Uri filePath;
    private Bitmap bitmap;

    private final String SELECT_IMAGE = "更改头像";
    private final String UPDATE_IMAGE = "上传头像";

    private int PICK_IMAGE_REQUEST = 100;
    private final int READ_EXTERNAL_STORAGE_REQUEST_CODE = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_info_edit);
        initView();
        initToolbar();
        selectedFile = false;

        if (ContextCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE)
                != PackageManager.PERMISSION_GRANTED) {
            //申请WRITE_EXTERNAL_STORAGE权限
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.READ_EXTERNAL_STORAGE},
                    READ_EXTERNAL_STORAGE_REQUEST_CODE);
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == READ_EXTERNAL_STORAGE_REQUEST_CODE) {
            if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                // Permission Granted
            } else {
                // Permission Denied
            }
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.btn_change_avatar:{
                if(!selectedFile){
                    showFileChooser();
                    btnChangeAvatar.setText(UPDATE_IMAGE);
                    selectedFile = true;
                }else {
                    uploadImage(BlogConst.url_upload_avatar);
                    btnChangeAvatar.setText(SELECT_IMAGE);
                    selectedFile = false;
                }
                break;
            }
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case android.R.id.home :{
                finish();
            }
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        Log.d("TAG","enter onActivityResult");
        if (requestCode == PICK_IMAGE_REQUEST && resultCode == RESULT_OK && data != null && data.getData() != null) {
            filePath = data.getData();
            try {
                bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), filePath);
                avatar.setImageBitmap(bitmap);
                MainActivity.user.setAvatar(bitmap);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            Log.i("TAG", "Handler " + msg.what);
            if (msg.what == 1) {
                // upload success;
                Log.d("TAG","upload success;");
            } else {
                // upload failed;
                Log.d("TAG","upload failed;");
            }
        }

    };

    /**
     * 上传图片
     */
    private void uploadImage(String uploadUrl) {
        UploadImageApacheHttp uploadTask = new UploadImageApacheHttp();
        String url = uploadUrl + "?user="+MainActivity.user.getId();
        uploadTask.doFileUpload(url, bitmap, handler);
    }

    /**
     * 选择文件
     */
    private void showFileChooser() {
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(Intent.createChooser(intent, "Select Image"), PICK_IMAGE_REQUEST);
    }



    private void initView(){
        btnChangeAvatar = (Button)findViewById(R.id.btn_change_avatar);
        btnChangeAvatar.setOnClickListener(this);
        btnChangeAvatar.setText(SELECT_IMAGE);
        avatar = (ImageView)findViewById(R.id.iv_userinfo_edit_avatar);
    }

    /**
     * 初始化Toolbar
     */
    private void initToolbar(){
        toolbar = (Toolbar) findViewById(R.id.toolbar_user_info);
        setSupportActionBar(toolbar);
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);//显示导航按钮（返回）
            actionBar.setDisplayShowTitleEnabled(false);
            toolbar.setTitle(MainActivity.user.getId());
        }
    }
}
