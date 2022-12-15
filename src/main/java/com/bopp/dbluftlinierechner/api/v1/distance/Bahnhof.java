package com.bopp.dbluftlinierechner.api.v1.distance;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
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
}
