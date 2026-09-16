package com.example.lab2_iot_20223291; // Ajusta a tu paquete

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface OmdbApiService {

    @GET("/")
    Call<PeliculaBean> obtenerPelicula(
            @Query("apikey") String apiKey,
            @Query("i") String imdbId
    );

}