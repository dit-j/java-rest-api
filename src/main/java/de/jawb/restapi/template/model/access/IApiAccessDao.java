/**
 * 
 */
package de.jawb.restapi.template.model.access;

/**
 * @author dit (21.02.2016)
 */
public interface IApiAccessDao {
    
    ApiAccess getById(String key);
    
    /**
     * @param key
     * @return
     */
    boolean isExists(String key);
    
    /**
     * @param a
     */
    void create(ApiAccess a);
    
    /**
     * @param acc
     */
    void merge(ApiAccess acc);
    
}
