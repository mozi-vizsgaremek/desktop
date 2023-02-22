package com.example.cinemaapp.rest;

// import Retrofit

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class RetrofitSingleton {
    private static Retrofit retrofitInstance = null;

    public static Retrofit getInstance() {
        if (retrofitInstance == null)
            retrofitInstance = new Retrofit.Builder()
                    .baseUrl("https://vr-api.leventea.hu/")
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();

        return retrofitInstance;
    }
}
