package com.example.android.e_fundi.Services;

import android.content.Intent;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ProgressBar;

import com.example.android.e_fundi.LogicHelper.BottomNavigationViewHelper;
import com.example.android.e_fundi.R;

public class fedbackActivity extends AppCompatActivity {

    private final int SPLASH_TIME_OUT = 200;
    ProgressBar feedback_progress;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fedback);



        feedback_progress = (ProgressBar) findViewById(R.id.feedback_progress);
        BottomNavigationView navigation = (BottomNavigationView) findViewById(R.id.navigation);
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);
        BottomNavigationViewHelper.disableShiftMode(navigation);
        Menu menu = navigation.getMenu();
        MenuItem menuItem = menu.getItem(0);
        menuItem.setChecked(true);

    }



    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {


        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            switch (item.getItemId()) {

                case R.id.garage:
                    startActivity(new Intent(getApplicationContext(), MapMecActivity.class));

                    return true;

                case R.id.petrolS:
                    startActivity(new Intent(getApplicationContext(), PetrolStations.class));

                    return true;

                case R.id.spareS:
                    startActivity(new Intent(getApplicationContext(), SpareShop.class));
                    return true;

                case R.id.feedback:

                    return true;

                case R.id.towtruck:
                    //mTextMessage.setText(R.string.map);
                    new Handler().postDelayed(new Runnable() {

                        @Override
                        public void run() {
                            Intent intentMap = new Intent(getApplicationContext(), TruckTow.class);
                            startActivity(intentMap);
                            finish();
                        }
                    }, SPLASH_TIME_OUT);
                    return true;


            }
            return false;
        }

    };

    public void SendFb(View view) {
        feedback_progress.setVisibility(View.VISIBLE);
    }
}
