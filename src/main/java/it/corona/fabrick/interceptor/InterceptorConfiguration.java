package it.corona.fabrick.interceptor;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Slf4j
@Configuration
@RequiredArgsConstructor
public class InterceptorConfiguration implements WebMvcConfigurer {

    @Value("${cors.origins}")
    private String allowedOrigins;

    @Value("${cors.methods}")
    private String[] allowedMethods;

    private final RequestInterceptor requestInterceptor;

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(requestInterceptor).addPathPatterns("/**");
    }

    @Override
    public void addCorsMappings(CorsRegistry registry) {
        log.info("Cors enabled for methods: {}, from origins: {}", allowedMethods, allowedOrigins);
        CorsRegistration corsRegistration = registry.addMapping("/**");
        corsRegistration.allowedOrigins(allowedOrigins);
        corsRegistration.allowedMethods(allowedMethods);
    }
}
