package com.example.cinemaapp.Movie;

import retrofit2.Call;
import retrofit2.http.*;

import java.util.List;

public interface MoviesCRUD {
    @GET("/movie/")
    Call<List<Movie>> getMovies(@Header("Authorization") String accessKey);

    @POST("/movie/")
    Call<Movie> createMovie(@Header("Authorization") String accessKey, @Body Movie movie);

    @DELETE("/movie/{id}")
    Call<Void> deleteMovie(@Header("Authorization") String accessKey, @Path("id") String id);

    @POST("/movie/{id}/images")
    Call<MovieUrlDto> addImage(@Header("Authorization") String accessKey, @Body MovieImageDto movieImageDto,
                        @Path("id") String id);

    @DELETE("/movie/{id}/images/{imageSelector}")
    Call<Movie> deleteMovieImage(@Header("Authorization") String accessKey, @Path("id") String id,
                                @Path("imageSelector") String imageSelector);
}


