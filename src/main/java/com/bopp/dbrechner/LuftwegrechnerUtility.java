package com.bopp.dbrechner;

import java.util.Map;

import org.springframework.stereotype.Component;

import com.opencsv.CSVParser;
import com.opencsv.CSVParserBuilder;
import com.opencsv.CSVReader;
import com.opencsv.CSVReaderBuilder;
import com.opencsv.bean.CsvBindByPosition;
import com.opencsv.bean.CsvToBean;
import com.opencsv.bean.CsvToBeanBuilder;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

// Luftrechner 
@Component
public final class LuftwegrechnerUtility {
    public static HashMap<String, Bahnhof> ds100HashMap = new HashMap<String, Bahnhof>();

    public static void readCsv() {
        URL ioStream = LuftwegrechnerUtility.class.getClassLoader().getResource("./data/D_Bahnhof_2020_alle.CSV");
        try {
            
        } catch (Exception e) {
            // TODO: handle exception
        }
    }

    public static double toRad(double n) {
        return n * (Math.PI / 180);
    }

    public static double toDeg(double n) {
        return n * (180 / Math.PI);
    }

    public static HashMap<String, String> distanceBetweenPoints(Bahnhof b1, Bahnhof b2) {
        return distanceBetweenPoints(b1, b2, "km");
    }

    public static HashMap<String, String> distanceBetweenPoints(Bahnhof b1, Bahnhof b2, String unit) {
        double theta = b1.getLongitude() - b2.getLongitude();
        double distance = (Math.sin(toRad(b1.getLatitude())) *
                Math.sin(toRad(b2.getLatitude())))
                + (Math.cos(toRad(b1.getLatitude())) *
                        Math.cos(toRad(b2.getLatitude()))) *
                        Math.cos(toRad(theta));
        distance = Math.acos(distance);
        distance = toDeg(distance);
        distance = distance * 60 * 1.1515;
        switch (unit) {
            case "miles":
                break;
            case "km":
                distance = distance * 1.609344;
                break;
            default:
                return null;
        }
        HashMap<String, String> d = new HashMap<String, String>();
        d.put("unit", unit);
        d.put("distance", Double.toString(distance));
        return d;
    }

}
