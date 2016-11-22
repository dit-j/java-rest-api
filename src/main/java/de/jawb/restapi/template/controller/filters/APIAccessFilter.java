package de.jawb.restapi.template.controller.filters;

import java.io.IOException;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

import javax.annotation.PostConstruct;
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

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import de.jawb.restapi.template.controller.api.response.APIResponse;
import de.jawb.restapi.template.service.access.IAccessService;

/**
 * @author dit
 */
@Component(value = "apiAccessFilter")
public class APIAccessFilter implements Filter {

    private String              ERR_403;

    private static final Logger _logger = LoggerFactory.getLogger(APIAccessFilter.class);

    @Autowired
    private IAccessService      _accessService;

    @Value(value = "${security.allowedRequestMethods}")
    private String              _allowedMethodsConfig;

    private Set<String>         _allowedMethods;

    @PostConstruct
    public void init() {
        _logger.debug("init");
        if (_allowedMethodsConfig != null && !_allowedMethodsConfig.isEmpty()) {
            _allowedMethods = new HashSet<>(Arrays.asList(_allowedMethodsConfig.split(",")));
            _logger.info("ALLOWED-METHODS: {}", _allowedMethods);
        } else {
            _logger.info("ALLOWED-METHODS: {}", Arrays.toString(HttpMethod.values()));
        }
        try {
            ERR_403 = new ObjectMapper().writeValueAsString(APIResponse.internalError("403 - access denied"));
        } catch (JsonProcessingException e) {
            ERR_403 = "403";
        }
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {

        final String requestURI = ((HttpServletRequest) request).getRequestURI();
        final String requestMETHOD = ((HttpServletRequest) request).getMethod();

        _logger.debug("doFilter {}: {}", requestMETHOD, requestURI);

        if (requestURI.endsWith("/home") && "GET".equals(requestMETHOD)) {
            chain.doFilter(request, response);
        } else if (_hasValidApiKey(request) && _hasValidRequestMethod(requestMETHOD)) {
            chain.doFilter(request, response);
        } else {
            _logger.warn("\t send 403");
            HttpServletResponse resp = (HttpServletResponse) response;
            resp.setStatus(HttpStatus.FORBIDDEN.value());
            resp.getOutputStream().write(ERR_403.getBytes());
        }
    }

    @Override
    public void destroy() {}

    private boolean _hasValidRequestMethod(String method) {
        if (_allowedMethods == null)
            return true;
        return _allowedMethods.contains(method);
    }

    private boolean _hasValidApiKey(ServletRequest request) {
        HttpServletRequest httpRequest = (HttpServletRequest) request;
        final String apiKey = httpRequest.getHeader("API-KEY");

        if (apiKey != null && !apiKey.isEmpty()) {
            return _accessService.isValidKey(apiKey);
        }

        return false;
    }

    @Override
    public void init(FilterConfig arg0) throws ServletException {}
}
