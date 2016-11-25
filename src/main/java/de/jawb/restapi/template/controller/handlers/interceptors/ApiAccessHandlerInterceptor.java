/**
 *
 */
package de.jawb.restapi.template.controller.handlers.interceptors;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import de.jawb.restapi.template.model.access.ApiAccess;
import de.jawb.restapi.template.service.access.IAccessService;

/**
 * Dieser Interceptor ermittelt ein {@link ApiAccess}
 * @author dit (21.02.2016)
 */
@Component
public class ApiAccessHandlerInterceptor extends HandlerInterceptorAdapter {

    private static final Logger logger = LoggerFactory.getLogger(ApiAccessHandlerInterceptor.class);

    @Autowired
    private IAccessService      _accessService;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        String key = getApiKey(request);
        logger.debug("setApiAccess Object for {}", key);
        request.setAttribute("apiAccess", _accessService.getValidApiAccess(key));
        return super.preHandle(request, response, handler);
    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {
        String key = getApiKey(request);
        logger.debug("incrementRequestCount for {}", key);
        _accessService.incrementRequestCount(key);
        super.postHandle(request, response, handler, modelAndView);
    }

    private String getApiKey(HttpServletRequest request) {
        return request.getHeader("API-KEY");
    }

}
