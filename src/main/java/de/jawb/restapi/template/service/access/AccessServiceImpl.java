/**
 * 
 */
package de.jawb.restapi.template.service.access;

import java.util.UUID;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import de.jawb.restapi.template.base.exceptions.BaseAppException;
import de.jawb.restapi.template.model.access.ApiAccess;
import de.jawb.restapi.template.model.access.IApiAccessDao;

/**
 * @author dit
 */
@Service
public class AccessServiceImpl implements IAccessService {
    
    private static final Logger _logger = LoggerFactory.getLogger(AccessServiceImpl.class);
                                        
    @Autowired
    private IApiAccessDao       _dao;
                                
    @Override
    public ApiAccess getValidApiAccess(String key) {
        _logger.debug("getValidApiAccess: {}", key);
        ApiAccess acc = _dao.getById(key);
        if (acc != null && Boolean.TRUE.equals(acc.getIsActive())) {
            return acc;
        }
        return null;
    }
    
    @Transactional
    @Override
    public boolean isValidKey(String key) {
        _logger.debug("isValidKey: {}", key);
        ApiAccess acc = _dao.getById(key);
        if (acc != null && Boolean.TRUE.equals(acc.getIsActive())) {
            acc.setRequests(acc.getRequests() + 1L);
            return true;
        }
        return false;
    }
    
    @Async
    @Transactional
    @Override
    public Long incrementRequestCount(String key) {
        _logger.debug("incrementRequestCount: {}", key);
        ApiAccess acc = _dao.getById(key);
        if (acc != null) {
            
            acc.setRequests(acc.getRequests() + 1L);
            _dao.merge(acc);
            
            return acc.getRequests();
        }
        throw new BaseAppException("unknown access key: " + key);
    }
    
    @Override
    @Transactional
    public String createNewAccess(String key) {
        _logger.debug("createNewAccess: {}", key);
        String uuid = key;
        
        if (uuid == null) {
            do {
                uuid = _randomUUID();
            } while (_dao.isExists(uuid));
        }
        
        ApiAccess a = new ApiAccess();
        a.setId(uuid);
        a.setIsActive(true);
        a.setRequests(0L);
        
        _dao.create(a);
        
        return uuid;
    }
    
    private String _randomUUID() {
        return UUID.randomUUID().toString();
    }
}
