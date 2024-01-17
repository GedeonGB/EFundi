package com.example.android.e_fundi;

import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;

public class SceenPager extends AppCompatActivity {

    SlideAdapter myAdapter;
    ViewPager viewPager;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sceen_pager);

        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            getWindow().setStatusBarColor(Color.argb(68,61,61,61));
        }

        viewPager= (ViewPager) findViewById(R.id.viewPager);
        myAdapter = new SlideAdapter(this);
        viewPager.setAdapter(myAdapter);

    }

    public void toBaseAct(View view) {
        startActivity(new Intent(SceenPager.this, BaseActivity.class));
    }
}
