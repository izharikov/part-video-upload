package com.devnock.video_streamer.networks;

import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by Sergey Mochulsky on 18.09.2017.
 */


public class ApiUtils {

    private static final String BASE_URL = "http://192.168.43.112/";
    public static final String VIDEO_BASE_URL = BASE_URL;

    public static <S> S createService(Class<S> serviceClass) {
        Retrofit.Builder retrofitBuilder = new Retrofit.Builder()
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .baseUrl(getBaseUrl());

        return retrofitBuilder.build().create(serviceClass);
    }

    private static String getBaseUrl() {
        return BASE_URL;
    }
}