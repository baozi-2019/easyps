package com.baozi.easyps.config;

import com.baozi.easyps.intercepter.LoginHandlerIntercepter;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class ViewConfig implements WebMvcConfigurer {
    @Override
    public void addViewControllers(ViewControllerRegistry registry) {
        registry.addViewController("/fail").setViewName("fail");
        registry.addViewController("/success").setViewName("success");
        registry.addViewController("/registerFail").setViewName("registerFail");
    }

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(new LoginHandlerIntercepter()).addPathPatterns("/**")
                .excludePathPatterns("/", "/login", "/login.html", "/loginCheck"
                        , "/webjars/**", "/css/**", "/fail", "/reg/**", "/registerFail");
    }
}
