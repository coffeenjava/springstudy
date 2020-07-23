package com.brian.springstudy.config;

import com.brian.springstudy.support.convert.StringToEnumConverterFactory;
import org.springframework.context.annotation.Configuration;
import org.springframework.format.FormatterRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * 설정 적용 순서
 * configureMessageConverters
 * addFormatters
 * addArgumentResolvers
 * addInterceptors
 * addViewControllers
 */
@Configuration
public class WebMvcConfig implements WebMvcConfigurer {

    @Override
    public void addFormatters(FormatterRegistry registry) {
        registry.addConverterFactory(new StringToEnumConverterFactory());
//        registry.addConverter(new StringToLocalDateTimeConverter());
    }
}
