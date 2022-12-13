package com.bopp.dbrechner.api.v1.distance;

public class Bahnhof {
    private String ds100;
    private String name;
    private float longitude;
    private float latitude;

    Bahnhof(String ds100, String name, float longitude, float latitude) {
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

    public float getLatitude() {
        return latitude;
    }

    public void setLatitude(float latitude) {
        this.latitude = latitude;
    }

    public float getLongitude() {
        return longitude;
    }

    public void setLongitude(float longitude) {
        this.longitude = longitude;
    }

}
