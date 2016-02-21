package de.jawb.restapi.template.controller.filters;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * @author dit
 */
@Component(value = "apiRequestStatisticsFilter")
public class APIRequestStatisticsFilter implements Filter {

//    private static final Logger _logger = LoggerFactory.getLogger(APIRequestStatisticsFilter.class);
    
    @Autowired
    private RequestStatistics _statistics;
    
    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
    }
    
    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        
        _statistics.mark(((HttpServletRequest) request).getRequestURI());
        
        chain.doFilter(request, response);
    }
    
    @Override
    public void destroy() {
    }
    
}
