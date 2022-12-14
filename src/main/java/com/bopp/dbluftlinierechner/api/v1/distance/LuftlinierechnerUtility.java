package com.bopp.dbluftlinierechner.api.v1.distance;

import org.apache.catalina.mapper.Mapper;
import org.springframework.stereotype.Component;

import com.bopp.dbluftlinierechner.Application;
import com.fasterxml.jackson.databind.JsonNode;
import com.opencsv.CSVParser;
import com.opencsv.CSVParserBuilder;
import com.opencsv.CSVReader;
import com.opencsv.CSVReaderBuilder;
import com.opencsv.bean.CsvBindByPosition;
import java.io.BufferedReader;
import java.io.FileReader;
import java.net.URL;
import java.nio.charset.Charset;
import java.util.HashMap;

// Luftrechner 
@Component
public final class LuftlinierechnerUtility {

    /**
     * Diese Funktion initialisiert den Luftlinierechner durch das einlesen der
     * gegebenen CSV. Diese Funktion sollte am Start des Services ausgeführt werden.
     */
    public static void init() {
        try {
            LuftlinierechnerUtility.readCsv("./data/D_Bahnhof_2020_alle.CSV");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static HashMap<String, Bahnhof> ds100HashMap = new HashMap<String, Bahnhof>();

    // TODO: BahnhofBean via CSVtoBean möglich machen
    /*
     * public class BahnhofBean {
     * public String EVA_NR;
     * public String DS100;
     * public String IFOPT;
     * public String NAME;
     * public String Verkehr;
     * public String Laenge;
     * public String Breite;
     * public String Betreiber_Name;
     * public String Betreiber_Nr;
     * public String Status;
     * }
     */

    /**
     * Liest die Angegebene CSV File
     * 
     * @param filename
     */
    // TODO: BahnhofBean -> Name, Breite, etc. via CSVToBean abfragen
    public static void readCsv(String filename) throws Exception {

        URL csvUrl = Application.class.getClassLoader().getResource(filename);
        HashMap<String, Bahnhof> ds100Map = new HashMap<String, Bahnhof>();

        try {
            BufferedReader reader = new BufferedReader(new FileReader(csvUrl.getFile(), Charset.forName("UTF-8")));
            CSVParser csvParser = new CSVParserBuilder().withSeparator(';').build();
            CSVReader csvReader = new CSVReaderBuilder(reader).withCSVParser(csvParser).build();
            String[] line;

            // Checke Positionen der Columnheader
            line = csvReader.readNext();
            if (!(line[1].equals("DS100") &&
                    line[3].equals("NAME") &&
                    line[4].equals("Verkehr") &&
                    line[5].equals("Laenge") &&
                    line[6].equals("Breite"))) {
                reader.close();
                throw new Exception("CSV Columnheader sind nicht korrekt.");
            }

            // Iteriere durch alle CSV Einträge, speichere sie in die Hashmap
            while ((line = csvReader.readNext()) != null) {
                String verkehr = line[4];
                if (verkehr.equals("FV")) {
                    float longitude = Float.parseFloat(line[5].replace(',', '.'));
                    float latitude = Float.parseFloat(line[6].replace(',', '.'));
                    String ds100Code = line[1];
                    String[] ds100Split = ds100Code.split(",");
                    String name = line[3];
                    for (String ds100 : ds100Split) {
                        Bahnhof b = new Bahnhof(ds100, name, longitude, latitude);
                        ds100Map.put(ds100, b);
                    }
                }
            }
            LuftlinierechnerUtility.ds100HashMap = ds100Map;
            reader.close();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Konvertiert Grad zu Bogenmaß
     * 
     * @param n Grad
     * @return
     */
    public static double toRad(double n) {
        return n * (Math.PI / 180);
    }

    /*
     * Konvertiert Bogenmaß zu Grad
     */
    public static double toDeg(double n) {
        return n * (180 / Math.PI);
    }

    /**
     * 
     * @param b1
     * @param b2
     * @return {"unit": String, "distance": String}
     *         default unit: "km"
     *         Quelle https://de.martech.zone/calculate-great-circle-distance/
     */
    public static HashMap<String, String> distanceBetweenPoints(Bahnhof b1, Bahnhof b2) {
        return distanceBetweenPoints(b1, b2, "km");
    }

    /**
     * 
     * @param b1
     * @param b2
     * @param unit "km" oder "miles"
     * @return {"unit": String, "distance": String}
     * @implNote Quelle: https://de.martech.zone/calculate-great-circle-distance/
     */
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
        d.put("distance", Integer.toString((int) Math.round(distance)));
        return d;
    }

}
