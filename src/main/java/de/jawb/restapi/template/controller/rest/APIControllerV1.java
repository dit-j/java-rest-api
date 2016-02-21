package de.jawb.restapi.template.controller.rest;

import java.util.HashMap;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import de.jawb.restapi.template.config.profiles.V1;
import de.jawb.restapi.template.controller.api.APIResponse;
import de.jawb.restapi.template.controller.handlers.GlobalExceptionHandler;
import de.jawb.restapi.template.service.IService;

@V1
@RestController
@RequestMapping(value = "/v1")
public class APIControllerV1 {

    private static final Logger _logger = LoggerFactory.getLogger(GlobalExceptionHandler.class);
                                        
    @Qualifier("now")
    @Autowired
    private String              _startTime;
                                
    @Autowired
    private IService            _service;
                                
    @RequestMapping(value = "/status")
    public Object status() {
        
        _logger.debug("status {}", _startTime);
        
        Map<String, Object> map = new HashMap<>();
        map.put("status", "online since: " + _startTime);
        map.put("version", "v1");
        map.put("request", "32/sec");
        map.put("service", _service.toString());
        
        
        if(_service != null){
            throw new RuntimeException();
        }
        
        
        return APIResponse.status(map);
    }
    
}
