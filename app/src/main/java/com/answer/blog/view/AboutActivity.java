package com.answer.blog.view;

import android.content.pm.ApplicationInfo;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import com.answer.blog.R;

import java.util.Random;

public class AboutActivity extends AppCompatActivity {

    ImageView imageView;
    TextView devName, devId, devEmail, devWebsite;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about);
        init();
    }

    private void init(){
        imageView = (ImageView)findViewById(R.id.about_img);
        devName = (TextView)findViewById(R.id.dev_name);
        devId = (TextView)findViewById(R.id.dev_id);
        devEmail = (TextView)findViewById(R.id.dev_email);
        devWebsite = (TextView)findViewById(R.id.dev_website);

        Random random = new Random();
        int resId = getResIdByName("bg" + String.valueOf(random.nextInt(34) + 1));
        imageView.setBackgroundResource(resId);

        devName.setText("杨盛");
        devId.setText("1400170168");
        devEmail.setText("ysatgg1608@gmail.com");
        devWebsite.setText("syang.site");
    }

    /**
     * 通过文件名获取resource id
     * @param name 资源文件名
     * @return
     */
    public int getResIdByName(String name)
    {
        ApplicationInfo appInfo = getApplicationInfo();
        return getResources().getIdentifier(name, "drawable", appInfo.packageName);
    }
}
