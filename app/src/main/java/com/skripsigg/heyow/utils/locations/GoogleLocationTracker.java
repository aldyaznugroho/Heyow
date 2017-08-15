package com.skripsigg.heyow.utils.locations;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentSender;
import android.content.pm.PackageManager;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.provider.Settings;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.widget.Toast;

import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.PendingResult;
import com.google.android.gms.common.api.Result;
import com.google.android.gms.common.api.ResultCallback;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.location.LocationSettingsRequest;
import com.google.android.gms.location.LocationSettingsResult;
import com.google.android.gms.location.LocationSettingsStates;
import com.google.android.gms.location.LocationSettingsStatusCodes;
import com.skripsigg.heyow.R;

/**
 * Created by Aldyaz on 12/18/2016.
 */

/**
 * For getting latitude and longitude using Google API
 */
public class GoogleLocationTracker implements ResultCallback<LocationSettingsResult> {
    private final String TAG = getClass().getSimpleName();
    private static final int REQUEST_CHECK_SETTINGS = 100;

    // The minimum distance to change Updates in meters
    private static final long MIN_DISTANCE_CHANGE_FOR_UPDATES = 10; // 10 meters

    // The minimum time between updates in milliseconds
    private static final long MIN_TIME_BW_UPDATES = 1000 * 60; // 1 minute

    private Context context;
    private GoogleApiClient googleApiClient;
    private Location lastLocation;
    private LocationManager locationManager;

    // Represent latitude and longitude
    private double latitude;
    private double longitude;

    // flag for GPS status
    private boolean isGPSEnabled = false;

    // flag for network status
    private boolean isNetworkEnabled = false;

    private boolean canGetLocation = false;

    public GoogleLocationTracker(Context context, Location lastLocation) {
        this.context = context;
        this.lastLocation = lastLocation;
        getLastLocation();
    }

    public Location getLastLocation() {
        try {
            locationManager = (LocationManager)
                    context.getSystemService(Context.LOCATION_SERVICE);
            isGPSEnabled = locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER);

            if (!isGPSEnabled) {
            } else {
                this.canGetLocation = true;
                getLocationByGPS();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return lastLocation;
    }

    public void getLocationByNetwork() {
        if (ActivityCompat.checkSelfPermission(context, Manifest.permission.ACCESS_FINE_LOCATION) !=
                PackageManager.PERMISSION_GRANTED &&
                ActivityCompat.checkSelfPermission(context, Manifest.permission.ACCESS_COARSE_LOCATION) !=
                        PackageManager.PERMISSION_GRANTED) {
            Toast.makeText(context, context.getString(R.string.location_permission_denied), Toast.LENGTH_SHORT).show();
        } else {
            lastLocation = LocationServices.FusedLocationApi.getLastLocation(googleApiClient);
            if (googleApiClient.isConnected() && lastLocation != null) {
                if (!Geocoder.isPresent()) {
                    Toast.makeText(context, context.getString(R.string.no_geocoder_available), Toast.LENGTH_SHORT).show();
                }

                latitude = lastLocation.getLatitude();
                longitude = lastLocation.getLongitude();
            }
        }
    }

    public void getLocationByGPS() {
        if (lastLocation == null) {
            if (ActivityCompat.checkSelfPermission(context, Manifest.permission.ACCESS_FINE_LOCATION) !=
                    PackageManager.PERMISSION_GRANTED &&
                    ActivityCompat.checkSelfPermission(context, Manifest.permission.ACCESS_COARSE_LOCATION) !=
                            PackageManager.PERMISSION_GRANTED) {
                Toast.makeText(context, context.getString(R.string.location_permission_denied), Toast.LENGTH_SHORT).show();
            } else {
                locationManager.requestLocationUpdates(
                        LocationManager.GPS_PROVIDER,
                        MIN_TIME_BW_UPDATES,
                        MIN_DISTANCE_CHANGE_FOR_UPDATES,
                        (LocationListener) context);
                Log.e(TAG, "GPS Enabled");
                if (locationManager != null) {
                    lastLocation = locationManager
                            .getLastKnownLocation(LocationManager.GPS_PROVIDER);
                    if (lastLocation != null) {
                        latitude = lastLocation.getLatitude();
                        longitude = lastLocation.getLongitude();
                    }
                }
            }
        }
    }

    public void activateGPS() {
        LocationRequest locationRequest = LocationRequest.create();
        locationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);
        locationRequest.setInterval(30 * 1000);
        locationRequest.setFastestInterval(5 * 1000);

        LocationSettingsRequest.Builder requestBuilder = new LocationSettingsRequest.Builder()
                .addLocationRequest(locationRequest);
        requestBuilder.setAlwaysShow(true);

        PendingResult<LocationSettingsResult> result = LocationServices.SettingsApi
                .checkLocationSettings(googleApiClient, requestBuilder.build());
        result.setResultCallback(this);
    }

    public boolean isCanGetLocation() {
        return canGetLocation;
    }

    /**
     * Method to get latitude
     * @return latitude
     */
    public double getLatitude() {
        if (lastLocation != null) {
            latitude = lastLocation.getLatitude();
        }
        return latitude;
    }

    /**
     * Method to get longitude
     * @return longitude
     */
    public double getLongitude() {
        if (lastLocation != null) {
            longitude = lastLocation.getLongitude();
        }
        return longitude;
    }

    public boolean isNetworkEnabled() {
        return isNetworkEnabled;
    }

    public boolean isGPSEnabled() {
        return isGPSEnabled;
    }

    public void showLocationsSettingsDialog() {
        AlertDialog.Builder locationDialog = new AlertDialog.Builder(context);
        locationDialog.setTitle("GPS Settings");
        locationDialog.setMessage("GPS is not enabled. Do you want change location settings?");
        locationDialog.setPositiveButton("Settings", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                Intent settingsIntent = new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS);
                context.startActivity(settingsIntent);
            }
        });
        locationDialog.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                dialogInterface.cancel();
            }
        });
        locationDialog.show();
    }

    @Override
    public void onResult(@NonNull LocationSettingsResult locationSettingsResult) {
        Status status = locationSettingsResult.getStatus();
        LocationSettingsStates locationSettingsStates = locationSettingsResult.getLocationSettingsStates();

        switch (status.getStatusCode()) {
            case LocationSettingsStatusCodes.SUCCESS:
                // No dialog needed.
                Log.e(TAG, "Success enable");
                break;
            case LocationSettingsStatusCodes.RESOLUTION_REQUIRED:
                // Location settings are not satisfied. Show the user a dialog.
                try {
                    status.startResolutionForResult((Activity) context, REQUEST_CHECK_SETTINGS);
                } catch (IntentSender.SendIntentException e) {
                    e.printStackTrace();
                }
                break;
            case LocationSettingsStatusCodes.SETTINGS_CHANGE_UNAVAILABLE:
                // Location settings are unavailable so not possible to show any dialog
                break;
        }
    }
}
