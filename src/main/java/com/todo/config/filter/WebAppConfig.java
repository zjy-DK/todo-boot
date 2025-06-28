package com.todo.config.filter;

import com.todo.config.idempotent.IdempotentInterceptor;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import javax.annotation.Resource;

/**
 * @author zjy
 * @date 2025/02/13  20:09
 */
@Configuration
public class WebAppConfig implements WebMvcConfigurer {

    @Resource
    private IdempotentInterceptor idempotentInterceptor;
    @Resource
    private RequestLogInterceptor requestLogInterceptor;

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        //添加请求日志拦截器
        registry.addInterceptor(requestLogInterceptor).addPathPatterns("/**");
        //添加幂等性验证拦截器
        registry.addInterceptor(idempotentInterceptor).addPathPatterns("/**");
    }

    /**
     * 跨域配置
     */
    @Bean
    public FilterRegistrationBean<CorsFilter> corsFilter() {
        // 创建配置对象
        CorsConfiguration config = new CorsConfiguration();
        config.addAllowedOriginPattern("*");
        config.addAllowedHeader("*");
        config.addAllowedMethod("*");
        config.setMaxAge(1800L);

        // 注册路径
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", config);

        // 创建 CorsFilter 实例
        CorsFilter corsFilter = new CorsFilter(source);

        // 使用 FilterRegistrationBean 包裹并设置顺序
        FilterRegistrationBean<CorsFilter> bean = new FilterRegistrationBean<>(corsFilter);
        // 设置为最高优先级（数字越小优先级越高）
        bean.setOrder(0);
        return bean;
    }
}
