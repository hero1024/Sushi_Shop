package com.example.demo.config;

import org.dozer.DozerBeanMapper;
import org.dozer.Mapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * mvc相关配置文件
 *
 * @author songpeijiang
 * @since 2020/6/10
 */
@Configuration
public class MvcConfig implements WebMvcConfigurer {

    @Bean
    public Mapper fooDozerBeanMapper() {

        return new DozerBeanMapper();
    }

}
