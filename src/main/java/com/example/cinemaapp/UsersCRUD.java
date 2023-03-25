package com.example.cinemaapp;

import retrofit2.Call;
import retrofit2.http.*;

import java.util.List;

public interface UsersCRUD {
    @GET("items")
    Call<List<Person>> getItems();

    @POST("items")
    Call<Person> createItem(@Body Person person);

    @PUT("items/{id}")
    Call<Person> updateItem(@Path("id") int id, @Body Person person);

    @DELETE("items/{id}")
    Call<Void> deleteItem(@Path("id") int id);
}
