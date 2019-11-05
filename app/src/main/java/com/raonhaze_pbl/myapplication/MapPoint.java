package com.raonhaze_pbl.myapplication;

public class MapPoint {
    private String Name;
    private double latitude;
    private double longtitude;

    public MapPoint()
    {
        super();
    }

    public MapPoint(String Name,double latitude,double longitude)
    {
        this.Name=Name;
        this.latitude=latitude;
        this.longtitude=longitude;
    }

    public String getName()
    {
        return Name;
    }

    public void setName(String name) {
        this.Name = name;
    }

    public double getLatitude() {
        return latitude;
    }

    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }

    public double getLongtitude() {
        return longtitude;
    }

    public void setLongtitude(double longtitude) {
        this.longtitude = longtitude;
    }
}
