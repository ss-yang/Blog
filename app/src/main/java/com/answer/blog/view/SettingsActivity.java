package com.answer.blog.view;

import android.os.Bundle;
import android.preference.Preference;
import android.support.v7.app.ActionBar;
import android.util.Log;
import android.view.MenuItem;

import com.answer.blog.R;
import com.answer.blog.util.AppCompatPreferenceActivity;

public class SettingsActivity extends AppCompatPreferenceActivity implements Preference.OnPreferenceClickListener {
    private Preference prefChangeTheme;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        addPreferencesFromResource(R.xml.settings);
        setActionBar();
    }

    @Override
    public boolean onPreferenceClick (Preference preference){
        if(preference.getKey().equals("change_theme")) {
            Log.d("TAG", "DDDDDDDDDDDDDDDDDDDDDDDDD");
            return true;
        }
        Log.d("TAG", "OOOOOOOOOOOOOOOOOOOOOOO");
        return true;
    }

    private void setActionBar(){
        ActionBar actionBar = getSupportActionBar();
        if(actionBar != null){
            actionBar.setDisplayHomeAsUpEnabled(true);
            actionBar.setTitle("设置");
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        switch (id){
            case android.R.id.home :{
                finish();
            }
        }
        return super.onOptionsItemSelected(item);
    }
}
