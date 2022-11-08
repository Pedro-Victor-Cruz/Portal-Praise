package br.com.praise.portal.configuration;

import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

public class ConfigurationView implements WebMvcConfigurer {

    public void addViewControllers(ViewControllerRegistry registry) {
        registry.addViewController("/login/login").setViewName("/login/login");
        registry.addViewController("/login/register").setViewName("/login/register");
        registry.addViewController("/home/index").setViewName("/home/index");
        registry.addViewController("/public/nav").setViewName("/public/nav");
        registry.addViewController("/public/header").setViewName("/public/header");
        registry.addViewController("/static/assets/**").setViewName("/static/assets/**");
    }

}
