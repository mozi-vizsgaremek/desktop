package com.example.cinemaapp.rest.auth;

import retrofit2.Call;
import retrofit2.http.*;

import java.util.List;

public interface AuthService {
    @POST("/auth/login")
    Call<TokenPair> login(@Body() LoginRequest req);

    @POST("/auth/refresh")
    Call<RefreshDto> refresh(@Body() RefreshToken req);

    @GET("/auth/admin/")
    Call<List<User>> getUsers(@Header("Authorization") String accessKey);

    @GET("/auth/admin/{id}")
    Call<User> getUser(@Header("Authorization") String accessKey,
                       @Path(value = "id", encoded = true) String userId);
}
