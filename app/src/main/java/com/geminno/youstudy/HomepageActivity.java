package com.geminno.youstudy;

import android.app.Activity;
import android.graphics.Typeface;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.geminno.youstudy.fragment.homeworkFragment;
import com.geminno.youstudy.fragment.communicateFragment;
import com.geminno.youstudy.fragment.chatFragment;
import com.geminno.youstudy.fragment.settingFragment;

import java.util.List;

public class HomepageActivity extends AppCompatActivity implements View.OnClickListener {
    private ViewPager homework_vp;
    private RadioGroup rg_menu;
    private int currentPage = 0;
    private RadioButton rb1;
    private RadioButton rb2;
    private RadioButton rb3;
    private RadioButton rb4;
//    public static Activity HomepageActivity;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        this.requestWindowFeature(Window.FEATURE_NO_TITLE);//去掉标题栏
        setContentView(R.layout.activity_homepage);


        rb1 = ((RadioButton) findViewById(R.id.rb1));
        rb2 = ((RadioButton) findViewById(R.id.rb2));
        rb3 = ((RadioButton) findViewById(R.id.rb3));
        rb4 = ((RadioButton) findViewById(R.id.rb4));
        rb1.setOnClickListener(this);
        rb2.setOnClickListener(this);
        rb3.setOnClickListener(this);
        rb4.setOnClickListener(this);


    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.rb1:
                getSupportFragmentManager().beginTransaction().replace(R.id.fl_main, new homeworkFragment()).commit();
                break;

            case R.id.rb2:
                getSupportFragmentManager().beginTransaction().replace(R.id.fl_main, new communicateFragment()).commit();
                break;

            case R.id.rb3:
                getSupportFragmentManager().beginTransaction().replace(R.id.fl_main, new chatFragment()).commit();
                break;

            case R.id.rb4:
                getSupportFragmentManager().beginTransaction().replace(R.id.fl_main, new settingFragment()).commit();
                break;
        }
    }


    public void switchFragment(Fragment fragment) {

        getSupportFragmentManager().beginTransaction().
                replace(R.id.fl_main, fragment).commit();
    }


    public void inittext(TextView textView) {
        Typeface typeface = Typeface.createFromAsset(getAssets(), "fontawesome-webfont.ttf");
        textView.setTypeface(typeface);

    }


}

