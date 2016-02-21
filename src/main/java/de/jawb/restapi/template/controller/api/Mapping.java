package de.jawb.restapi.template.controller.api;

public interface Mapping {
    
    String REST = "/rest";
    
    public interface V1 {
        
        String root   = REST + "/v1";
                      
        String status = root + "/status";
        String user   = root + "/user";
                      
    }
}
