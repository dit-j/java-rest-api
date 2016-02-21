package de.jawb.restapi.template.controller.rest;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import de.jawb.restapi.template.config.profiles.V2;
import de.jawb.restapi.template.controller.api.APIResponse;
import de.jawb.restapi.template.service.IService;

@V2
@RestController
@RequestMapping(value = "/v2")
public class APIControllerV2 {
    
    private static final DateTimeFormatter PATTERN    = DateTimeFormatter.ofPattern("dd.MM.yyyy HH:mm:ss");
    private final String                   _startTime = LocalDateTime.now().format(PATTERN);
                                                      
    @Autowired
    private IService                       _service;
                                           
    @RequestMapping(value = "/status")
    public Object status() {
        
        Map<String, Object> map = new HashMap<>();
        map.put("status", "online since: " + _startTime);
        map.put("version", "v2");
        map.put("request", "32/sec");
        map.put("service", _service.toString());
        
        return APIResponse.status(map);
    }
    
}
