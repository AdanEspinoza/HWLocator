package com.helloworld.hwlocator.activity;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;

import com.helloworld.hwlocator.database.LocationDBHelper;
import com.helloworld.hwlocator.util.Constants;

public class BaseActivity extends AppCompatActivity implements LocationListener {

    protected LocationManager locationManager;
    protected Location location;
    protected double latitude=0, longitude=0;
    protected LocationDBHelper mLocationDBHelper;
    private OnNotifyLocationPermissionListener mListener;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mLocationDBHelper = new LocationDBHelper(this);
        if(isPhoneOnline()) {
            locationManager = (LocationManager) this.getSystemService(Context.LOCATION_SERVICE);
            requestPermissionLocation();
        }
    }

    protected boolean isPhoneOnline() {
        ConnectivityManager cm = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetwork = cm.getActiveNetworkInfo();
        return activeNetwork != null && activeNetwork.isConnectedOrConnecting();
    }

    protected void requestPermissionLocation() {
        if(isPhoneOnline()) {
            if (Build.VERSION.SDK_INT >= 23 &&
                    ContextCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED &&
                    ContextCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                ActivityCompat.requestPermissions(this,
                        new String[]{Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION}, Constants.PERMISSION_LOCATION_REQUEST);
                return;
            }

            locationManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, 0, 0, this);
            location = locationManager.getLastKnownLocation(LocationManager.NETWORK_PROVIDER);
            if (location != null) {
                latitude = location.getLatitude();
                longitude = location.getLongitude();
            }
        }
    }

    protected boolean hasPermissonLocation() {
        if(isPhoneOnline()) {
            if (Build.VERSION.SDK_INT >= 23 &&
                    ContextCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED &&
                    ContextCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                return false;
            }
            return true;
        }
        return false;
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions,
                                           int[] grantResults) {
        switch (requestCode) {
            case Constants.PERMISSION_LOCATION_REQUEST: {
                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    requestPermissionLocation();
                    if(mListener!=null) {
                        mListener.onUpdateList();
                    }
                }
                break;
            }
        }
    }

    @Override
    public void onLocationChanged(Location location) {

    }

    @Override
    public void onProviderDisabled(String provider) {

    }

    @Override
    public void onProviderEnabled(String provider) {

    }

    @Override
    public void onStatusChanged(String provider, int status, Bundle extras) {

    }

    public void setOnNotifyLocationListener(OnNotifyLocationPermissionListener listener){
        mListener = listener;
    }

    public interface OnNotifyLocationPermissionListener{
        void onUpdateList();
    }

}