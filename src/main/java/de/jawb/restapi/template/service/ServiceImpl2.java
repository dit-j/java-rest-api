/**
 * 
 */
package de.jawb.restapi.template.service;

import org.apache.commons.lang3.builder.ReflectionToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import org.springframework.stereotype.Service;

import de.jawb.restapi.template.config.profiles.V2;

/**
 * @author dit (20.01.2016)
 */
@V2
@Service
public class ServiceImpl2 implements IService {
    
    @Override
    public String toString() {
        return ReflectionToStringBuilder.toString(this, ToStringStyle.SHORT_PREFIX_STYLE);
    }
    
}
