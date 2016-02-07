package com.helloworld.hwlocator.network;

import com.helloworld.hwlocator.model.LocationsResponse;
import com.helloworld.hwlocator.util.Constants;

import retrofit.Callback;
import retrofit.ErrorHandler;
import retrofit.RestAdapter;
import retrofit.http.GET;
import retrofit.http.Path;

/**
 * Created by snakelogan on 2/5/16.
 */
public class RetrofitManager {

    private static final RestAdapter REST_ADAPTER = new RestAdapter.Builder()
            .setEndpoint(Constants.API_URL_BASE)
            .setLogLevel(RestAdapter.LogLevel.FULL.FULL)
            .setErrorHandler(ErrorHandler.DEFAULT)
            .build();

    private static final Retrofit_Interface api_interface = REST_ADAPTER.create(Retrofit_Interface.class);

    public static Retrofit_Interface getService() {
        return api_interface;
    }

    public interface Retrofit_Interface {

        @GET("/{path}")
        void getLocations(@Path(value = "path", encode = false) String path, Callback<LocationsResponse> response);

        @GET("/{path}")
        LocationsResponse getLoca(@Path(value = "path") String path);

    }
}