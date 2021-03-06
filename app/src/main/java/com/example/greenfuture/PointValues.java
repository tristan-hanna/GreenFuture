package com.example.greenfuture;

public class PointValues {
    long m_time;
    int moisture;
    int temperature;
    int humidity;

    public PointValues(){

    }

    //Constructor
    public PointValues(long m_time, int moisture, int temp, int humidity)
    {
        this.m_time = m_time;
        this.moisture = moisture;
        this.temperature = temp;
        this.humidity = humidity;
    }

    //Gets that retrieve data from database
    public long getM_time()
    {
        return m_time * 1000;
    }

    public int getMoisture()
    {
        return moisture;
    }

    public int getTemp()
    {
        return temperature;
    }

    public int getHumidity()
    {
        return humidity;
    }


}
