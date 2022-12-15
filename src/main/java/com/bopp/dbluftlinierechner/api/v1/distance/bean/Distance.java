package com.bopp.dbluftlinierechner.api.v1.distance.bean;

import lombok.AllArgsConstructor;
import lombok.Data;
@Data
@AllArgsConstructor
public class Distance {
    private double distance;
    private String unit;
}
