package de.jawb.restapi.template.model.access;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Repository;

import de.jawb.restapi.template.model.AbstractJpaDao;

/**
 * @author dit
 */
@Repository
public class ApiAccessDao extends AbstractJpaDao<ApiAccess> implements IApiAccessDao {
    
    private static final Logger _logger = LoggerFactory.getLogger(ApiAccessDao.class);
    
    public ApiAccessDao() {
        setClazz(ApiAccess.class);
    }
    
    @Override
    @Cacheable(value = "access", key = "#id")
    public ApiAccess getById(String id) {
        _logger.debug("getById");
        simulateSlowService();
        return getByID(id);
    }
    
    private void simulateSlowService() {
        _logger.debug("simulateSlowService");
        try {
            long time = 5000L;
            Thread.sleep(time);
        } catch (InterruptedException e) {
            throw new IllegalStateException(e);
        }
    }
    
    @Override
    public void merge(ApiAccess acc) {
        super.defaultMerge(acc);
    }
    
    @Override
    public boolean isExists(String key) {
        return getById(key) != null;
    }
}
