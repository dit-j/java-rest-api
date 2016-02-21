/**
 * 
 */
package de.jawb.restapi.template.model.user;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * @author dit (14.12.2015)
 */
@Service
public class UserService implements IUserService {
    
    @Autowired
    private UserDao _udao;
    
    @Override
    @Transactional
    public User saveNewUser(String name, String secondName) {
        
        User u = new User();
        u.setFirstName(name);
        u.setSecondName(secondName);
        u.setLogin("test");
        u.setPassword("TODO"); // TODO: Hash
        
        _udao.create(u);
        
        return u;
    }
}
