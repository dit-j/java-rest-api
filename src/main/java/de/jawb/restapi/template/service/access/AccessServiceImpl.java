/**
 * 
 */
package de.jawb.restapi.template.service.access;

import java.util.UUID;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import de.jawb.restapi.template.model.access.ApiAccess;
import de.jawb.restapi.template.model.access.ApiAccessDao;

/**
 * @author dit
 */
@Service
public class AccessServiceImpl implements IAccessService {
    
    @Autowired
    private ApiAccessDao _dao;
    
    @Transactional
    @Override
    public boolean isValidKey(String key) {
        return _dao.isExists(key);
    }
    
    @Transactional
    @Override
    public String createNewAccess() {
        
        String uuid = null;
        
        do {
            uuid = _randomUUID();
        } while(_dao.isExists(uuid));
        
        ApiAccess a = new ApiAccess();
        a.setId(uuid);
        a.setIsActive(true);
        a.setRequests(0L);
        
        _dao.create(a);
        
        return uuid;
    }
    
    private String _randomUUID(){
        return UUID.randomUUID().toString();
    }
}
