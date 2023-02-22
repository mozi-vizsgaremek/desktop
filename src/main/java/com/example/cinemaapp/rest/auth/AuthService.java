package com.example.cinemaapp.rest.auth;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;

public interface AuthService {
    @POST("/auth/login")
    Call<TokenPair> login(@Body() LoginRequest req);

    @POST("/auth/refresh")
    Call<String> refresh(@Body() RefreshToken req);
}
