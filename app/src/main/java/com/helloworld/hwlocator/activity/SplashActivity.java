package com.helloworld.hwlocator.activity;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import com.helloworld.hwlocator.R;
import com.helloworld.hwlocator.database.LocationDBHelper;
import com.helloworld.hwlocator.model.LocationObject;
import com.helloworld.hwlocator.model.LocationsResponse;
import com.helloworld.hwlocator.model.Singleton;
import com.helloworld.hwlocator.network.RetrofitManager;
import com.helloworld.hwlocator.util.Constants;

import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import retrofit.Callback;
import retrofit.RetrofitError;
import retrofit.client.Response;

public class SplashActivity extends AppCompatActivity {

    private static String TAG = SplashActivity.class.getSimpleName();
    private static final long SPLASH_SCREEN_DELAY = 3000;
    private LocationDBHelper mLocationDBHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        setContentView(R.layout.activity_splash);

        mLocationDBHelper = new LocationDBHelper(this);

        if (isPhoneOnline()) {
            //go to JSON
            try {
                Thread thread = new Thread(new Runnable() {
                    @Override
                    public void run() {
                        getDataFromJson();
                    }
                });
                thread.start();
                thread.join();
            }catch (InterruptedException e){
                Log.d(TAG, e.getMessage());
            }

        } else if (LocationDBHelper.doesDatabaseExist(SplashActivity.this)) {
            getDataFromDB();

        } else {
            showAlertDialog();
        }


        TimerTask task = new TimerTask() {
            @Override
            public void run() {
                Intent intent = new Intent(SplashActivity.this, OfficesActivity.class);
                startActivity(intent);
                finish();
            }
        };
        Timer timer = new Timer();
        timer.schedule(task, SPLASH_SCREEN_DELAY);
    }

//    private boolean executeLogic() {
//        if (isPhoneOnline()) {
//            //go to JSON
//            getDataFromServer();
//            return true;
//        } else if (LocationDBHelper.doesDatabaseExist(this)) {
//            getDataFromDB();
//            return true;
//        } else return false;
//    }


    private boolean isPhoneOnline() {
        ConnectivityManager cm = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetwork = cm.getActiveNetworkInfo();
        return activeNetwork != null && activeNetwork.isConnectedOrConnecting();
    }

    private void getDataFromServer() {
        String path = Constants.API_URL_LOCATIONS;
        RetrofitManager.getService().getLocations(path, new Callback<LocationsResponse>() {
            @Override
            public void success(LocationsResponse locationsResponse, Response response) {
                Singleton.getInstance().setLocationObjectList(locationsResponse.getLocationObjects());
                if (!LocationDBHelper.doesDatabaseExist(SplashActivity.this)) {
                    //insert in database first time
                    insertDataDB();
                }
                Log.d(TAG, "Success");
            }

            @Override
            public void failure(RetrofitError error) {
                Log.d(TAG, error.getMessage());
            }
        });
    }

    private void getDataFromJson() {
        String path = Constants.API_URL_LOCATIONS;
        LocationsResponse locationsResponse = RetrofitManager.getService().getLoca(path);
        Singleton.getInstance().setLocationObjectList(locationsResponse.getLocationObjects());
        if (!LocationDBHelper.doesDatabaseExist(SplashActivity.this)) {
            //insert in database first time
            insertDataDB();
        }
        Log.d(TAG, "Success");
    }

    private void insertDataDB() {
        List<LocationObject> locationObjectList = Singleton.getInstance().getLocationObjectList();
        if (locationObjectList.size() > 0) {
            for (LocationObject locationObject : locationObjectList) {
                if (locationObject != null) {
                    mLocationDBHelper.insertLocation(locationObject);
                }
            }
        }
    }

    private void getDataFromDB() {
        Singleton.getInstance().setLocationObjectList(mLocationDBHelper.getAllLocations());
    }

    private void showAlertDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle(getString(R.string.alert_title_error))
                .setMessage(getString(R.string.alert_message_internet_error))
                .setCancelable(false)
                .setPositiveButton(getString(R.string.ok_button), new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        finish();
                    }
                });
        builder.create().show();
    }

    private class LoadData extends AsyncTask<Void, Void, Boolean> {

        @Override
        protected Boolean doInBackground(Void... arg0) {
            if (isPhoneOnline()) {
                //go to JSON
                getDataFromJson();
                return true;
            } else if (LocationDBHelper.doesDatabaseExist(SplashActivity.this)) {
                getDataFromDB();
                return true;
            } else return false;
        }

        @Override
        protected void onPostExecute(Boolean result) {
            super.onPostExecute(result);
            if (!result) {
                showAlertDialog();
            }
        }

    }
}
