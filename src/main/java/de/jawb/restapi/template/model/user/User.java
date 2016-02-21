/**
 * 
 */
package de.jawb.restapi.template.model.user;

import java.time.LocalDateTime;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * @author dit (13.12.2015)
 */
@Entity
@Table(name = "user")
public class User {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long          id;
                          
    @Column(name = "first_name")
    private String        firstName;
                          
    @Column(name = "second_name")
    private String        secondName;
                          
    @Column(name = "login", unique = true, nullable = false)
    private String        login;
                          
    @Column(name = "password", nullable = false)
    private String        password;
                          
    @Column(name = "last_login", nullable = true)
    private LocalDateTime lastLogin;
                          
    public Long getId() {
        return id;
    }
    
    public void setId(Long id) {
        this.id = id;
    }
    
    public String getFirstName() {
        return firstName;
    }
    
    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }
    
    public String getSecondName() {
        return secondName;
    }
    
    public void setSecondName(String secondName) {
        this.secondName = secondName;
    }
    
    public String getLogin() {
        return login;
    }
    
    public void setLogin(String login) {
        this.login = login;
    }
    
    public String getPassword() {
        return password;
    }
    
    public void setPassword(String password) {
        this.password = password;
    }
    
    public LocalDateTime getLastLogin() {
        return lastLogin;
    }
    
    public void setLastLogin(LocalDateTime lastLogin) {
        this.lastLogin = lastLogin;
    }
    
}
