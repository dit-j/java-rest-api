package de.jawb.restapi.template.controller.filters;

import java.io.IOException;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;

import de.jawb.restapi.template.service.access.IAccessService;

/**
 * @author dit
 */
@Component(value = "apiAccessFilter")
public class APIAccessFilter implements Filter {
    
    private static final Logger _logger = LoggerFactory.getLogger(APIAccessFilter.class);
                                        
    @Autowired
    private IAccessService      _accessService;
                                
    @Value(value = "${security.allowedRequestMethods}")
    private String              _allowedMethodsConfig;
                                
    private Set<String>         _allowedMethods;
                                
    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        if (_allowedMethodsConfig != null && !_allowedMethodsConfig.isEmpty()) {
            _allowedMethods = new HashSet<>(Arrays.asList(_allowedMethodsConfig.split(",")));
            _logger.info("ALLOWED-METHODS: {}", _allowedMethods);
        } else {
            _logger.info("ALLOWED-METHODS: {}", HttpMethod.values());
        }
    }
    
    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        
        final String requestURI = ((HttpServletRequest) request).getRequestURI();
        final String requestMETHOD = ((HttpServletRequest) request).getMethod();
        
        _logger.debug("doFilter {}: {}",requestMETHOD, requestURI);
        
        if (requestURI.endsWith("/home") && "GET".equals(requestMETHOD)) {
            chain.doFilter(request, response);
        } else if (_hasValidApiKey(request) && _hasValidRequestMethod(requestMETHOD)) {
            chain.doFilter(request, response);
        } else {
            _logger.warn("\t send 403");
            ((HttpServletResponse) response).sendError(HttpStatus.FORBIDDEN.value());
        }
    }
    
    @Override
    public void destroy() {
    }
    
    private boolean _hasValidRequestMethod(String method) {
        if (_allowedMethods == null) return true;
        return _allowedMethods.contains(method);
    }
    
    private boolean _hasValidApiKey(ServletRequest request) {
        String apiKey = ((HttpServletRequest) request).getHeader("API-KEY");
        return apiKey != null && apiKey.length() > 0 && _accessService.isValidKey(apiKey);
    }
}
