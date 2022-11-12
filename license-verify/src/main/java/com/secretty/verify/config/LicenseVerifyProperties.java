package com.secretty.verify.config;

import com.secretty.core.model.verify.LicenseVerifyParam;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;


/**
 * <p>License验证属性类</p>
 *
 * @author maktub
 */
@Component
@ConfigurationProperties(prefix = "springboot.license.verify")
@Data
@NoArgsConstructor
public class LicenseVerifyProperties {

    private String subject;
    private String publicAlias;
    private String publicKeysStorePath = "";
    private String storePass = "";
    private String licensePath;


    public LicenseVerifyParam getVerifyParam() {
        LicenseVerifyParam param = new LicenseVerifyParam();
        param.setSubject(subject);
        param.setPublicAlias(publicAlias);
        param.setStorePass(storePass);
        param.setLicensePath(licensePath);
        param.setPublicKeysStorePath(publicKeysStorePath);
        return param;
    }
}
