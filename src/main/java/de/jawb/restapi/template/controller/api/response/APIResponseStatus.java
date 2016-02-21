/**
 * 
 */
package de.jawb.restapi.template.controller.api.response;

/**
 * @author Dieter Kraus (08.10.2015)
 */
public enum APIResponseStatus {
    
    OK(
            "Anfrage ist gueltig und wurde erfolgreich vom Server bearbeitet."),
            
    ERROR(
            "Es kam zu einem Fehler bei der Verarbeitung der Anrage.");
            
    public final String info;
    
    private APIResponseStatus(String i) {
        info = i;
    }
    
}
