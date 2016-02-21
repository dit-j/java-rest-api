/**
 * 
 */
package de.jawb.restapi.template.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

import de.jawb.restapi.template.service.access.IAccessService;

/**
 * @author dit (21.02.2016)
 */
@Component
public class Test {
    
    private static final Logger _logger = LoggerFactory.getLogger(Test.class);
                                        
    @Autowired
    private IAccessService      _service;
                                
    @EventListener
    public void handleContextRefresh(ContextRefreshedEvent event) {
        
        _logger.debug("handleContextRefresh");
        
        String key = "daFDe-sk44-ssv21-iFGr";
        
        if (!_service.isValidKey(key)) {
            _service.createNewAccess(key);
        }
        
    }
    
}
