package com.s23010285.desk.api;

import com.google.gson.annotations.SerializedName;
import java.util.List;

public class WeatherResponse {
    @SerializedName("coord")
    private Coordinates coordinates;
    
    @SerializedName("weather")
    private List<Weather> weather;
    
    @SerializedName("main")
    private MainWeather main;
    
    @SerializedName("wind")
    private Wind wind;
    
    @SerializedName("name")
    private String cityName;
    
    @SerializedName("dt")
    private long timestamp;
    
    // Getters and Setters
    public Coordinates getCoordinates() { return coordinates; }
    public void setCoordinates(Coordinates coordinates) { this.coordinates = coordinates; }
    
    public List<Weather> getWeather() { return weather; }
    public void setWeather(List<Weather> weather) { this.weather = weather; }
    
    public MainWeather getMain() { return main; }
    public void setMain(MainWeather main) { this.main = main; }
    
    public Wind getWind() { return wind; }
    public void setWind(Wind wind) { this.wind = wind; }
    
    public String getCityName() { return cityName; }
    public void setCityName(String cityName) { this.cityName = cityName; }
    
    public long getTimestamp() { return timestamp; }
    public void setTimestamp(long timestamp) { this.timestamp = timestamp; }
    
    // Inner classes
    public static class Coordinates {
        @SerializedName("lat")
        private double latitude;
        
        @SerializedName("lon")
        private double longitude;
        
        public double getLatitude() { return latitude; }
        public void setLatitude(double latitude) { this.latitude = latitude; }
        
        public double getLongitude() { return longitude; }
        public void setLongitude(double longitude) { this.longitude = longitude; }
    }
    
    public static class Weather {
        @SerializedName("id")
        private int id;
        
        @SerializedName("main")
        private String main;
        
        @SerializedName("description")
        private String description;
        
        @SerializedName("icon")
        private String icon;
        
        public int getId() { return id; }
        public void setId(int id) { this.id = id; }
        
        public String getMain() { return main; }
        public void setMain(String main) { this.main = main; }
        
        public String getDescription() { return description; }
        public void setDescription(String description) { this.description = description; }
        
        public String getIcon() { return icon; }
        public void setIcon(String icon) { this.icon = icon; }
    }
    
    public static class MainWeather {
        @SerializedName("temp")
        private double temperature;
        
        @SerializedName("feels_like")
        private double feelsLike;
        
        @SerializedName("temp_min")
        private double tempMin;
        
        @SerializedName("temp_max")
        private double tempMax;
        
        @SerializedName("pressure")
        private int pressure;
        
        @SerializedName("humidity")
        private int humidity;
        
        public double getTemperature() { return temperature; }
        public void setTemperature(double temperature) { this.temperature = temperature; }
        
        public double getFeelsLike() { return feelsLike; }
        public void setFeelsLike(double feelsLike) { this.feelsLike = feelsLike; }
        
        public double getTempMin() { return tempMin; }
        public void setTempMin(double tempMin) { this.tempMin = tempMin; }
        
        public double getTempMax() { return tempMax; }
        public void setTempMax(double tempMax) { this.tempMax = tempMax; }
        
        public int getPressure() { return pressure; }
        public void setPressure(int pressure) { this.pressure = pressure; }
        
        public int getHumidity() { return humidity; }
        public void setHumidity(int humidity) { this.humidity = humidity; }
    }
    
    public static class Wind {
        @SerializedName("speed")
        private double speed;
        
        @SerializedName("deg")
        private int direction;
        
        public double getSpeed() { return speed; }
        public void setSpeed(double speed) { this.speed = speed; }
        
        public int getDirection() { return direction; }
        public void setDirection(int direction) { this.direction = direction; }
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
