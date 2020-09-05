package com.baozi.easyps.config;

import com.baozi.easyps.component.MyLocalResolver;
import com.baozi.easyps.utils.PhotoHandingUtil;
import com.baozi.easyps.utils.SendEmailUtil;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.LocaleResolver;

@Configuration
public class UnitConfig {

    @Bean()
    public LocaleResolver localeResolver() {
        return new MyLocalResolver();
    }

    @Bean
    public SendEmailUtil sendEmail() {
        return new SendEmailUtil();
    }

    @Bean
    public PhotoHandingUtil photoHandingUtil() {
        return new PhotoHandingUtil();
    }

}
