package com.companyhr;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
@EnableWebMvc

public class MvcConfig implements WebMvcConfigurer {


    public void addViewControllers(ViewControllerRegistry registry) {
        registry.addViewController("/").setViewName("welcome");
        registry.addViewController("/home").setViewName("home");
        registry.addViewController("/login").setViewName("login");
        registry.addViewController("/notes").setViewName("notes");
        registry.addViewController("/greeting").setViewName("greeting");
        registry.addViewController("/result").setViewName("result");
        registry.addViewController("/registration").setViewName("registration");
        registry.addViewController("/welcome").setViewName("welcome");
        registry.addViewController("/restricted/afterlogin").setViewName("/restricted/afterlogin");
        registry.addViewController("/accesdenied").setViewName("accesdenied");
        //registry.addViewController("/logout").setViewName("logout");


    }

    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler(
                "/webjars/**",
                "/img/**",
                "/css/**",
                "/js/**")
                .addResourceLocations(
                        "classpath:/META-INF/resources/webjars/",
                        "classpath:/static/img/",
                        "classpath:/static/css/",
                        "classpath:/static/js/");
    }

}



