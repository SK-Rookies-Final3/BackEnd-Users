package com.users.common.config;

import com.users.common.interceptor.AuthorizationInterceptor;
import com.users.common.interceptor.LoggerInterceptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.util.List;

@Configuration
public class WebMvcConfig implements WebMvcConfigurer {

    @Autowired
    private AuthorizationInterceptor authorizationInterceptor;

    private static final List<String> OPEN_API = List.of(
            "/open-api/**"
    );

    private static final List<String> DEFAULT_EXCLUDE = List.of(
            "/",
            "/favicon.ico",
            "/error"
    );

    private static final List<String> SWAGGER = List.of(
            "/swagger-ui.html",
            "/swagger-ui/**",
            "/v1/api-docs/**"
    );

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        // LoggerInterceptor 등록
        registry.addInterceptor(new LoggerInterceptor());

        // AuthorizationInterceptor 등록
        registry.addInterceptor(authorizationInterceptor)
                .excludePathPatterns("/public/**", "/open-api/**")
                .excludePathPatterns("/static/**", "/css/**", "/js/**", "/images/**")
                .excludePathPatterns(SWAGGER.toArray(new String[0]))
                .excludePathPatterns(DEFAULT_EXCLUDE.toArray(new String[0]));
    }

    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/**")
                .allowedOriginPatterns("*")
                .allowedMethods("GET", "POST", "DELETE", "PUT", "PATCH")
                .allowedHeaders("*") // 허용할 헤더
                .allowCredentials(true);
    }
}
