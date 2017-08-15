package com.skripsigg.heyow.ui.mapdetails;

import android.Manifest;
import android.content.pm.PackageManager;
import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;
import android.location.Location;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.skripsigg.heyow.R;
import com.skripsigg.heyow.utils.others.Constants;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

public class MapDetailsActivity extends AppCompatActivity implements
        OnMapReadyCallback {

    private final String TAG = getClass().getSimpleName();

    /**
     * UI Widgets
     */
    private Toolbar mapsChooseToolbar;

    /**
     * Provides google maps service
     */
    protected GoogleMap googleMap;

    /**
     * Represents a current lat lng.
     */
    protected LatLng currentLatLng;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_map_choose);
        overridePendingTransition(R.anim.pull_in_right, R.anim.push_out_left);

        mapsChooseToolbar = (Toolbar) findViewById(R.id.maps_choose_toolbar);

        setSupportActionBar(mapsChooseToolbar);
        setTitle(getString(R.string.location_detail));

        initToolbar();
        initMapsFragment();
    }

    private void initToolbar() {
        mapsChooseToolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finishActivity();
            }
        });

        Drawable navigationIcon = mapsChooseToolbar.getNavigationIcon();
        Drawable overflowIcon = mapsChooseToolbar.getOverflowIcon();
        if (navigationIcon != null && overflowIcon != null) {
            navigationIcon.mutate();
            navigationIcon.setColorFilter(
                    ContextCompat.getColor(this, R.color.colorPrimary),
                    PorterDuff.Mode.SRC_ATOP);

            overflowIcon.mutate();
            overflowIcon.setColorFilter(
                    ContextCompat.getColor(this, R.color.colorPrimary),
                    PorterDuff.Mode.SRC_ATOP);
        }
    }

    private void initMapsFragment() {
        MapFragment mapFragment = (MapFragment)
                getFragmentManager().findFragmentById(R.id.maps_fragment);
        mapFragment.getMapAsync(this);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_map_choose, menu);

        for (int i = 0; i < menu.size(); i++) {
            Drawable menuIcon = menu.getItem(i).getIcon();
            if (menuIcon != null) {
                menuIcon.mutate();
                menuIcon.setColorFilter(
                        ContextCompat.getColor(this, R.color.colorPrimary),
                        PorterDuff.Mode.SRC_ATOP);
            }
        }

        return true;
    }

    @Override
    public void onBackPressed() {
        finishActivity();
    }

    @Override
    public void onMapReady(GoogleMap gmaps) {
        googleMap = gmaps;
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) !=
                PackageManager.PERMISSION_GRANTED &&
                ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) !=
                        PackageManager.PERMISSION_GRANTED) {
            Toast.makeText(this, "Permission denied!", Toast.LENGTH_SHORT).show();
        } else {
            double receiveLatitude = Double.parseDouble(getIntent().getExtras().getString(Constants.KEY_LATITUDE_DETAIL));
            double receiveLongitude = Double.parseDouble(getIntent().getExtras().getString(Constants.KEY_LONGITUDE_DETAIL));
            currentLatLng = new LatLng(receiveLatitude, receiveLongitude);

            googleMap.setMyLocationEnabled(true);
            googleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(currentLatLng, 15));
            googleMap.addMarker(new MarkerOptions()
                    .position(currentLatLng));
        }
    }

    /**
     * Finish this activity
     */
    private void finishActivity() {
        finish();
        overridePendingTransition(R.anim.pull_in_left, R.anim.push_out_right);
    }
}
