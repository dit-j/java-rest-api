package de.jawb.restapi.template.controller.filters;

import java.util.Map;
import java.util.Map.Entry;
import java.util.TreeMap;
import java.util.concurrent.atomic.AtomicLong;

import javax.annotation.PostConstruct;
import javax.servlet.ServletContext;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import de.jawb.restapi.template.controller.api.Mapping;
import de.jawb.restapi.template.util.ReflectionUtil;

@Component
public class RequestStatistics {
    
    @Autowired
    private ServletContext          servletContext;
                                    
    private static final String     BAD_REQUEST = "404";
    private Map<String, AtomicLong> map;
                                    
    @PostConstruct
    public void postConstruct() {
        
        Map<String, Object> consts = ReflectionUtil.getConstants(Mapping.V1.class);
        
        map = new TreeMap<>();
        
        for (Object uri : consts.values()) {
            map.put(servletContext.getContextPath() + uri.toString(), new AtomicLong());
        }
        
        map.put(BAD_REQUEST, new AtomicLong());
        
    }
    
    public void mark(String requestURI) {
        AtomicLong al = map.get(requestURI);
        if (al == null) {
            al = map.get(BAD_REQUEST);
        }
        al.incrementAndGet();
    }
    
    public Map<String, Long> currentStats() {
        
        Map<String, Long> m = new TreeMap<>();
        
        for (Entry<String, AtomicLong> e : map.entrySet()) {
            m.put(e.getKey(), e.getValue().get());
        }
        
        return m;
    }
    
}
