package de.jawb.restapi.template.controller.web;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.mvc.method.RequestMappingInfo;
import org.springframework.web.servlet.mvc.method.annotation.RequestMappingHandlerMapping;

@Controller
public class RestApiDocController {
    
    private static final Logger          _logger = LoggerFactory.getLogger(RestApiDocController.class);
                                                 
    @Autowired
    private RequestMappingHandlerMapping _handlerMapping;
                                         
    @RequestMapping(value = "/home", method = RequestMethod.GET)
    public String index(HttpServletRequest request, Model model) {
        
        _logger.info("index");
        
        Map<RequestMappingInfo, HandlerMethod> handlerMethods = _handlerMapping.getHandlerMethods();
        
//        List<MappingInfo> miList = new ArrayList<>();
//        for (Entry<RequestMappingInfo, HandlerMethod> o : handlerMethods.entrySet()) {
//            
//            RequestMappingInfo mapping = o.getKey();
//            HandlerMethod method = o.getValue();
//            
//            for (String url : mapping.getPatternsCondition().getPatterns()) {
//                miList.add(new MappingInfo(url, mapping.getMethodsCondition()));
//            }
//            
//        }
//        
//        for (MappingInfo mappingInfo : miList) {
//            _logger.debug("{}", mappingInfo);
//        }
        
        model.addAttribute("handlerMethods", handlerMethods.keySet());
        
        return "home";
    }
    
    public static class MappingInfo {
        
        String url, method;
        
        /**
         * @param url
         * @param method
         */
        public MappingInfo(String url, String method) {
            super();
            this.url = url;
            this.method = method;
        }
        
        public String getUrl() {
            return url;
        }
        
        public void setUrl(String url) {
            this.url = url;
        }
        
        public String getMethod() {
            return method;
        }
        
        public void setMethod(String method) {
            this.method = method;
        }
        
        @Override
        public String toString() {
            return "MappingInfo [method=" + method + ", url=" + url + "]";
        }
        
    }
    
}
