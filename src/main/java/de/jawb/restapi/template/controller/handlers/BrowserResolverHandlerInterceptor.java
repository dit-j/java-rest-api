package de.jawb.restapi.template.controller.handlers;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import eu.bitwalker.useragentutils.UserAgent;
import eu.bitwalker.useragentutils.Version;

public class BrowserResolverHandlerInterceptor extends HandlerInterceptorAdapter {
    
    private static final Logger logger = LoggerFactory.getLogger(BrowserResolverHandlerInterceptor.class);
    
    public BrowserResolverHandlerInterceptor() {
    }
    
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        
        String userAgent = request.getHeader("user-agent");
        
        logger.debug("user-agent: {}", userAgent);
        
        UserAgent ua = UserAgent.parseUserAgentString(userAgent);
        Version browserVersion = ua.getBrowserVersion();
        String browserName = ua.getBrowser().toString();

        try {
            int majVersion = Integer.parseInt(browserVersion.getMajorVersion());
            BrowserInfo browser = new BrowserInfo(browserName, majVersion);
            request.setAttribute("browserInfo", browser);
        } catch (Exception e) {
            //
        }
        
        return true;
    }
    
    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {
    }
    
    /**
     * @author dikr
     */
    public static class BrowserInfo {
        
        private String name;
        private int    version;
                       
        public BrowserInfo() {
        }
        
        public BrowserInfo(String name, int version) {
            this.name = name;
            this.version = version;
        }
        
        public String getName() {
            return name;
        }
        
        public void setName(String name) {
            this.name = name;
        }
        
        public int getVersion() {
            return version;
        }
        
        public void setVersion(int version) {
            this.version = version;
        }
        
        @Override
        public String toString() {
            return "BrowserInfo [name=" + name + ", version=" + version + "]";
        }
        
    }
}
