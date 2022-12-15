package com.bopp.dbluftlinierechner.api.v1.distance.bean;

import com.opencsv.bean.CsvBindByName;
import com.opencsv.bean.CsvNumber;

import lombok.Data;

@Data
public class Bahnhof {
    // EVA_NR;DS100;IFOPT;NAME;Verkehr;Laenge;Breite;Betreiber_Name;Betreiber_Nr;Status
    @CsvBindByName(column = "DS100")
    private String ds100;

    @CsvBindByName(column = "NAME")
    private String name;
    
    @CsvBindByName(column = "Laenge", locale = "de-DE")
    @CsvNumber("0.0")
    private Float longitude;
    
    @CsvBindByName(column = "Breite", locale = "de-DE" )
    @CsvNumber("0.0")
    private Float latitude;
    
    @CsvBindByName(column = "Verkehr")
    private String verkehr;
    
    @CsvBindByName(column = "EVA_NR")
    private String eva_nr;
    
    @CsvBindByName(column = "IFOPT")
    private String ifopt;
    
    @CsvBindByName(column = "Betreiber_Name")
    private String betreiber_name;
    
    @CsvBindByName(column = "Betreiber_Nr")
    private String betreiber_nr;
    
    @CsvBindByName(column = "Status")
    private String status;

}
