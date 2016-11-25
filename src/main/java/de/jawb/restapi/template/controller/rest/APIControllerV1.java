package de.jawb.restapi.template.controller.rest;

import java.util.HashMap;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import de.jawb.restapi.template.config.profiles.V1;
import de.jawb.restapi.template.controller.api.response.APIResponse;
import de.jawb.restapi.template.controller.filters.RequestStatistics;
import de.jawb.restapi.template.controller.handlers.GlobalExceptionHandler;
import de.jawb.restapi.template.model.access.ApiAccess;
import de.jawb.restapi.template.service.user.IUserService;

@V1
@RestController
@RequestMapping(value = "/v1")
public class APIControllerV1 {

    private static final Logger _logger = LoggerFactory.getLogger(GlobalExceptionHandler.class);

    @Qualifier("now")
    @Autowired
    private String              _startTime;

    @Autowired
    private IUserService        _userService;

    @Autowired
    private RequestStatistics   _stats;

    @RequestMapping(value = "/status", method = RequestMethod.GET)
    public Object status(ApiAccess access) {
        _logger.debug("status {}", access.getId());

        Map<String, Object> map = new HashMap<>();
        map.put("status", "online since: " + _startTime);
        map.put("version", "v1");
        map.put("stats", _stats.currentStats());

        return APIResponse.status(map);
    }

    @RequestMapping(value = "/user", method = RequestMethod.GET)
    public Object user(ApiAccess access, @RequestParam Long id) {
        _logger.debug("{}: user {}", access.getId(), id);
        return _userService.findUserWithId(id);
    }

}
