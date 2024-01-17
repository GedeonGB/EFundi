package com.example.android.e_fundi;

import android.content.Intent;
import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.annotation.RequiresApi;
import android.support.design.widget.BottomNavigationView;
import android.support.design.widget.BottomSheetBehavior;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.android.e_fundi.Lists.Recycler_Item;
import com.example.android.e_fundi.Lists.items;
import com.example.android.e_fundi.LogicHelper.BottomNavigationViewHelper;
import com.example.android.e_fundi.Services.MapMecActivity;
import com.example.android.e_fundi.Services.TruckTow;
import com.example.android.e_fundi.Services.PetrolStations;
import com.example.android.e_fundi.Services.SpareShop;
import com.example.android.e_fundi.Services.fedbackActivity;

import java.util.ArrayList;
import java.util.List;

import qiu.niorgai.StatusBarCompat;


public class BaseActivity extends AppCompatActivity {

    private final int SPLASH_TIME_OUT = 200;
    ImageView arrowToLeft, arrowToRight;
    List<items> lstItem;
    TextView itemNametxt, itemShoptxt, itemDatetxt, itemdesc;
    Recycler_Item mAdapter;
    LinearLayoutManager layoutManager3;
    RecyclerView myrx3;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_base);

        //set color for status bar
        StatusBarCompat.setStatusBarColor(this, Color.argb(21,230,230,230));
        //add alpha to color
        //StatusBarCompat.setStatusBarColor(Activity activity, int statusColor, int alpha)

        //translucent status bar
        StatusBarCompat.translucentStatusBar(this);
        //should hide status bar background (default black background) when SDK >= 21
        StatusBarCompat.translucentStatusBar(this, false);



        arrowToLeft=(ImageView)findViewById(R.id.arrowToLeft);
        arrowToRight=(ImageView)findViewById(R.id.arrowToRight);
        myrx3 = (RecyclerView) findViewById(R.id.recyclerSpearItem);
        layoutManager3 = new LinearLayoutManager(BaseActivity.this, LinearLayoutManager.VERTICAL, false);


        arrowToRight.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
            @Override
            public void onClick(View v) {
                if (layoutManager3.findFirstVisibleItemPosition() > 0) {
                    myrx3.smoothScrollToPosition(layoutManager3.findFirstVisibleItemPosition() - 1);
//                    arrowToRight.setBackgroundDrawable(getDrawable(R.drawable.cerclebackgroundpink));

                } else {
                    myrx3.smoothScrollToPosition(0);
//                    arrowToRight.setBackgroundColor(Color.argb(255,255,184,130));
                }

            }
        });

        arrowToLeft.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                myrx3.smoothScrollToPosition(layoutManager3.findLastVisibleItemPosition() + 1);
            }
        });

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            getWindow().setStatusBarColor(Color.argb(68, 61, 61, 61));
        }



        new myTaskHW().execute();


        Toast toastp = Toast.makeText(BaseActivity.this, "Prices updated", Toast.LENGTH_SHORT);
        toastp.setGravity(Gravity.CENTER, 1, 1);
        toastp.show();


        BottomNavigationView navigation = (BottomNavigationView) findViewById(R.id.navigation);
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);
        BottomNavigationViewHelper.disableShiftMode(navigation);
        Menu menu = navigation.getMenu();
        MenuItem menuItem = menu.getItem(2);
        menuItem.setChecked(true);

        View bottomSheet = findViewById(R.id.design_Bottom_sheet1);
        final BottomSheetBehavior behavior = BottomSheetBehavior.from(bottomSheet);
        behavior.setBottomSheetCallback(new BottomSheetBehavior.BottomSheetCallback() {
            @Override
            public void onStateChanged(@NonNull View bottomSheet, int newState) {

            }

            @Override
            public void onSlide(@NonNull View bottomSheet, float slideOffset) {

            }
        });
    }


    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
                = new BottomNavigationView.OnNavigationItemSelectedListener() {


            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()) {

                    case R.id.garage:
                        startActivity(new Intent(BaseActivity.this, MapMecActivity.class));

                        return true;

                    case R.id.petrolS:
                        startActivity(new Intent(BaseActivity.this, PetrolStations.class));

                        return true;

                    case R.id.spareS:
                        startActivity(new Intent(BaseActivity.this, SpareShop.class));
                        return true;

                    case R.id.feedback:
                        new Handler().postDelayed(new Runnable() {

                            @Override
                            public void run() {
                                Intent intentMarket = new Intent(BaseActivity.this, fedbackActivity.class);
                                startActivity(intentMarket);
                                finish();
                            }
                        }, SPLASH_TIME_OUT);
                        return true;

                    case R.id.towtruck:
                        //mTextMessage.setText(R.string.map);
                        new Handler().postDelayed(new Runnable() {

                            @Override
                            public void run() {
                                Intent intentMap = new Intent(BaseActivity.this, MapMecActivity.class);
                                startActivity(intentMap);
                                finish();
                            }
                        }, SPLASH_TIME_OUT);
                        return true;


                }
                return false;
            }

        };

        boolean twice = false;
        @Override
        public void onBackPressed() {
            //super.onBackPressed();


            if (twice == true) {
                Intent intent = new Intent(Intent.ACTION_MAIN);
                intent.addCategory(Intent.CATEGORY_HOME);
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);
                finish();
                System.exit(0);
            }
            twice = true;
            //Log.d(TAG, "twice: "+ twice);

            Toast toast = Toast.makeText(BaseActivity.this, "Please press BACK again to exit", Toast.LENGTH_SHORT);
            toast.setGravity(Gravity.CENTER,0,0);
            toast.show();


            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    twice = false;
                    //Log.d(TAG, "twice: "+ twice);
                }
            }, 3000);


        }



    public void toMechanic(View view) {
        startActivity(new Intent(BaseActivity.this, TruckTow.class));
    }

    public void toTowTruck(View view) {
        //startActivity(new Intent(BaseActivity.this, MechanicSvs.class));

    }

    public void toPS(View view) {
        startActivity(new Intent(BaseActivity.this, PetrolStations.class));

    }

    public void toSpareShop(View view) {
        startActivity(new Intent(BaseActivity.this, SpareShop.class));

    }

    public void toSpareAct(View view) {
        startActivity(new Intent(BaseActivity.this, SpareShop.class));

    }

    public void imgHide1(View view) {
    }


    //class for async task getting data from the server


    class myTaskHW extends AsyncTask<Void, List, String> {

        Recycler_Item mAdapter;
        private int count=0;
        @Override
        protected void onPreExecute() {
            lstItem = new ArrayList<>();
            mAdapter = new Recycler_Item(BaseActivity.this, lstItem);
//            setProgressBarIndeterminate(false);
//            setProgressBarVisibility(true);

        }

        @Override
        protected String doInBackground(Void... voids) {

//
//            stringRequest = new StringRequest(com.android.volley.Request.Method.GET, urlStyleHS, new Response.Listener<String>() {
//                @Override
//                public void onResponse(String response) {

                    try {

                        lstItem.add(new items("12-12-2017","Turbo tyre","Ssenoga", R.drawable.tire1));
                        lstItem.add(new items("12-12-2017","Turbo tyre","Ssenoga", R.drawable.tyre2));
                        lstItem.add(new items("12-12-2017","Turbo tyre","Ssenoga", R.drawable.tyre3));
                        lstItem.add(new items("12-12-2017","Turbo tyre","Ssenoga", R.drawable.img3));
                        lstItem.add(new items("12-12-2017","Turbo tyre","Ssenoga", R.drawable.img4));

                        publishProgress(lstItem);


                    } catch (Exception e) {
                        Toast.makeText(BaseActivity.this, e.getMessage(), Toast.LENGTH_SHORT).show();

                    }

            return "All the styles have been loaded successfully";
        }

        @Override
        protected void onProgressUpdate(List... values) {

            myrx3.setNestedScrollingEnabled(false);
            myrx3.setLayoutManager(layoutManager3);
            myrx3.setAdapter(mAdapter);

        }

        @Override
        protected void onPostExecute(String result) {
            setProgressBarVisibility(false);
        }

    }


}
