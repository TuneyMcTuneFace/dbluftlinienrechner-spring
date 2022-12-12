package com.bopp.dbrechner;

public class Bahnhof {
    private String ds100;
    private String name;
    private double longitude;
    private double latitude;

    Bahnhof(String ds100, String name, double longitude, double latitude) {
        setDs100(ds100);
        setName(name);
        setLongitude(longitude);
        setLatitude(latitude);
    }

    public String getDs100() {
        return ds100;
    }

    public void setDs100(String ds100) {
        this.ds100 = ds100;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public double getLatitude() {
        return latitude;
    }

    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }

    public double getLongitude() {
        return longitude;
    }

    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }

}
