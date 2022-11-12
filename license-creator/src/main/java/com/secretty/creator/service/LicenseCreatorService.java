package com.secretty.creator.service;

import com.secretty.core.exception.LicenseCommonException;
import com.secretty.core.model.creator.LicenseCreatorParam;
import com.secretty.core.model.creator.LicenseResult;
import com.secretty.core.model.manager.LicenseCreatorManager;
import org.springframework.stereotype.Service;

@Service
public class LicenseCreatorService {

    /**
     * <p>生成证书</p>
     * @param param 证书创建需要的参数对象
     * @return Map<String,Object>
     */
    public void generateLicense(LicenseCreatorParam param) {
        LicenseCreatorManager licenseCreator = new LicenseCreatorManager(param);
        LicenseResult licenseResult = licenseCreator.generateLicense();
        if (!licenseResult.getResult()) {
            throw new LicenseCommonException("证书文件生成失败！");
        }
    }
}
