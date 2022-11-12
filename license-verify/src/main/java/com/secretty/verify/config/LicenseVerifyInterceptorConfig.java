package com.secretty.verify.config;

import com.secretty.verify.interceptor.LicenseVerifyInterceptor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * <p>License拦截器配置类</p>
 *
 * @author maktub
 */
@Configuration
public class LicenseVerifyInterceptorConfig implements WebMvcConfigurer {

    @Bean
    public LicenseVerifyInterceptor getLicenseCheckInterceptor() {
        return new LicenseVerifyInterceptor();
    }

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(this.getLicenseCheckInterceptor())
                .addPathPatterns("/**")
                //.excludePathPatterns() // 添加排除路径
        ;
    }
}
