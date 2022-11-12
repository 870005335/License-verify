package com.secretty.verify.config;


import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

/**
 * <p>License验证模块自动扫包/装配Bean实例</p>
 *
 * @author maktub
 */
@Configuration
@ComponentScan(basePackages = {"com.secretty.verify"})
@Slf4j
public class LicenseVerifyAutoConfigure {

    public LicenseVerifyAutoConfigure(){
        log.info("============ license-verify-spring-boot-starter initialization Complete！ ===========");
    }
}
