package com.secretty.core.config;

import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

@Configuration
@ComponentScan({"com.secretty.core"})
@Slf4j
public class LicenseCoreAutoConfigure {

    public LicenseCoreAutoConfigure(){
        log.info("============ license-core-spring-boot-starter initialization Complete！ ===========");
    }
}
