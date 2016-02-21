package de.jawb.restapi.template.config;

import java.sql.Driver;
import java.util.HashMap;
import java.util.Map;

import javax.persistence.EntityManagerFactory;
import javax.sql.DataSource;

import org.apache.commons.dbcp.BasicDataSource;
import org.hibernate.jpa.HibernatePersistenceProvider;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@Configuration
@EnableTransactionManagement
public class DBConfiguration {
    
    @Bean
    public DataSource dataSource(Environment environment) throws Exception {
        
        String user = environment.getProperty("db.user");
        String pass = environment.getProperty("db.password");
        String url = environment.getProperty("db.url");
        
        Class<Driver> driverClass = environment.getPropertyAsClass("db.driverClass", Driver.class);
        
        BasicDataSource basicDataSource = new BasicDataSource();
        
        basicDataSource.setDriverClassName(driverClass.getName());
        basicDataSource.setPassword(pass);
        basicDataSource.setUrl(url);
        basicDataSource.setUsername(user);
        basicDataSource.setInitialSize(5);
        basicDataSource.setMaxActive(10);
        
        return basicDataSource;
        
    }
    
    @Bean
    public LocalContainerEntityManagerFactoryBean localContainerEntityManagerFactoryBean(DataSource dataSource) throws Exception {
        
        LocalContainerEntityManagerFactoryBean em = new LocalContainerEntityManagerFactoryBean();
        em.setDataSource(dataSource);
        em.setPackagesToScan("de.jawb.restapi.template.model.db"); 
        em.setPersistenceProvider(new HibernatePersistenceProvider());
        
        Map<String, String> p = new HashMap<String, String>();
        p.put(org.hibernate.cfg.Environment.HBM2DDL_AUTO, "create");
        //p.put(org.hibernate.cfg.Environment.HBM2DDL_IMPORT_FILES, "import_psql.sql");
        p.put(org.hibernate.cfg.Environment.DIALECT, CustomMysqlDialect.class.getName());
        p.put(org.hibernate.cfg.Environment.SHOW_SQL, "true");
        em.setJpaPropertyMap(p);
        
        return em;
    }
    
    @Bean
    public PlatformTransactionManager transactionManager(EntityManagerFactory entityManagerFactory) throws Exception {
        return new JpaTransactionManager(entityManagerFactory);
    }
    
//    @Bean
//    public CacheManager cacheManager() throws Exception {
//        SimpleCacheManager scm = new SimpleCacheManager();
//         
//        scm.setCaches(Arrays.asList(new ???Cache()));
//        
//        return scm;
//    }

}
