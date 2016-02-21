package de.jawb.restapi.template.service.access;

public interface IAccessService {
    
    boolean isValidKey(String key);
    
    String createNewAccess();
}
