package com.bopp.dbrechner.api.v1.distance;

import org.springframework.stereotype.Component;
import com.bopp.dbrechner.DbrechnerApplication;
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
public final class LuftwegrechnerUtility {

    /**
     * Diese Funktion initialisiert den Luftwegrechner durch das einlesen der gegebenen CSV
     */
    public static void init(){
        LuftwegrechnerUtility.readCsv("./data/D_Bahnhof_2020_alle.CSV");
    }

    public static HashMap<String, Bahnhof> ds100HashMap = new HashMap<String, Bahnhof>();

    public class BahnhofBean {
        @CsvBindByPosition(position = 0)
        public String EVA_NR;
        @CsvBindByPosition(position = 1)
        public String DS100;
        @CsvBindByPosition(position = 2)
        public String IFOPT;
        @CsvBindByPosition(position = 3)
        public String NAME;
        @CsvBindByPosition(position = 4)
        public String Verkehr;
        @CsvBindByPosition(position = 5)
        public String Laenge;
        @CsvBindByPosition(position = 6)
        public String Breite;
        @CsvBindByPosition(position = 7)
        public String Betreiber_Name;
        @CsvBindByPosition(position = 8)
        public String Betreiber_Nr;
        @CsvBindByPosition(position = 9)
        public String Status;
    }

    public static void readCsv(String filename) {
        URL csvUrl = DbrechnerApplication.class.getClassLoader().getResource(filename);
        HashMap<String, Bahnhof> ds100Map = new HashMap<String, Bahnhof>();
        try {
            BufferedReader reader = new BufferedReader(new FileReader(csvUrl.getFile(), Charset.forName("UTF-8")));
            CSVParser csvParser = new CSVParserBuilder().withSeparator(';').build();
            CSVReader csvReader = new CSVReaderBuilder(reader).withSkipLines(1).withCSVParser(csvParser).build();
            String[] line;
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
            LuftwegrechnerUtility.ds100HashMap = ds100Map;
            reader.close();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static double toRad(double n) {
        return n * (Math.PI / 180);
    }

    public static double toDeg(double n) {
        return n * (180 / Math.PI);
    }


    /**
     * 
     * @param b1
     * @param b2
     * @return {"unit": String, "distance": String}
     * default unit: "km"
     * Quelle https://de.martech.zone/calculate-great-circle-distance/
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
     * Quelle https://de.martech.zone/calculate-great-circle-distance/
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
