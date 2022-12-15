package com.bopp.dbluftlinierechner.api.v1.distance;

import org.springframework.stereotype.Component;
import com.bopp.dbluftlinierechner.Application;
import com.bopp.dbluftlinierechner.api.v1.distance.bean.CSVBahnhof;
import com.bopp.dbluftlinierechner.api.v1.distance.bean.Distance;
import com.opencsv.bean.CsvToBean;
import com.opencsv.bean.CsvToBeanBuilder;
import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.Charset;
import java.util.HashMap;
import java.util.Iterator;

// Luftlinienrechner 
/**
 * Diese Utility-Klasse beinhaltet die Hashmap für die DS100 Codes und liest die
 * CSVFile ein.
 * Darüberhinaus ist dort die Methode für die Rechnung der Luftlinie.
 */
@Component
public final class LuftlinierechnerUtility {

    /**
     * Diese Funktion initialisiert den Luftlinierechner durch das einlesen der
     * gegebenen CSV. Diese Funktion sollte am Start des Services ausgeführt werden.
     */
    public static void init(String csvFile) {
        try {
            LuftlinierechnerUtility.ds100HashMap = LuftlinierechnerUtility.readCsvToHashMap(csvFile);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static HashMap<String, Bahnhof> ds100HashMap = new HashMap<String, Bahnhof>();

    /**
     * Liest die Angegebene CSV File und gibt das Ergebnis in einer Hashmap aus.
     * 
     * @param filename
     */
    public static HashMap<String, Bahnhof> readCsvToHashMap(String filename) throws Exception {

        InputStream inputStream = Application.class.getResourceAsStream(filename);
        if (inputStream == null)
            throw new Exception(String.format("File %s nicht findbar", filename));

        HashMap<String, Bahnhof> ds100Map = new HashMap<String, Bahnhof>();

        try {
            InputStreamReader isr = new InputStreamReader(inputStream, Charset.forName("UTF-8"));
            BufferedReader reader = new BufferedReader(isr);
            CsvToBean<CSVBahnhof> csvToBean = new CsvToBeanBuilder<CSVBahnhof>(reader)
                    .withType(CSVBahnhof.class)
                    .withSeparator(';')
                    .build();

            Iterator<CSVBahnhof> beanIterator = csvToBean.iterator();

            while (beanIterator.hasNext()) {
                CSVBahnhof bh = beanIterator.next();
                if (bh.getVerkehr().equals("FV")) {
                    
                    float longitude = Float.parseFloat(bh.getLaenge().replace(',', '.'));
                    float latitude = Float.parseFloat(bh.getBreite().replace(',', '.'));

                    // Wenn mehrere DS100 Codes in einem Wert sind
                    for (String ds100 : bh.getDS100().split(",")) {
                        Bahnhof b = new Bahnhof(ds100, bh.getNAME(), longitude, latitude);
                        ds100Map.put(ds100, b);
                    }
                }
            }
            reader.close();

        } catch (Exception e) {
            throw e;
        }

        return ds100Map;
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

    /**
     * Konvertiert Bogenmaß zu Grad
     * 
     * @param n
     * @return
     */
    public static double toDeg(double n) {
        return n * (180 / Math.PI);
    }

    /**
     * 
     * @param b1
     * @param b2
     * @return {"unit": String, "distance": Double}
     *         default unit: "km"
     *         Quelle https://de.martech.zone/calculate-great-circle-distance/
     */
    public static Distance distanceBetweenPoints(Bahnhof b1, Bahnhof b2) {
        return distanceBetweenPoints(b1, b2, "km");
    }

    /**
     * 
     * @param b1
     * @param b2
     * @param unit "km" oder "miles"
     * @return {"unit": String, "distance": Double}
     *         Quelle: https://de.martech.zone/calculate-great-circle-distance/
     */
    public static Distance distanceBetweenPoints(Bahnhof b1, Bahnhof b2, String unit) {
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
                distance *= 1.609344;
                break;
            default:
                return null;
        }
        
        Distance result = new Distance(distance, unit);
        return result;
    }

}
