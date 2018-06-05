package com.dp.headfirst.observer;

import org.junit.Test;

public class WeatherStationTest {
    
    @Test
    public void weatherStationTest(){
        WeatherData weatherData = new WeatherData();
        CurrentConditionsDisplay display = new CurrentConditionsDisplay(weatherData);
        weatherData.setMeasurements(80, 65, 30.4f);
    }

    @Test
    public void weaterStationJDKTest(){
        com.dp.headfirst.observer.jdk.WeatherData weatherData = new com.dp.headfirst.observer.jdk.WeatherData();
        com.dp.headfirst.observer.jdk.CurrentConditionsDisplay display = 
            new com.dp.headfirst.observer.jdk.CurrentConditionsDisplay(weatherData);
        weatherData.setMeasurements(99, 55, 30.4f);
    }

}