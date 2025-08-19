package com.s23010285.desk.api;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface WeatherService {
    
    @GET("weather")
    Call<WeatherResponse> getCurrentWeather(
        @Query("lat") double latitude,
        @Query("lon") double longitude,
        @Query("appid") String apiKey,
        @Query("units") String units
    );
    
    @GET("forecast")
    Call<WeatherForecastResponse> getWeatherForecast(
        @Query("lat") double latitude,
        @Query("lon") double longitude,
        @Query("appid") String apiKey,
        @Query("units") String units
    );
}
