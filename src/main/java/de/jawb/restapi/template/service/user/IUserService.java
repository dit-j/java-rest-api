/**
 * 
 */
package de.jawb.restapi.template.service.user;

import de.jawb.restapi.template.model.user.User;

/**
 * @author dit (14.12.2015)
 */
public interface IUserService {
    
    User saveNewUser(String name, String secondName);
    
    Object findUserWithId(Long id);
    
}
