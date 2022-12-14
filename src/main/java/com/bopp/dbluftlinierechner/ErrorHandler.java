package com.bopp.dbluftlinierechner;

import org.springframework.boot.web.servlet.error.ErrorController;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

@RestController
@ResponseBody
public class ErrorHandler implements ErrorController {
    @RequestMapping("/error")
    @ResponseStatus(value = HttpStatus.NOT_FOUND)
    Map defaultError() {
        Map<String, String> response = new HashMap<String, String>();
        response.put("httpcode", "404");
        response.put("error", "resource not found");
        return response;
    }
}
