package de.jawb.restapi.template.controller.rest.v1;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.Map;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import de.jawb.restapi.template.controller.api.Mapping;
import de.jawb.restapi.template.controller.api.response.APIResponse;
import de.jawb.restapi.template.controller.filters.RequestStatistics;
import de.jawb.restapi.template.model.db.user.IUserService;

@RestController
@RequestMapping(value = Mapping.V1.root)
public class ExampleRestV1Controller {
    
    final static String       V = "1.2.3";
                                
    @Autowired
    private RequestStatistics _statistics;
                              
    @Autowired
    private IUserService      _uService;
                              
    private LocalDateTime     _startTime;
                              
    @PostConstruct
    public void postConstruct() {
        _startTime = LocalDateTime.now();
    }
    
    @RequestMapping(value = Mapping.V1.status)
    public Object status() {
        
        Map<String, Object> map = new HashMap<>();
        map.put("status", "online since: " + _startTime.format(DateTimeFormatter.ofPattern("d MMM uuuu HH:mm")));
        map.put("version", V);
        map.put("statistics", _statistics.currentStats());
        
        return APIResponse.status(map);
    }
    
    @RequestMapping(value = Mapping.V1.user)
    public Object findUser(@RequestParam Long id) {
        return _uService.findUserWithId(id);
    }
}
