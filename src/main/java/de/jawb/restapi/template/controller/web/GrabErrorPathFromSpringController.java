package de.jawb.restapi.template.controller.web;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("${error.path:/error}")
public class GrabErrorPathFromSpringController {    
}
