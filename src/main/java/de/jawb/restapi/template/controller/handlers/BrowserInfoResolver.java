package de.jawb.restapi.template.controller.handlers;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.MethodParameter;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.ModelAndViewContainer;

import de.jawb.restapi.template.controller.handlers.BrowserResolverHandlerInterceptor.BrowserInfo;

public class BrowserInfoResolver implements HandlerMethodArgumentResolver {
    
    private static final Logger logger = LoggerFactory.getLogger(BrowserInfoResolver.class);
    
    @Override
    public boolean supportsParameter(MethodParameter parameter) {
        return parameter.getParameterType().equals(BrowserInfo.class);
    }
    
    @Override
    public Object resolveArgument(MethodParameter parameter, ModelAndViewContainer mavContainer, NativeWebRequest webRequest, WebDataBinderFactory binderFactory) throws Exception {
        logger.info("resolveArgument");
        return (BrowserInfo) webRequest.getAttribute("browserInfo", RequestAttributes.SCOPE_REQUEST);
    }
}