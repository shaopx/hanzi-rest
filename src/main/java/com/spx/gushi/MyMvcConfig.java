package com.spx.gushi;

import com.spx.gushi.intercepter.SecureIntercepter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

/**
 * Created by Administrator on 2016/11/20.
 */

@Configuration
@EnableWebMvc
@ComponentScan("com.spx.gushi")
public class MyMvcConfig extends WebMvcConfigurerAdapter {

    @Bean
    public SecureIntercepter secureIntercepter() {
        return new SecureIntercepter();
    }

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(secureIntercepter());
    }
}
