/**
 * 
 */
package de.jawb.restapi.template.controller.api.response;

import java.util.Map;

import de.jawb.restapi.template.model.db.user.User;

/**
 * @author Dieter Kraus (08.10.2015)
 */
public enum APIResponseResultType {
    
    error(
            "Fehlerbeschreibung",
            String.class),
            
    status(
            "Status-Report",
            String.class),
            
    errormap(
            "Fehler Map<String, String>",
            Map.class),
            
    string(
            "Ergebnis ist ein String",
            String.class),
    
    user(
            "Ergebnis ist ein User-Objekt",
            User.class);
            
    public final String   info;
    public final Class<?> data;
                          
    private APIResponseResultType(String i, Class<?> clazz) {
        info = i;
        data = clazz;
    }
}
