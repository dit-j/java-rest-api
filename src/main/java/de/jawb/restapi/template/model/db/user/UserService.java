/**
 * 
 */
package de.jawb.restapi.template.model.db.user;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import de.jawb.restapi.template.controller.api.response.APIResponse;

/**
 * @author dit (14.12.2015)
 */
@Service
public class UserService implements IUserService {
    
    @Autowired
    private UserDao         _udao;
                            
    @Autowired
    private PasswordEncoder _encoder;
                            
    @Transactional
    @Override
    public User saveNewUser(String name, String secondName) {
        
        User u = new User();
        u.setFirstName(name);
        u.setSecondName(secondName);
        u.setLogin("test");
        u.setPassword(_encoder.encode("12345"));
        
        _udao.create(u);
        
        return u;
    }
    
    @Transactional(readOnly = true)
    @Override
    public Object findUserWithId(Long id) {
        return APIResponse.user(_udao.getByID(id));
    }
    
}
