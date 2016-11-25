package de.jawb.restapi.template.controller.filters;

import org.springframework.stereotype.Component;
import org.springframework.web.filter.CharacterEncodingFilter;

@Component(value = "apiEncodingFilter")
public class APIEncodingFilter extends CharacterEncodingFilter {

    public APIEncodingFilter() {
        setEncoding("UTF-8");
        setForceEncoding(true);
    }
}
