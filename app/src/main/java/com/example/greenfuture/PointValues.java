package com.example.greenfuture;

public class PointValues {
    long m_time;
    int moisture;

    public PointValues(){

    }

    public PointValues(long m_time, int moisture)
    {
        this.m_time = m_time;
        this.moisture = moisture;
    }

    public long getM_time()
    {
        return m_time;
    }

    public int getMoisture()
    {
        return moisture;
    }
}
