package com.bopp.dbluftlinierechner;

import org.springframework.boot.web.servlet.error.ErrorController;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;

@RestController
@ResponseBody
public class ErrorHandler implements ErrorController {
    @RequestMapping("/error")
    @ResponseStatus(value = HttpStatus.NOT_FOUND)
    ObjectNode notfound() {
        ObjectNode response = new ObjectMapper().createObjectNode();
        response.put("error", "resource not found");
        return response;
    }
}
