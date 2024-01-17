package com.example.android.e_fundi.Lists;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.location.Location;
import android.location.LocationManager;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Build;
import android.provider.Settings;
import android.support.annotation.RequiresApi;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Gravity;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.android.e_fundi.BaseActivity;
import com.example.android.e_fundi.R;
import com.example.android.e_fundi.Services.SpareShop;
import com.example.android.e_fundi.Sign_out_Activity;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

import qiu.niorgai.StatusBarCompat;

public class itemDescription extends AppCompatActivity implements OnMapReadyCallback {

    ImageView ImgItem, ImgItem2;
    String itemName, itemDate, itemShop;
    int itemImg;
    String sNUm, emailStr;
    private GoogleMap mMap;
    RelativeLayout containerMap;


    private final int SPLASH_TIME_OUT = 200;
    ImageView arrowToLeft, arrowToRight;
    List<items> lstItem;
    TextView itemNametxt, itemShoptxt, itemDatetxt, itemdesc,itemEmail;
    Recycler_Item mAdapter;
    LinearLayoutManager layoutManager3, layoutManager4;
    RecyclerView myrx3, myrx4;
    LocationManager locationManager;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_item_description);


        //set color for status bar
        StatusBarCompat.setStatusBarColor(this, Color.argb(21,230,230,230));
        //add alpha to color
        //StatusBarCompat.setStatusBarColor(Activity activity, int statusColor, int alpha)

        //translucent status bar
        StatusBarCompat.translucentStatusBar(this);
        //should hide status bar background (default black background) when SDK >= 21
        StatusBarCompat.translucentStatusBar(this, false);


        containerMap = (RelativeLayout) findViewById(R.id.containerMap);

        ImgItem2 = (ImageView) findViewById(R.id.imgItem1);
        ImgItem = (ImageView) findViewById(R.id.imgItem);
        itemNametxt = (TextView) findViewById(R.id.itemName1);
        itemShoptxt = (TextView) findViewById(R.id.itemShop1);

        Intent i = getIntent();
        itemName = i.getExtras().getString("itemName");
        itemShop = i.getExtras().getString("itemShop");
        itemDate = i.getExtras().getString("itemDate");
        itemImg = i.getExtras().getInt("itemImg");

        Picasso.with(itemDescription.this).load(itemImg).into(ImgItem);
        Picasso.with(itemDescription.this).load(itemImg).into(ImgItem2);
        itemNametxt.setText(itemName);
        itemShoptxt.setText(itemShop);


        itemEmail = (TextView) findViewById(R.id.itemEmail);
        emailStr = itemEmail.getText().toString();

        arrowToLeft = (ImageView) findViewById(R.id.arrowToLeft);
        arrowToRight = (ImageView) findViewById(R.id.arrowToRight);
        myrx3 = (RecyclerView) findViewById(R.id.recyclerSpearItem);
        myrx4 = (RecyclerView) findViewById(R.id.recyclerTrending);
        layoutManager3 = new LinearLayoutManager(itemDescription.this, LinearLayoutManager.HORIZONTAL, false);
        layoutManager4 = new LinearLayoutManager(itemDescription.this, LinearLayoutManager.HORIZONTAL, false);


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


//        Toast toastp = Toast.makeText(itemDescription.this, "Prices updated", Toast.LENGTH_SHORT);
//        toastp.setGravity(Gravity.CENTER, 1, 1);
//        toastp.show();


        LocationManager_check locationManagerCheck = new LocationManager_check(
                this);
        Location location = null;

        if (locationManagerCheck.isLocationServiceAvailable()) {

            if (locationManagerCheck.getProviderType() == 1) {
                if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                    // TODO: Consider calling
                    //    ActivityCompat#requestPermissions
                    // here to request the missing permissions, and then overriding
                    //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
                    //                                          int[] grantResults)
                    // to handle the case where the user grants the permission. See the documentation
                    // for ActivityCompat#requestPermissions for more details.
                    return;
                }
                location = locationManager.getLastKnownLocation(LocationManager.NETWORK_PROVIDER);
            }
            else if (locationManagerCheck.getProviderType() == 2)
                location = locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);
        }else{
            locationManagerCheck .createLocationServiceError(itemDescription.this);
        }

    }

    public void btnCall(View view) {

        Intent i = new Intent(Intent.ACTION_CALL);
        sNUm = "0785516556";

        if (sNUm.trim().isEmpty()) {
            i.setData(Uri.parse("tel: 75516556"));
        } else {
            i.setData(Uri.parse("tel:" +sNUm));
        }

        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {
            Toast.makeText(this, "Please grant the permission to call", Toast.LENGTH_SHORT).show();
//            requestPermission();
        }
        else {
            startActivity(i);
        }
    }

    public void toSpareAct(View view) {
        startActivity(new Intent(itemDescription.this, SpareShop.class));

    }

    public void toAdvsActivity(View view) {
        startActivity(new Intent(itemDescription.this, BaseActivity.class));

    }

    public void ToRegister(View view) {
        startActivity(new Intent(itemDescription.this, Sign_out_Activity.class));

    }

    @Override
    public void onMapReady(GoogleMap googleMap) {


        mMap = googleMap;

        //mMap.getUiSettings().setZoomControlsEnabled(true);
        mMap.getUiSettings().setZoomGesturesEnabled(true);
        mMap.getUiSettings();
        mMap.getCameraPosition();
        mMap.getProjection();
        mMap.isMyLocationEnabled();
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return;
        }
        mMap.setMyLocationEnabled(true);
        mMap.setMapType(GoogleMap.MAP_TYPE_NORMAL);

    }

    public void SHowMap(View view) {

        containerMap.setVisibility(View.VISIBLE);
    }

    public void toEmail(View view) {
        Intent i = new Intent(Intent.ACTION_SEND);
        i.setData(Uri.parse("email"));
        String[] s ={emailStr};
        i.putExtra(Intent.EXTRA_EMAIL, s);
        i.putExtra(Intent.EXTRA_SUBJECT, "");
        i.setType("message/rfc822");
        Intent chooser = Intent.createChooser(i, "Launch Email");
        startActivity(chooser);
    }


    public class LocationManager_check {

        LocationManager locationManager;
        Boolean locationServiceBoolean = false;
        int providerType = 0;
        AlertDialog alert;

        public LocationManager_check(Context context) {
            locationManager = (LocationManager) context
                    .getSystemService(Context.LOCATION_SERVICE);
            boolean gpsIsEnabled = locationManager
                    .isProviderEnabled(LocationManager.GPS_PROVIDER);
            boolean networkIsEnabled = locationManager
                    .isProviderEnabled(LocationManager.NETWORK_PROVIDER);

            if (networkIsEnabled == true && gpsIsEnabled == true) {
                locationServiceBoolean = true;
                providerType = 1;

            } else if (networkIsEnabled != true && gpsIsEnabled == true) {
                locationServiceBoolean = true;
                providerType = 2;

            } else if (networkIsEnabled == true && gpsIsEnabled != true) {
                locationServiceBoolean = true;
                providerType = 1;
            }

        }


        public Boolean isLocationServiceAvailable() {
            return locationServiceBoolean;
        }

        public int getProviderType() {
            return providerType;
        }

        public void createLocationServiceError(final Activity activityObj) {

            // show alert dialog if Internet is not connected
            AlertDialog.Builder builder = new AlertDialog.Builder(activityObj);

            builder.setMessage(
                    "You need to activate location service to use this feature. Please turn on network or GPS mode in location settings")
                    .setTitle("LostyFound")
                    .setCancelable(false)
                    .setPositiveButton("Settings",
                            new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int id) {
                                    Intent intent = new Intent(
                                            Settings.ACTION_LOCATION_SOURCE_SETTINGS);
                                    activityObj.startActivity(intent);
                                    dialog.dismiss();
                                }
                            })
                    .setNegativeButton("Cancel",
                            new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int id) {
                                    dialog.dismiss();
                                }
                            });
            alert = builder.create();
            alert.show();
        }
    }


    class myTaskHW extends AsyncTask<Void, List, String> {

        Recycler_Item_front mAdapter;
        private int count=0;
        @Override
        protected void onPreExecute() {
            lstItem = new ArrayList<>();
            mAdapter = new Recycler_Item_front(itemDescription.this, lstItem);
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

                lstItem.add(new items("","Turbo tyre","Ssenoga", R.drawable.tire1));
                lstItem.add(new items("","Turbo tyre","Ssenoga", R.drawable.tyre2));
                lstItem.add(new items("","Turbo tyre","Ssenoga", R.drawable.tyre3));
                lstItem.add(new items("","Turbo tyre","Ssenoga", R.drawable.img3));
                lstItem.add(new items("","Turbo tyre","Ssenoga", R.drawable.img4));

                publishProgress(lstItem);


            } catch (Exception e) {
                Toast.makeText(itemDescription.this, e.getMessage(), Toast.LENGTH_SHORT).show();

            }

            return "All the styles have been loaded successfully";
        }

        @Override
        protected void onProgressUpdate(List... values) {

            myrx3.setNestedScrollingEnabled(false);
            myrx3.setLayoutManager(layoutManager3);
            myrx3.setAdapter(mAdapter);



            myrx4.setNestedScrollingEnabled(false);
            myrx4.setLayoutManager(layoutManager4);
            myrx4.setAdapter(mAdapter);


//            count ++;
//            setProgress((int)(((double)count/lstItem.size())*10000));

        }

        @Override
        protected void onPostExecute(String result) {
            setProgressBarVisibility(false);
        }

    }


}
