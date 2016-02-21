package de.jawb.restapi.template.config;

import org.hibernate.dialect.MySQL5InnoDBDialect;

/**
 * @author dit (18.03.2012)
 */
public class CustomMysqlDialect extends MySQL5InnoDBDialect {
    
    public String getTableTypeString() {
        return "ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci ROW_FORMAT=DYNAMIC";
    }
}
