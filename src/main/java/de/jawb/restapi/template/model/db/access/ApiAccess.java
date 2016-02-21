package de.jawb.restapi.template.model.db.access;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "api_access")
public class ApiAccess {
    
    @Id
    private String  id;
                    
    @Column(name = "is_active")
    private Boolean isActive;
                    
    @Column(name = "requests")
    private Long    requests;
                    
    public String getId() {
        return id;
    }
    
    public void setId(String id) {
        this.id = id;
    }
    
    public Boolean getIsActive() {
        return isActive;
    }
    
    public void setIsActive(Boolean isActive) {
        this.isActive = isActive;
    }
    
    public Long getRequests() {
        return requests;
    }
    
    public void setRequests(Long requests) {
        this.requests = requests;
    }
    
}
