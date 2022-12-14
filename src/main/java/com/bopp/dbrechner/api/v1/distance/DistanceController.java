package com.bopp.dbrechner.api.v1.distance;

import java.util.HashMap;
import java.util.Map;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.annotation.JsonAnyGetter;

@RestController
@ResponseBody
public class DistanceController {
    @GetMapping("/api/v1/distance/{bahnhof1}/{bahnhof2}")
    @JsonAnyGetter
    public Map<String, String> calculateDistance(@PathVariable("bahnhof1") String b1, @PathVariable("bahnhof2") String b2){
        Map<String, String> response = new HashMap<String, String>();

        if(!LuftwegrechnerUtility.ds100HashMap.containsKey(b1)){
            response.put("error", "Value 1 nicht da");
            return response;
        }

        if(!LuftwegrechnerUtility.ds100HashMap.containsKey(b2)){
            response.put("error", "Value 2 nicht da");
            return response;
        }

        Bahnhof bh1 = LuftwegrechnerUtility.ds100HashMap.get(b1);
        Bahnhof bh2 = LuftwegrechnerUtility.ds100HashMap.get(b2);

        if(bh1.getName() == bh2.getName()){
            response.put("error", "Selber Bahnhof");
            return response;
        }

        HashMap<String, String> d = LuftwegrechnerUtility.distanceBetweenPoints(bh1, bh2);       
        
        response.put("from", bh1.getName());
        response.put("to", bh2.getName());
        response.put("distance", d.get("distance"));       
        response.put("unit", d.get("unit")); 

        return response;
    }

    @GetMapping("/api/v1/distance/")
    public Map<String, String> distanceResponse(){
        Map<String, String> response = new HashMap<String, String>();
        response.put("error", "Keine Bahnhöfe angegeben");
        return response;
    }

    //TODO Vielleicht hier eine API für einen CSV Hot-swap machen? Datei upload?
}
