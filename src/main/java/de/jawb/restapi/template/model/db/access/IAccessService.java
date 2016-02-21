package de.jawb.restapi.template.model.db.access;

public interface IAccessService {
    
    boolean isValidKey(String key);
    
    String createNewAccess();
}
