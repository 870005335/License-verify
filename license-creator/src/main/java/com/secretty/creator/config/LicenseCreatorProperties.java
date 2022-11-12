package com.secretty.creator.config;

import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.boot.context.properties.ConfigurationProperties;

import java.io.File;

@ConfigurationProperties(prefix = "springboot.license.generate")
@NoArgsConstructor
@Data
public class LicenseCreatorProperties {

    private String storePath;

    private String privateKeyStorePath;

    private String licenseStorePath;

    public void setStorePath(String storePath) {
        this.storePath = storePath;
        File file = new File(storePath);
        if(!file.exists()){
            file.mkdirs();
        }
    }
}
