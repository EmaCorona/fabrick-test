package it.corona.fabrick.interceptor;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.NonNull;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

@Slf4j
@Component
public class RequestInterceptor implements HandlerInterceptor {

    private static final String START_TIME = "START_TIME";

    @Override
    public boolean preHandle(HttpServletRequest request, @NonNull HttpServletResponse response, @NonNull Object handler) {
        log.info("Pre handle of {} request: {}", request.getMethod(), request.getRequestURI());
        request.setAttribute(START_TIME, System.currentTimeMillis());
        return true;
    }

    @Override
    public void afterCompletion(HttpServletRequest request, @NonNull HttpServletResponse response, @NonNull Object handler, Exception ex) {
        Long startTime = (Long) request.getAttribute(START_TIME);
        log.info("Request {}: {} completed in {} ms", request.getMethod(), request.getRequestURI(), System.currentTimeMillis() - startTime);
    }
}
