package de.jawb.restapi.template.config;

import java.sql.Driver;
import java.util.HashMap;
import java.util.Map;

import javax.persistence.EntityManagerFactory;
import javax.sql.DataSource;

import org.apache.commons.dbcp.BasicDataSource;
import org.hibernate.jpa.HibernatePersistenceProvider;
import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.cache.concurrent.ConcurrentMapCacheManager;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import org.springframework.dao.annotation.PersistenceExceptionTranslationPostProcessor;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@Configuration
@EnableCaching
@EnableTransactionManagement
@EnableJpaRepositories(basePackages = "de.jawb.restapi.template.model")
@EnableAsync
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
    public LocalContainerEntityManagerFactoryBean entityManagerFactory(DataSource dataSource) {
        
        LocalContainerEntityManagerFactoryBean em = new LocalContainerEntityManagerFactoryBean();
        em.setDataSource(dataSource);
        em.setPackagesToScan("de.jawb.restapi.template.model");
        em.setPersistenceProvider(new HibernatePersistenceProvider());
        
        Map<String, String> p = new HashMap<String, String>();
        p.put(org.hibernate.cfg.Environment.HBM2DDL_AUTO, "update");
        //p.put(org.hibernate.cfg.Environment.HBM2DDL_IMPORT_FILES, "import_psql.sql");
        p.put(org.hibernate.cfg.Environment.DIALECT, CustomMysqlDialect.class.getName());
        p.put(org.hibernate.cfg.Environment.SHOW_SQL, "true");
        
        em.setJpaPropertyMap(p);
        em.setJpaVendorAdapter(new HibernateJpaVendorAdapter());
        
        return em;
    }
    
    @Bean
    public PlatformTransactionManager transactionManager(EntityManagerFactory entityManagerFactory) throws Exception {
        return new JpaTransactionManager(entityManagerFactory);
    }
    
    @Bean
    public PersistenceExceptionTranslationPostProcessor exceptionTranslation() {
        return new PersistenceExceptionTranslationPostProcessor();
    }
    
    @Bean
    public CacheManager cacheManager() throws Exception {
        ConcurrentMapCacheManager scm = new ConcurrentMapCacheManager("access");
        return scm;
    }
    
}
