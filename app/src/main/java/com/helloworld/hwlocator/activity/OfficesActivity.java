package com.helloworld.hwlocator.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
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

import java.util.List;


public class OfficesActivity extends FragmentActivity implements OnMapReadyCallback {

    private static String TAG = OfficesActivity.class.getSimpleName();
    private List<LocationObject> mLocationObjectList;
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
        mLocationObjectList = Singleton.getInstance().getLocationObjectList();
        if (mLocationObjectList != null) {
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
    public void onMapReady(GoogleMap googleMap) {
        mGoogleMap = googleMap;
        int zoomPadding = 150;
        mGoogleMap.clear();
        mBuilder = new LatLngBounds.Builder();
        for (LocationObject locationObject : mLocationObjectList) {
            if(locationObject != null) {
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
