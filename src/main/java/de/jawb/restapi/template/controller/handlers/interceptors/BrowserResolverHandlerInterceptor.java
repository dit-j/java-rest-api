package de.jawb.restapi.template.controller.handlers.interceptors;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import eu.bitwalker.useragentutils.UserAgent;
import eu.bitwalker.useragentutils.Version;

/**
 * Dieser Interceptor ermittelt den {@link BrowserInfo} eines Clients und setzt diesen als Request-Attribute
 *
 * @author dit
 */
@Component
public class BrowserResolverHandlerInterceptor extends HandlerInterceptorAdapter {

    private static final Logger logger = LoggerFactory.getLogger(BrowserResolverHandlerInterceptor.class);

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        String userAgent = request.getHeader("User-Agent");
        logger.debug("User-Agent: {}", userAgent);

        UserAgent ua = UserAgent.parseUserAgentString(userAgent);
        Version browserVersion = ua.getBrowserVersion();
        String browserName = ua.getBrowser().toString();

        try {
            int majVersion = Integer.parseInt(browserVersion.getMajorVersion());
            request.setAttribute("browserInfo", new BrowserInfo(browserName, majVersion));
        } catch (Exception e) {
            request.setAttribute("browserInfo", new BrowserInfo(browserName, -1));
        }

        return true;
    }

    /**
     * @author dit
     */
    public static class BrowserInfo {

        private final String name;
        private final int    version;

        public BrowserInfo(String name, int version) {
            this.name = name;
            this.version = version;
        }

        public String getName() {
            return name;
        }

        public int getVersion() {
            return version;
        }

        @Override
        public String toString() {
            return "BrowserInfo [name=" + name + ", version=" + version + "]";
        }

    }
}
