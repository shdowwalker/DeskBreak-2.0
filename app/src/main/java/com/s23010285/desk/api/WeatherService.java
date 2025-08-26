package com.s23010285.desk.api;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

/**
 * Weather API service interface for DeskBreak App
 * Defines endpoints for retrieving weather information
 * This class is like a weather forecaster that helps users plan outdoor workouts
 */
public interface WeatherService {
    
    /**
     * Get current weather for a specific location
     * This method retrieves the current weather conditions for a given city
     * @param city The name of the city to get weather for (e.g., "London", "New York")
     * @param apiKey The API key required to access the weather service
     * @return A call object that will contain the weather response data
     */
    @GET("weather")
    Call<WeatherResponse> getCurrentWeather(
        @Query("q") String city,
        @Query("appid") String apiKey
    );
    
    /**
     * Get current weather using GPS coordinates
     * This method retrieves the current weather conditions for a specific location using latitude and longitude
     * @param lat The latitude coordinate of the location
     * @param lon The longitude coordinate of the location
     * @param apiKey The API key required to access the weather service
     * @return A call object that will contain the weather response data
     */
    @GET("weather")
    Call<WeatherResponse> getCurrentWeatherByCoordinates(
        @Query("lat") double lat,
        @Query("lon") double lon,
        @Query("appid") String apiKey
    );
    
    /**
     * Get weather forecast for a specific location
     * This method retrieves a 5-day weather forecast for a given city
     * @param city The name of the city to get forecast for (e.g., "London", "New York")
     * @param apiKey The API key required to access the weather service
     * @return A call object that will contain the forecast response data
     */
    @GET("forecast")
    Call<WeatherResponse> getWeatherForecast(
        @Query("q") String city,
        @Query("appid") String apiKey
    );
    
    /**
     * Get weather forecast using GPS coordinates
     * This method retrieves a 5-day weather forecast for a specific location using latitude and longitude
     * @param lat The latitude coordinate of the location
     * @param lon The longitude coordinate of the location
     * @param apiKey The API key required to access the weather service
     * @return A call object that will contain the forecast response data
     */
    @GET("forecast")
    Call<WeatherResponse> getWeatherForecastByCoordinates(
        @Query("lat") double lat,
        @Query("lon") double lon,
        @Query("appid") String apiKey
    );
}
