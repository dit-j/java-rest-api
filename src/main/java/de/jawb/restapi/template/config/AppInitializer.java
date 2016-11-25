package de.jawb.restapi.template.config;

import javax.servlet.Filter;
import javax.servlet.FilterRegistration;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.filter.DelegatingFilterProxy;
import org.springframework.web.servlet.DispatcherServlet;
import org.springframework.web.servlet.support.AbstractAnnotationConfigDispatcherServletInitializer;

public class AppInitializer extends AbstractAnnotationConfigDispatcherServletInitializer {

    private static final Logger _logger = LoggerFactory.getLogger(AppInitializer.class);

    @Override
    protected Class<?>[] getRootConfigClasses() {
        return new Class[] { MainConfiguration.class };
    }

    @Override
    protected Class<?>[] getServletConfigClasses() {
        return new Class[] {};
    }

    @Override
    protected String[] getServletMappings() {
        return new String[] { "/" };
    }

    @Override
    protected DispatcherServlet createDispatcherServlet(WebApplicationContext servletAppContext) {
        DispatcherServlet ds = new DispatcherServlet(servletAppContext);
        ds.setThrowExceptionIfNoHandlerFound(true);
        return ds;
    }

    @Override
    protected Filter[] getServletFilters() {
        return new Filter[] { /* */ };
    }

    @Override
    public void onStartup(ServletContext ctx) throws ServletException {
        registerFilter(ctx, "apiEncodingFilter",           new DelegatingFilterProxy("apiEncodingFilter"),          "/v1/*");
        registerFilter(ctx, "apiRequestStatisticsFilter",  new DelegatingFilterProxy("apiRequestStatisticsFilter"), "/v1/*");
        registerFilter(ctx, "apiAccessFilter",             new DelegatingFilterProxy("apiAccessFilter"),            "/v1/*");

        super.onStartup(ctx);
    }

    private void registerFilter(ServletContext ctx, String name, DelegatingFilterProxy filter, String url) {
        _logger.debug("registerFilter: {} for {}", filter, url);
        FilterRegistration.Dynamic registration = ctx.addFilter(name, filter);
        registration.addMappingForUrlPatterns(null, false, url);
    }


}
