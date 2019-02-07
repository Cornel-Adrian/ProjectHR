package com.companyhr;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.format.datetime.DateFormatter;
import org.springframework.format.datetime.DateFormatterRegistrar;
import org.springframework.format.number.NumberFormatAnnotationFormatterFactory;
import org.springframework.format.support.DefaultFormattingConversionService;
import org.springframework.format.support.FormattingConversionService;
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
        registry.addViewController("/registrationdetails").setViewName("registrationdetails");
        registry.addViewController("/welcome").setViewName("welcome");
        registry.addViewController("/restricted/afterlogin").setViewName("/restricted/afterlogin");
        registry.addViewController("/restricted/viewpersonaldetails").setViewName("/restricted/viewpersonaldetails");
        registry.addViewController("/accesdenied").setViewName("accesdenied");
        registry.addViewController("/addholidayhr").setViewName("addholidayhr");
        registry.addViewController("/restricted/userhomepage").setViewName("/restricted/userhomepage");
        registry.addViewController("/adddayoff").setViewName("adddayoff");
        registry.addViewController("/setcredits").setViewName("setcredits");
        registry.addViewController("/viewvacancies").setViewName("viewvacancies");
        registry.addViewController("/restricted/hrhomepage").setViewName("/restricted/hrhomepage");
        registry.addViewController("/restricted/adminhomepage").setViewName("/restricted/adminhomepage");
        registry.addViewController("/restricted/viewcredits").setViewName("/restricted/viewcredits");

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

    @Bean
    public FormattingConversionService conversionService() {

        // Use the DefaultFormattingConversionService but do not register defaults
        DefaultFormattingConversionService conversionService = new DefaultFormattingConversionService(false);

        // Ensure @NumberFormat is still supported
        conversionService.addFormatterForFieldAnnotation(new NumberFormatAnnotationFormatterFactory());

        // Register date conversion with a specific global format
        DateFormatterRegistrar registrar = new DateFormatterRegistrar();
        registrar.setFormatter(new DateFormatter("yyyyMMdd"));
        registrar.registerFormatters(conversionService);

        return conversionService;
    }
}




