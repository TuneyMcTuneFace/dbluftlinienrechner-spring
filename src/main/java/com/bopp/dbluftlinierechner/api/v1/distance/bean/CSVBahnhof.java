package com.bopp.dbluftlinierechner.api.v1.distance.bean;
import com.opencsv.bean.CsvBindByName;
import lombok.Data;

@Data
public class CSVBahnhof {
    // EVA_NR;DS100;IFOPT;NAME;Verkehr;Laenge;Breite;Betreiber_Name;Betreiber_Nr;Status
    @CsvBindByName()
    private String DS100;

    @CsvBindByName
    private String NAME;
    
    @CsvBindByName
    private String Laenge;
    
    @CsvBindByName
    private String Breite;
    
    @CsvBindByName
    private String Verkehr;
    
    @CsvBindByName
    private String EVA_NR;
    
    @CsvBindByName
    private String IFOPT;
    
    @CsvBindByName
    private String Betreiber_Name;
    
    @CsvBindByName
    private String Betreiber_Nr;
    
    @CsvBindByName
    private String Status;
}
