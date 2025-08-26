package com.s23010285.desk.api;

import com.google.gson.annotations.SerializedName;
import java.util.List;

/**
 * Weather response data model for DeskBreak App
 * Contains weather information received from the weather API
 * This class is like a weather report that stores all the weather details
 */
public class WeatherResponse {
    
    // These variables store the main weather information
    // coord stores the geographic coordinates of the location
    @SerializedName("coord")
    public Coordinates coordinates;
    
    // weather stores an array of weather conditions (usually just one element)
    @SerializedName("weather")
    public WeatherCondition[] weatherConditions;
    
    // base stores the internal parameter (usually "stations")
    @SerializedName("base")
    public String base;
    
    // main stores the main weather measurements like temperature and humidity
    @SerializedName("main")
    public MainWeather mainWeather;
    
    // visibility stores how far you can see in meters
    @SerializedName("visibility")
    public int visibility;
    
    // wind stores wind speed and direction information
    @SerializedName("wind")
    public Wind wind;
    
    // clouds stores cloud coverage information
    @SerializedName("clouds")
    public Clouds clouds;
    
    // dt stores when the weather data was calculated (Unix timestamp)
    @SerializedName("dt")
    public long dateTime;
    
    // sys stores system information like country and sunrise/sunset times
    @SerializedName("sys")
    public SystemInfo systemInfo;
    
    // timezone stores the timezone offset in seconds from UTC
    @SerializedName("timezone")
    public int timezone;
    
    // id stores the city ID from the weather service
    @SerializedName("id")
    public int cityId;
    
    // name stores the city name
    @SerializedName("name")
    public String cityName;
    
    // cod stores the HTTP response code
    @SerializedName("cod")
    public int responseCode;
    
    // Nested classes to organize the weather data
    
    /**
     * Geographic coordinates class
     * This class stores the latitude and longitude of a location
     */
    public static class Coordinates {
        // latitude stores the north-south position (positive = north, negative = south)
        @SerializedName("lat")
        public double latitude;
        
        // longitude stores the east-west position (positive = east, negative = west)
        @SerializedName("lon")
        public double longitude;
    }
    
    /**
     * Weather condition class
     * This class stores information about the current weather conditions
     */
    public static class WeatherCondition {
        // id stores a unique weather condition identifier
        @SerializedName("id")
        public int weatherId;
        
        // main stores the main weather category (e.g., "Clear", "Clouds", "Rain")
        @SerializedName("main")
        public String mainCondition;
        
        // description stores a detailed description of the weather
        @SerializedName("description")
        public String description;
        
        // icon stores the weather icon code for displaying weather graphics
        @SerializedName("icon")
        public String iconCode;
    }
    
    /**
     * Main weather measurements class
     * This class stores the primary weather measurements like temperature and humidity
     */
    public static class MainWeather {
        // temp stores the current temperature in the specified units
        @SerializedName("temp")
        public double temperature;
        
        // feels_like stores how warm or cold it actually feels (considering wind, humidity, etc.)
        @SerializedName("feels_like")
        public double feelsLike;
        
        // temp_min stores the minimum temperature for the day
        @SerializedName("temp_min")
        public double temperatureMin;
        
        // temp_max stores the maximum temperature for the day
        @SerializedName("temp_max")
        public double temperatureMax;
        
        // pressure stores the atmospheric pressure in hectopascals (hPa)
        @SerializedName("pressure")
        public int pressure;
        
        // humidity stores the relative humidity as a percentage (0-100)
        @SerializedName("humidity")
        public int humidity;
        
        // sea_level stores the atmospheric pressure at sea level in hectopascals
        @SerializedName("sea_level")
        public int seaLevelPressure;
        
        // grnd_level stores the atmospheric pressure at ground level in hectopascals
        @SerializedName("grnd_level")
        public int groundLevelPressure;
    }
    
    /**
     * Wind information class
     * This class stores wind speed and direction information
     */
    public static class Wind {
        // speed stores the wind speed in the specified units
        @SerializedName("speed")
        public double speed;
        
        // deg stores the wind direction in degrees (0 = north, 90 = east, 180 = south, 270 = west)
        @SerializedName("deg")
        public int direction;
        
        // gust stores the wind gust speed in the specified units
        @SerializedName("gust")
        public double gust;
    }
    
    /**
     * Cloud coverage class
     * This class stores information about cloud coverage
     */
    public static class Clouds {
        // all stores the percentage of sky covered by clouds (0-100)
        @SerializedName("all")
        public int coverage;
    }
    
    /**
     * System information class
     * This class stores system-related information like country and sunrise/sunset times
     */
    public static class SystemInfo {
        // type stores the type of system (usually 1)
        @SerializedName("type")
        public int type;
        
        // id stores the system ID
        @SerializedName("id")
        public int id;
        
        // country stores the country code (e.g., "US", "GB", "CA")
        @SerializedName("country")
        public String country;
        
        // sunrise stores when the sun rises (Unix timestamp)
        @SerializedName("sunrise")
        public long sunrise;
        
        // sunset stores when the sun sets (Unix timestamp)
        @SerializedName("sunset")
        public long sunset;
    }
}

class WeatherForecastResponse {
    @SerializedName("list")
    private List<ForecastItem> forecastList;
    
    @SerializedName("city")
    private City city;
    
    public List<ForecastItem> getForecastList() { return forecastList; }
    public void setForecastList(List<ForecastItem> forecastList) { this.forecastList = forecastList; }
    
    public City getCity() { return city; }
    public void setCity(City city) { this.city = city; }
    
    public static class ForecastItem {
        @SerializedName("dt")
        private long timestamp;
        
        @SerializedName("main")
        private WeatherResponse.MainWeather main;
        
        @SerializedName("weather")
        private List<WeatherResponse.Weather> weather;
        
        @SerializedName("dt_txt")
        private String dateText;
        
        public long getTimestamp() { return timestamp; }
        public void setTimestamp(long timestamp) { this.timestamp = timestamp; }
        
        public WeatherResponse.MainWeather getMain() { return main; }
        public void setMain(WeatherResponse.MainWeather main) { this.main = main; }
        
        public List<WeatherResponse.Weather> getWeather() { return weather; }
        public void setWeather(List<WeatherResponse.Weather> weather) { this.weather = weather; }
        
        public String getDateText() { return dateText; }
        public void setDateText(String dateText) { this.dateText = dateText; }
    }
    
    public static class City {
        @SerializedName("name")
        private String name;
        
        @SerializedName("coord")
        private WeatherResponse.Coordinates coordinates;
        
        public String getName() { return name; }
        public void setName(String name) { this.name = name; }
        
        public WeatherResponse.Coordinates getCoordinates() { return coordinates; }
        public void setCoordinates(WeatherResponse.Coordinates coordinates) { this.coordinates = coordinates; }
    }
}
