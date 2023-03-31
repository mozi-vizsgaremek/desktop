package com.example.cinemaapp.rest;

// import Retrofit

import com.example.cinemaapp.adapter.LocalDateAdapter;
import com.example.cinemaapp.adapter.LocalDateTimeAdapter;
import com.google.gson.GsonBuilder;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import java.time.LocalDate;
import java.time.LocalDateTime;

public class RetrofitSingleton {
    private static Retrofit retrofitInstance = null;

    public static Retrofit getInstance() {
        if (retrofitInstance == null) {
            var gsonBuilder = new GsonBuilder();
            gsonBuilder.registerTypeAdapter(LocalDate.class, new LocalDateAdapter());
            gsonBuilder.registerTypeAdapter(LocalDateTime.class, new LocalDateTimeAdapter());

            var gsonInstance = gsonBuilder.create();

            retrofitInstance = new Retrofit.Builder()
                    .baseUrl("https://vr-api.leventea.hu/")
                    .addConverterFactory(GsonConverterFactory.create(gsonInstance))
                    .build();
        }

        return retrofitInstance;
    }
}
