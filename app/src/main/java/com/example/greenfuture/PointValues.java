package com.example.greenfuture;

public class PointValues {
    long m_time;
    int moisture;
    int temp;
    int humidity;

    public PointValues(){

    }

    public PointValues(long m_time, int moisture, int temp, int humidity)
    {
        this.m_time = m_time;
        this.moisture = moisture;
        this.temp = temp;
        this.humidity = humidity;
    }

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
        return temp;
    }

    public int getHumidity()
    {
        return humidity;
    }


}
