package com.github.simp7.commentdict;

import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import javax.servlet.Filter;

@Configuration
public class WebMvcConfiguration implements WebMvcConfigurer {

    @Bean
    public FilterRegistrationBean<Filter> filterBean() {

        FilterRegistrationBean<Filter> registrationBean = new FilterRegistrationBean<Filter>(new EncodingFilter());
        registrationBean.addUrlPatterns("/*");

        return registrationBean;
    }
}