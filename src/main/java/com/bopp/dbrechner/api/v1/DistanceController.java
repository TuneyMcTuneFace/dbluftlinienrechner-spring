package com.bopp.dbrechner.api.v1;

import java.util.HashMap;
import java.util.Map;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.bopp.dbrechner.LuftwegrechnerUtility;
import com.fasterxml.jackson.annotation.JsonAnyGetter;

import jakarta.websocket.server.PathParam;

@RestController
@ResponseBody
public class DistanceController {
    @GetMapping("/api/v1/distance/{bahnhof1}/{bahnhof2}")
    @JsonAnyGetter
    public Map<String, String> calculateDistance(@PathParam("bahnhof1") String b1, @PathParam("bahnhof2") String b2){
        Map<String, String> h = new HashMap<String, String>();
        h.put("Test", "lol");
        return h;
    }
}
