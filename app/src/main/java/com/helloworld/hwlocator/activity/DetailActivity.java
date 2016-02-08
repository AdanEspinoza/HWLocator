package com.helloworld.hwlocator.activity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.helloworld.hwlocator.R;
import com.helloworld.hwlocator.model.LocationObject;
import com.helloworld.hwlocator.util.Constants;
import com.helloworld.hwlocator.util.DeviceUtils;
import com.squareup.picasso.Picasso;

public class DetailActivity extends BaseActivity implements View.OnClickListener {

    private static final String TAG = DetailActivity.class.getSimpleName();

    private LocationObject mLocationObject;
    private ImageView mImageOffice, mStaticMap;
    private TextView mName, mAddress, mDistance, mToolbarTitle;
    private Button mBtnCall, mBtnDirections;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        mToolbarTitle = (TextView) findViewById(R.id.toolbar_tv_title);
        mStaticMap = (ImageView) findViewById(R.id.detail_iv_static_map);
        mImageOffice = (ImageView) findViewById(R.id.detail_iv_image);
        mName = (TextView) findViewById(R.id.detail_tv_name);
        mAddress = (TextView) findViewById(R.id.detail_tv_address);
        mDistance = (TextView) findViewById(R.id.detail_tv_distance);
        mBtnCall = (Button) findViewById(R.id.detail_btn_call);
        mBtnCall.setOnClickListener(this);
        mBtnDirections = (Button) findViewById(R.id.detail_btn_directions);
        mBtnDirections.setOnClickListener(this);

        if (getIntent() != null) {
            mLocationObject = (LocationObject) getIntent().getParcelableExtra(Constants.INTENT_EXTRA_DETAIL_LOCATION);
        } else if(savedInstanceState!=null) {
            mLocationObject = (LocationObject) savedInstanceState.getSerializable(Constants.BUNDLE_DETAIL_ACTIVITY);
        }
        mToolbarTitle.setText(mLocationObject.getName());
        String url = DeviceUtils.validateUrl(mLocationObject.getOfficeImage());
        Uri uri = Uri.parse(url);
        Picasso.with(this).
                load(uri).
                fit().
                placeholder(R.drawable.hw_splash).
                into(mImageOffice);

        mName.setText(mLocationObject.getName());
        mAddress.setText(DeviceUtils.getFullAddress(mLocationObject));
        mDistance.setText(mLocationObject.getDistance()+" KM");

        double latitude = DeviceUtils.getDoubleFromString(mLocationObject.getLatitude());
        double longitude = DeviceUtils.getDoubleFromString(mLocationObject.getLongitude());
        StringBuilder sb = new StringBuilder();
        sb.append("https://maps.googleapis.com/maps/api/staticmap?center=")
                .append(latitude)
                .append(",")
                .append(longitude)
                .append("&zoom=15&size=800x800&markers=color:0xff500e%7C")
                .append(latitude)
                .append(",")
                .append(longitude);
        uri = Uri.parse(sb.toString());
        Log.d(TAG, sb.toString());
        Picasso.with(this).
                load(uri).
                fit().
                placeholder(R.drawable.hw_splash).
                into(mStaticMap);

    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        outState.putParcelable(Constants.BUNDLE_DETAIL_ACTIVITY, mLocationObject);
        super.onSaveInstanceState(outState);
    }

    @Override
    public void onClick(View v) {
        int id = v.getId();
        Intent intent;
        switch (id) {
            case R.id.detail_btn_call:
                intent = new Intent(Intent.ACTION_DIAL, Uri.parse("tel:" + mLocationObject.getPhone().trim()));
                startActivity(intent);
                break;
            case R.id.detail_btn_directions:
                if (hasPermissonLocation()) {
                    intent = new Intent(android.content.Intent.ACTION_VIEW,
                            Uri.parse("http://maps.google.com/maps?saddr=" + latitude + "," + longitude + "&daddr=" + mLocationObject.getLatitude() + "," + mLocationObject.getLongitude()));
                } else {
                    intent = new Intent(android.content.Intent.ACTION_VIEW,
                            Uri.parse("http://maps.google.com/maps?daddr=" + mLocationObject.getLatitude() + "," + mLocationObject.getLongitude()));
                }
                startActivity(intent);
                break;
        }
    }
}
