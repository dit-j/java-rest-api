package de.jawb.restapi.template.service.access;

import de.jawb.restapi.template.model.access.ApiAccess;

public interface IAccessService {
    
    ApiAccess getValidApiAccess(String key);
    
    boolean isValidKey(String key);
    
    String createNewAccess(String key);
    
    Long incrementRequestCount(String key);
}
