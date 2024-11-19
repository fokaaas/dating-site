package org.spring.datingsite;

import org.spring.datingsite.auth.filter.AuthFilter;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class DatingSiteApplication {

    public static void main(String[] args) {
        SpringApplication.run(DatingSiteApplication.class, args);
    }

    @Bean
    public FilterRegistrationBean<AuthFilter> loggingFilter(){
        FilterRegistrationBean<AuthFilter> registrationBean
                = new FilterRegistrationBean<>();

        registrationBean.setFilter(getAuthFilter());
        registrationBean.addUrlPatterns("/users/*");
        registrationBean.setName("AuthFilter");
        registrationBean.setOrder(1);

        return registrationBean;
    }

    @Bean
    public AuthFilter getAuthFilter() {
        return new AuthFilter();
    }
}
