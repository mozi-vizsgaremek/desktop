package com.example.cinemaapp.Movie;

import com.example.cinemaapp.Auditorium.Auditorium;
import com.example.cinemaapp.Person.Person;
import retrofit2.Call;
import retrofit2.http.*;

import java.util.List;

public interface MoviesCRUD {
    @GET("/movie/")
    Call<List<Person>> getMovies(@Header("Authorization") String accessKey);

    @POST("/movie/")
    Call<List<Person>> createMovie(@Header("Authorization") String accessKey, @Body Movie movie);

    @DELETE("/movie/{id}")
    Call<Void> deleteMovie(@Header("Authorization") String accessKey, @Path("id") String id);

    @POST("/movie/{id}/images")
    Call<List<Person>> createImage(@Header("Authorization") String accessKey, @Body Movie movie, @Path("id") String id);
    //might need to change @Body Movie to Image

    @DELETE("/movie/{id}/images/{imageSelector}")
    Call<Void> deleteMovieImage(@Header("Authorization") String accessKey, @Path("id") String id,
                                @Path("imageSelector") String imageSelector);
}


