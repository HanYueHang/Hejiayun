package com.mashibing.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;
@Configuration
public class CorsConfig {
    private CorsConfiguration buildConfig(){
        System.out.println("===CorsConfiguration===");
        CorsConfiguration configuration = new CorsConfiguration();
        //设置属性
        //允许跨域请求的地址（*）表示所有
        configuration.addAllowedOriginPattern("*");
        //允许跨域的请求头（*）addAllowedOrigin表示所有
        configuration.addAllowedHeader("*");
        //允许跨域请求的方式 （*）表示所有
        configuration.addAllowedMethod("*");
        //表示跨域请求的时候是不是同一个session
        configuration.setAllowCredentials(true);
        return configuration;
    }
@Bean
    public CorsFilter corsFilter(){
        System.out.println("====corsFilter====");
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**",buildConfig());
        return new CorsFilter(source);
    }

}
