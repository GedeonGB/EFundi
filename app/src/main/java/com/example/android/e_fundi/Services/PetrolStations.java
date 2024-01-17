package com.example.android.e_fundi.Services;

import android.content.Intent;
import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import com.example.android.e_fundi.Lists.Recycler_Item;
import com.example.android.e_fundi.Lists.items;
import com.example.android.e_fundi.LogicHelper.BottomNavigationViewHelper;
import com.example.android.e_fundi.R;

import java.util.ArrayList;
import java.util.List;

import qiu.niorgai.StatusBarCompat;

public class PetrolStations extends AppCompatActivity {


    List<items> lstItem;
    private final int SPLASH_TIME_OUT = 200;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_petrol_stations);

        //set color for status bar
        StatusBarCompat.setStatusBarColor(this, Color.argb(21,230,230,230));
        //add alpha to color
        //StatusBarCompat.setStatusBarColor(Activity activity, int statusColor, int alpha)

        //translucent status bar
        StatusBarCompat.translucentStatusBar(this);
        //should hide status bar background (default black background) when SDK >= 21
        StatusBarCompat.translucentStatusBar(this, false);



        BottomNavigationView navigation = (BottomNavigationView) findViewById(R.id.navigation);
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);
        BottomNavigationViewHelper.disableShiftMode(navigation);
        Menu menu = navigation.getMenu();
        MenuItem menuItem = menu.getItem(1);
        menuItem.setChecked(true);

        new myTaskHW().execute();
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

                    return true;

                case R.id.spareS:
                    startActivity(new Intent(getApplicationContext(), SpareShop.class));
                    return true;

                case R.id.feedback:

                            Intent intentMarket = new Intent(getApplicationContext(), fedbackActivity.class);
                            startActivity(intentMarket);

                    return true;

                case R.id.towtruck:

                            Intent intentMap = new Intent(getApplicationContext(), TruckTow.class);
                            startActivity(intentMap);
                          return true;


            }
            return false;
        }

    };



    class myTaskHW extends AsyncTask<Void, List, String> {

        Recycler_Item mAdapter;
        private int count=0;
        @Override
        protected void onPreExecute() {
            lstItem = new ArrayList<>();
            mAdapter = new Recycler_Item(PetrolStations.this, lstItem);
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
                lstItem.add(new items("12-12-2017","SUPER tyre","Ssenoga", R.drawable.tyre2));
                lstItem.add(new items("12-12-2017","SUPER tyre","Ssenoga", R.drawable.tyre2));
                lstItem.add(new items("12-12-2017","SUPER tyre","Ssenoga", R.drawable.tyre2));
                lstItem.add(new items("12-12-2017","SUPER tyre","Ssenoga", R.drawable.tyre2));
                lstItem.add(new items("12-12-2017","Turbo tyre","Ssenoga", R.drawable.tyre3));
                lstItem.add(new items("12-12-2017","SUPER tyre","Ssenoga", R.drawable.img3));
                lstItem.add(new items("12-12-2017","Turbo tyre","Ssenoga", R.drawable.img4));

                publishProgress(lstItem);

            } catch (Exception e) {
                Toast.makeText(PetrolStations.this, e.getMessage(), Toast.LENGTH_SHORT).show();

            }

            return "All the styles have been loaded successfully";
        }

        @Override
        protected void onProgressUpdate(List... values) {

            RecyclerView myrx4 = (RecyclerView) findViewById(R.id.recyclerPetrolS);
            myrx4.setNestedScrollingEnabled(false);
            GridLayoutManager layoutManager4 = new GridLayoutManager(PetrolStations.this, 1);
            myrx4.setLayoutManager(layoutManager4);
            myrx4.setAdapter(mAdapter);

        }

        @Override
        protected void onPostExecute(String result) {
            setProgressBarVisibility(false);
        }

    }

}
