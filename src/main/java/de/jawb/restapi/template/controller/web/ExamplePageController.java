package de.jawb.restapi.template.controller.web;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import de.jawb.restapi.template.service.user.IUserService;

@Controller
public class ExamplePageController {
    
    private static final Logger _logger = LoggerFactory.getLogger(ExamplePageController.class);
                                        
    @Autowired
    private IUserService         _uService;
                                
    @RequestMapping("/home")
    public String index(HttpServletRequest request, Model model) { 
        
        _logger.info("index");
        
        _uService.saveNewUser("dit", "k");
        
        return "home";
    }
}
