/**
 * 
 */
package de.jawb.restapi.template.model.user;

import org.springframework.stereotype.Repository;

import de.jawb.restapi.template.model.AbstractJpaDao;

/**
 * @author dit (14.12.2015)
 */
@Repository
public class UserDao extends AbstractJpaDao<User> {
    
    public UserDao() {
        setClazz(User.class);
    }
    
}
