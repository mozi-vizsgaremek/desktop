package com.example.cinemaapp.Auditorium;

import retrofit2.Call;
import retrofit2.http.*;

import java.util.List;

public interface AuditoriumCRUD {
    @GET("/auditorium/")
    Call<List<Auditorium>> getAuditoriums(@Header("Authorization") String accessKey);
    @POST("/auditorium/")
    Call<Auditorium> createAuditorium(@Header("Authorization") String accessKey, @Body Auditorium auditorium);
    @DELETE("/auditorium/{id}")
    Call<Void> deleteAuditorium(@Header("Authorization") String accessKey, @Path("id") String id);
}
