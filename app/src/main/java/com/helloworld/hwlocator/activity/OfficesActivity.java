package com.helloworld.hwlocator.activity;

import android.content.Intent;
import android.location.Location;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.LatLngBounds;
import com.google.android.gms.maps.model.MarkerOptions;
import com.helloworld.hwlocator.R;
import com.helloworld.hwlocator.adapter.LocationAdapter;
import com.helloworld.hwlocator.model.LocationObject;
import com.helloworld.hwlocator.model.Singleton;
import com.helloworld.hwlocator.util.Constants;
import com.helloworld.hwlocator.util.DeviceUtils;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;


public class OfficesActivity extends BaseActivity implements OnMapReadyCallback {

    private static String TAG = OfficesActivity.class.getSimpleName();
    private ArrayList<LocationObject> mLocationObjectList;
    private ListView mListView;
    private LocationAdapter mAdapter;
    private GoogleMap mGoogleMap;
    private LatLngBounds.Builder mBuilder;
    private TextView mToolbarTitle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_offices);
        mToolbarTitle = (TextView) findViewById(R.id.toolbar_tv_title);
        mToolbarTitle.setText(R.string.title_activity_offices);
        mListView = (ListView) findViewById(R.id.office_lv_list);
        if(savedInstanceState!=null){
            mLocationObjectList = (ArrayList<LocationObject>) savedInstanceState.getSerializable(Constants.BUNDLE_OFFICE_ACTIVITY_LIST);
        }else{
            mLocationObjectList = Singleton.getInstance().getLocationObjectList();
        }

        if (mLocationObjectList != null) {
            if (isPhoneOnline()) {
                if (latitude != 0 && longitude != 0) {
                    //Sort list from close to further
                    sortListByDistance();
                }
            }
            mAdapter = new LocationAdapter(this, android.R.layout.simple_list_item_1, mLocationObjectList);
            mListView.setAdapter(mAdapter);
            mListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    Intent intent = new Intent(OfficesActivity.this, DetailActivity.class);
                    intent.putExtra(Constants.INTENT_EXTRA_DETAIL_LOCATION, mLocationObjectList.get(position));
                    startActivity(intent);
                }
            });
            SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                    .findFragmentById(R.id.map_fl_container);
            mapFragment.getMapAsync(this);
        }
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        outState.putParcelableArrayList(Constants.BUNDLE_OFFICE_ACTIVITY_LIST, mLocationObjectList);
        super.onSaveInstanceState(outState);
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        if (mLocationObjectList != null && !mLocationObjectList.isEmpty()) {
            mGoogleMap = googleMap;
            int zoomPadding = 150;
            mGoogleMap.clear();
            mBuilder = new LatLngBounds.Builder();
            for (LocationObject locationObject : mLocationObjectList) {
                if (locationObject != null) {
                    double lat = DeviceUtils.getDoubleFromString(locationObject.getLatitude());
                    double lon = DeviceUtils.getDoubleFromString(locationObject.getLongitude());
                    LatLng latLng = new LatLng(lat, lon);
                    mGoogleMap.addMarker(new MarkerOptions().
                            position(latLng).
                            title(locationObject.getName())
                            .icon(BitmapDescriptorFactory.defaultMarker(16))
                            .snippet(locationObject.getCity()));
                    mGoogleMap.moveCamera(CameraUpdateFactory.newLatLng(latLng));
                    mBuilder.include(latLng);
                }
            }
            LatLngBounds bounds = mBuilder.build();
            // Gets screen size
            int width = getResources().getDisplayMetrics().widthPixels;
            int height = getResources().getDisplayMetrics().heightPixels;
            // Calls moveCamera passing screen size as parameters
            mGoogleMap.animateCamera(CameraUpdateFactory.newLatLngBounds(bounds, width, height, zoomPadding));
            mGoogleMap.getUiSettings().setCompassEnabled(true);
            mGoogleMap.getUiSettings().setZoomControlsEnabled(true);
        }
    }

    private void setDistance() {
        Location userLocation = new Location("User");
        userLocation.setLatitude(latitude);
        userLocation.setLongitude(longitude);
        for (LocationObject locationObject : mLocationObjectList) {
            if (locationObject != null) {
                Location officeLocation = new Location("Office");
                officeLocation.setLatitude(DeviceUtils.getDoubleFromString(locationObject.getLatitude()));
                officeLocation.setLongitude(DeviceUtils.getDoubleFromString(locationObject.getLongitude()));
                float distanceBetween = userLocation.distanceTo(officeLocation) / 1000;//KM
                locationObject.setDistance(DeviceUtils.getStringFromFloat(distanceBetween));
            }
        }

    }

    private void sortListByDistance() {
        setDistance();
        //Collections.sort();


        Collections.sort(mLocationObjectList, new Comparator<LocationObject>() {
            @Override
            public int compare(LocationObject lhs, LocationObject rhs) {
                if (lhs != null && rhs != null) {
                    if (DeviceUtils.getIntFromString(lhs.getDistance()) < DeviceUtils.getIntFromString(rhs.getDistance())) {
                        return -1;
                    }
                    if (DeviceUtils.getIntFromString(lhs.getDistance()) > DeviceUtils.getIntFromString(rhs.getDistance())) {
                        return 1;
                    }
                }
                return 0;
            }
        });
    }


}
