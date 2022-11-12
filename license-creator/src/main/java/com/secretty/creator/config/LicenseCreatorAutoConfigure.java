package com.secretty.creator.config;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

@Configuration
@ComponentScan(basePackages = {"com.secretty.creator"})
@EnableConfigurationProperties({LicenseCreatorProperties.class})
@Slf4j
public class LicenseCreatorAutoConfigure {

    public LicenseCreatorAutoConfigure(){
        log.info("============ license-creator-spring-boot-starter initialization Complete！ ===========");
    }
}
