package com.bopp.dbluftlinierechner.api.v1.distance;

import java.util.regex.Pattern;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.bopp.dbluftlinierechner.api.v1.distance.bean.Bahnhof;
import com.bopp.dbluftlinierechner.api.v1.distance.bean.Distance;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;

@RestController
@RequestMapping("/api/v1/distance")
public class DistanceController {

    @GetMapping("/")
    public ResponseEntity<JsonNode> root() {
        ObjectNode response = new ObjectMapper().createObjectNode();
        response.put("error", "Keine Bahnhöfe angegeben");
        return ResponseEntity.badRequest().body(response);
    }

    @GetMapping("/{bahnhof}")
    public ResponseEntity<JsonNode> einBahnhof() {
        ObjectNode response = new ObjectMapper().createObjectNode();
        response.put("error", "Nur ein Bahnhof angegeben.");
        return ResponseEntity.badRequest().body(response);
    }

    @GetMapping("/{bahnhof1}/{bahnhof2}")
    public ResponseEntity<JsonNode> calculateDistance(
            @PathVariable("bahnhof1") String b1,
            @PathVariable("bahnhof2") String b2) {

        ObjectNode response = new ObjectMapper().createObjectNode();

        String regex = "^.{2,6}$";
        if (!Pattern.matches(regex, b1) || !Pattern.matches(regex, b2)) {
            response.put("error", "Parameter ausserhalb der Definition");
            return ResponseEntity.badRequest().body(response);
        }

        if (!LuftlinierechnerUtility.ds100HashMap.containsKey(b1)) {
            response.put("error", "Parameter 1 nicht da");
            return ResponseEntity.badRequest().body(response);
        }

        if (!LuftlinierechnerUtility.ds100HashMap.containsKey(b2)) {
            response.put("error", "Parameter 2 nicht da");
            return ResponseEntity.badRequest().body(response);
        }

        Bahnhof bh1 = LuftlinierechnerUtility.ds100HashMap.get(b1);
        Bahnhof bh2 = LuftlinierechnerUtility.ds100HashMap.get(b2);

        if (bh1.getName() == bh2.getName()) {
            response.put("error", "Selber Bahnhof");
            return ResponseEntity.badRequest().body(response);
        }
        
        Distance d = LuftlinierechnerUtility.distanceBetweenPoints(bh1, bh2);

        response.put("from", bh1.getName());
        response.put("to", bh2.getName());
        response.put("distance", Math.round(d.getDistance()));
        response.put("unit", d.getUnit());

        return ResponseEntity.ok(response);
    }
}
