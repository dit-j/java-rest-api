package de.jawb.restapi.template.config;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.support.ResourceBundleMessageSource;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.servlet.config.annotation.DefaultServletHandlerConfigurer;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.PathMatchConfigurer;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;
import org.springframework.web.servlet.view.InternalResourceViewResolver;
import org.springframework.web.servlet.view.JstlView;

import de.jawb.restapi.template.controller.handlers.argsresolvers.ApiAccessArgumentResolver;
import de.jawb.restapi.template.controller.handlers.argsresolvers.BrowserInfoArgumentResolver;
import de.jawb.restapi.template.controller.handlers.interceptors.ApiAccessHandlerInterceptor;
import de.jawb.restapi.template.controller.handlers.interceptors.BrowserResolverHandlerInterceptor;

@Configuration
@EnableWebMvc
public class WebConfiguration extends WebMvcConfigurerAdapter {

    @Autowired
    private ApiAccessHandlerInterceptor       accessCounterHandlerInterceptor;

    @Autowired
    private BrowserResolverHandlerInterceptor browserResolverHandlerInterceptor;

    @Bean
    public InternalResourceViewResolver internalResourceViewResolver() {
        InternalResourceViewResolver internalResourceViewResolver = new InternalResourceViewResolver();
        internalResourceViewResolver.setViewClass(JstlView.class);
        internalResourceViewResolver.setPrefix("/WEB-INF/views/");
        internalResourceViewResolver.setSuffix(".jsp");
        return internalResourceViewResolver;
    }

    @Bean
    public MessageSource messageSource() {
        String[] baseNames = "messages".split(","); // messages.properties
        ResourceBundleMessageSource resourceBundleMessageSource = new ResourceBundleMessageSource();
        resourceBundleMessageSource.setBasenames(baseNames);
        return resourceBundleMessageSource;
    }

    @Override
    public void configurePathMatch(PathMatchConfigurer configurer) {
        configurer.setUseSuffixPatternMatch(false);
        configurer.setUseTrailingSlashMatch(false);
    }

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler("/img/**").addResourceLocations("/web/assets/img/");
        registry.addResourceHandler("/js/**").addResourceLocations("/web/assets/js/");
        registry.addResourceHandler("/css/**").addResourceLocations("/web/assets/css/");
    }

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(accessCounterHandlerInterceptor).addPathPatterns("/v1/*");
        registry.addInterceptor(browserResolverHandlerInterceptor); // .addPathPatterns("/v1/*");
    }

    @Override
    public void addArgumentResolvers(List<HandlerMethodArgumentResolver> argumentResolvers) {
        argumentResolvers.add(new ApiAccessArgumentResolver());
        argumentResolvers.add(new BrowserInfoArgumentResolver());
        super.addArgumentResolvers(argumentResolvers);
    }

    @Override
    public void configureDefaultServletHandling(DefaultServletHandlerConfigurer configurer) {
        configurer.enable();
    }
}
