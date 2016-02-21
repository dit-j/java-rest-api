package de.jawb.restapi.template.config;

import javax.servlet.Filter;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.ServletRegistration.Dynamic;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.filter.CharacterEncodingFilter;

import org.springframework.web.servlet.DispatcherServlet;

import org.springframework.web.filter.DelegatingFilterProxy;
import org.springframework.web.servlet.support.AbstractAnnotationConfigDispatcherServletInitializer;

public class AppInitializer extends AbstractAnnotationConfigDispatcherServletInitializer {
    
    private static final Logger _logger = LoggerFactory.getLogger(AppInitializer.class);
    
    @Override
    protected Class<?>[] getRootConfigClasses() {
        return new Class[] { MainConfiguration.class };
    }
    
    @Override
    protected Class<?>[] getServletConfigClasses() {
        return new Class[] { WebConfiguration.class };
    }
    
    @Override
    protected String[] getServletMappings() {
        return new String[] { "/" };
    }
    
    @Override
    protected String getServletName() {
        return "restAPI-dispatcherServlet";
    }
    
//    
    @Override
    protected void customizeRegistration(Dynamic registration) {
        boolean set = registration.setInitParameter("throwExceptionIfNoHandlerFound", "true");
        
        _logger.debug("throwExceptionIfNoHandlerFound set: {}", set);
    }
    
    @Override
    protected DispatcherServlet createDispatcherServlet(WebApplicationContext servletAppContext) {
        DispatcherServlet ds = super.createDispatcherServlet(servletAppContext);
        
        _logger.debug("createDispatcherServlet: {}", ds.getServletName());
        
        return ds;
    }
    
    @Override
    protected Filter[] getServletFilters() {
        return new Filter[] { 
                createEncodigFilter(), 
                new DelegatingFilterProxy("apiAccessFilter"), 
                new DelegatingFilterProxy("apiRequestStatisticsFilter") 
        };
    }
    
    @Override
    public void onStartup(ServletContext servletContext) throws ServletException {
        super.onStartup(servletContext);
        
    }
    
//    @Override
//    public void onStartup(ServletContext servletContext) throws ServletException {
//        AnnotationConfigWebApplicationContext ctx = new AnnotationConfigWebApplicationContext();
//        ctx.register(getRootConfigClasses());
//        ctx.setServletContext(servletContext);
//        Dynamic dynamic = servletContext.addServlet("dispatcher", new DispatcherServlet(ctx));
//        dynamic.addMapping("/");
//        dynamic.setLoadOnStartup(1);
//    }
    
    private CharacterEncodingFilter createEncodigFilter() {
        CharacterEncodingFilter filter = new CharacterEncodingFilter();
        filter.setEncoding("UTF-8");
        filter.setForceEncoding(true);
        return filter;
    }
    
}
