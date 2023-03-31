package com.example.cinemaapp;

import retrofit2.Call;
import retrofit2.http.*;

import java.util.List;

public interface UsersCRUD {
    @GET("/auth/admin/")
    Call<List<Person>> getPeople(@Header("Authorization") String accessKey);

    @POST("/auth/admin")
    Call<Person> createPerson(@Body Person person);

    @PUT("/auth/admin/{id}")
    Call<Person> updatePerson(@Path("id") int id, @Body Person person);

    @DELETE("/auth/admin/{id}")
    Call<Void> deletePerson(@Path("id") int id);
}
