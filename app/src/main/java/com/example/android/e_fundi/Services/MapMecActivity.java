package com.example.android.e_fundi.Services;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.design.widget.BottomSheetBehavior;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.FragmentActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.android.e_fundi.LogicHelper.BottomNavigationViewHelper;
import com.example.android.e_fundi.R;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.karumi.dexter.Dexter;
import com.karumi.dexter.MultiplePermissionsReport;
import com.karumi.dexter.PermissionToken;
import com.karumi.dexter.listener.PermissionRequest;
import com.karumi.dexter.listener.multi.MultiplePermissionsListener;

import java.util.ArrayList;
import java.util.List;

import qiu.niorgai.StatusBarCompat;

public class MapMecActivity extends FragmentActivity implements OnMapReadyCallback {

    BottomSheetBehavior behavior;

    private static final int REQUEST_LOCATION = 500;
    private GoogleMap mMap;
    private final int SPLASH_TIME_OUT = 200;
    LinearLayout linearSearch1;
    ArrayList<LatLng> listPoints;
    FloatingActionButton fab_Normal, fab_Satellite, fab_Terrain;
    TextView searchP, nearbyP;

    Animation FabOpen, FabClose, FabClockWise, FabClockAntiWise;
    boolean isOpen = false;
    EditText edtSearch;
    int PROXIMITY_RADIUS = 10000;
    double end_latitude, end_longitude;
    double latitude, longitude;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_map_mec);



        //set color for status bar
        StatusBarCompat.setStatusBarColor(MapMecActivity.this, Color.argb(21,230,230,230));
        //add alpha to color
        //StatusBarCompat.setStatusBarColor(Activity activity, int statusColor, int alpha)

        //translucent status bar
        StatusBarCompat.translucentStatusBar(MapMecActivity.this);
        //should hide status bar background (default black background) when SDK >= 21
        StatusBarCompat.translucentStatusBar(MapMecActivity.this, false);

//        //set color for CollapsingToolbarLayout
//        setStatusBarColorForCollapsingToolbar(Activity activity, AppBarLayout appBarLayout, CollapsingToolbarLayout collapsingToolbarLayout,
//                Toolbar toolbar, int statusColor)



        linearSearch1 = (LinearLayout) findViewById(R.id.linearSearch1);
        searchP = (TextView) findViewById(R.id.txtsearchPlaces);
        nearbyP = (TextView) findViewById(R.id.txtnearbyPlaces);

        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);


        Dexter.withActivity(this)
                .withPermissions(
                        Manifest.permission.ACCESS_FINE_LOCATION,
                        Manifest.permission.READ_EXTERNAL_STORAGE,
                        Manifest.permission.CAMERA,
                        Manifest.permission.CALL_PHONE,
                        Manifest.permission.ACCESS_COARSE_LOCATION
                ).withListener(new MultiplePermissionsListener() {
            @Override
            public void onPermissionsChecked(MultiplePermissionsReport report) {/* ... */}

            @Override
            public void onPermissionRationaleShouldBeShown(List<PermissionRequest> permissions, PermissionToken token) {/* ... */}
        }).check();


        View bottomSheet = findViewById(R.id.design_Bottom_sheet1);
        behavior = BottomSheetBehavior.from(bottomSheet);

                behavior.setBottomSheetCallback(new BottomSheetBehavior.BottomSheetCallback() {
                    @Override
                    public void onStateChanged(@NonNull View bottomSheet, int newState) {
                        switch (newState) {
                            case BottomSheetBehavior.STATE_DRAGGING:
                                behavior.setState(BottomSheetBehavior.STATE_COLLAPSED);
                                Log.i("bts","bts exp");
                                break;

                            case BottomSheetBehavior.STATE_COLLAPSED:
                                behavior.setState(BottomSheetBehavior.STATE_EXPANDED);
                                break;
                        }

                    }

                    @Override
                    public void onSlide(@NonNull View bottomSheet, float slideOffset) {
                        behavior.setState(BottomSheetBehavior.STATE_COLLAPSED);
                        Log.i("BottomShCallback", "slide" + slideOffset);
                    }
                });
        behavior.setState(BottomSheetBehavior.STATE_EXPANDED);


        BottomNavigationView navigation = (BottomNavigationView) findViewById(R.id.navigation);
        BottomNavigationViewHelper.disableShiftMode(navigation);
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);

        Menu menu = navigation.getMenu();
        MenuItem menuItem = menu.getItem(2);
        menuItem.setChecked(true);


        //Animation of floating button


        fab_Normal = (FloatingActionButton) findViewById(R.id.fb_plus);
        fab_Satellite = (FloatingActionButton) findViewById(R.id.fb_favorite);
        fab_Terrain = (FloatingActionButton) findViewById(R.id.fb_help);

        //fb_search = (FloatingActionButton) findViewById(R.id.fb_Search);

        FabOpen = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.moveup);
        FabClose = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.move_down);
        FabClockWise = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.rotate_clockwise);
        FabClockAntiWise = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.rotate_anticlockwise);

        fab_Normal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Normal1(view);

                if (isOpen) {

                    fab_Satellite.startAnimation(FabClose);
                    searchP.startAnimation(FabClose);
                    fab_Terrain.startAnimation(FabClose);
                    nearbyP.startAnimation(FabClose);
                    fab_Normal.startAnimation(FabClockAntiWise);
//                    fab_Satellite.setClickable(false);
//                    fab_Terrain.setClickable(false);
                    isOpen = false;

                } else {
                    fab_Terrain.setVisibility(View.VISIBLE);
                    fab_Satellite.setVisibility(View.VISIBLE);
                    nearbyP.setVisibility(View.VISIBLE);
                    searchP.setVisibility(View.VISIBLE);

                    fab_Satellite.startAnimation(FabOpen);
                    nearbyP.startAnimation(FabOpen);
                    searchP.startAnimation(FabOpen);
                    fab_Terrain.startAnimation(FabOpen);

                    fab_Normal.startAnimation(FabClockWise);
//                    fab_Satellite.setClickable(true);
//                    fab_Terrain.setClickable(true);
                    isOpen = true;
                }
            }
        });

    }


    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {


        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            switch (item.getItemId()) {

                case R.id.garage:

                    return true;

                case R.id.petrolS:
                     startActivity(new Intent(MapMecActivity.this, PetrolStations.class));

                    return true;

                case R.id.spareS:
                    startActivity(new Intent(MapMecActivity.this, SpareShop.class));

                    return true;

                case R.id.feedback:
                            Intent intentMarket = new Intent(MapMecActivity.this, fedbackActivity.class);
                            startActivity(intentMarket);
                            return true;

                case R.id.towtruck:
//                    //mTextMessage.setText(R.string.map);
//                    new Handler().postDelayed(new Runnable() {
//
//                        @Override
//                        public void run() {
                            Intent intentMap = new Intent(MapMecActivity.this, TruckTow.class);
                            startActivity(intentMap);
//                            finish();
//                        }
//                    }, SPLASH_TIME_OUT);
                    return true;


            }
            return false;
        }

    };


    @Override
    public void onMapReady(GoogleMap googleMap) {

        mMap = googleMap;

        //mMap.getUiSettings().setZoomControlsEnabled(true);
        mMap.getUiSettings().setZoomGesturesEnabled(true);
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


    public void Satellite(View view) {
        if (mMap.getMapType() == GoogleMap.MAP_TYPE_NORMAL || mMap.getMapType() == GoogleMap.MAP_TYPE_TERRAIN) {
            mMap.setMapType(GoogleMap.MAP_TYPE_SATELLITE);
        } else {
            mMap.setMapType(GoogleMap.MAP_TYPE_SATELLITE);
        }
    }

    public void Terrain(View view) {
        if (mMap.getMapType() == GoogleMap.MAP_TYPE_NORMAL || mMap.getMapType() == GoogleMap.MAP_TYPE_SATELLITE) {
            mMap.setMapType(GoogleMap.MAP_TYPE_TERRAIN);
        } else {
            mMap.setMapType(GoogleMap.MAP_TYPE_TERRAIN);
        }

    }

    public void Normal1(View view) {
        if (mMap.getMapType() == GoogleMap.MAP_TYPE_TERRAIN || mMap.getMapType() == GoogleMap.MAP_TYPE_SATELLITE) {
            mMap.setMapType(GoogleMap.MAP_TYPE_NORMAL);
        } else {
            mMap.setMapType(GoogleMap.MAP_TYPE_NORMAL);
        }
    }

    public void onSearch(View view) {

    }

    public void nearbyPlaces(View view) {

    }

    public void ToSearch(View view) {
        linearSearch1.setVisibility(View.VISIBLE);
    }

    public void ToNearby(View view) {
        linearSearch1.setVisibility(View.VISIBLE);

    }
}
