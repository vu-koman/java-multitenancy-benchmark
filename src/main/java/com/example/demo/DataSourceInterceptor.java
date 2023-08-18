package com.example.demo;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

@ConditionalOnProperty(name = "multitenant.datasource", havingValue = "true")
@Component
public class DataSourceInterceptor implements HandlerInterceptor {

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        String tenant = request.getHeader("tenant");
        MTContextHolder.setMTLabel(Integer.parseInt(tenant));
        return true;
    }
}
