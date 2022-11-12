package com.secretty.core.model.manager;

import com.secretty.core.exception.LicenseCommonException;
import com.secretty.core.helper.ParamInitHelper;
import com.secretty.core.model.creator.LicenseCreatorParam;
import com.secretty.core.model.creator.LicenseResult;
import de.schlichtherle.license.LicenseContent;
import de.schlichtherle.license.LicenseManager;
import de.schlichtherle.license.LicenseParam;
import lombok.extern.slf4j.Slf4j;

import java.io.File;
import java.io.InputStream;
import java.nio.file.Files;
import java.text.MessageFormat;

@Slf4j
public class LicenseCreatorManager {

    private LicenseCreatorParam param;

    public LicenseCreatorManager(LicenseCreatorParam param) {
        this.param = param;
    }

    /**
     * <p>生成License证书</p>
     * @return GxLicenseResult 证书生成结果
     */
    public LicenseResult generateLicense() {
        try {
            // 1、根据外部传入的创建Lic的参数信息初始化lic参数（秘钥部分）
            LicenseParam licenseParam = ParamInitHelper.initLicenseParam(param);
            // 2、根据外部传入的创建Lic的属性信息初始化lic内容（除了truelicense自带的还包括自己定义的）
            LicenseContent licenseContent = ParamInitHelper.initLicenseContent(param);
            // 3、构建Lic管理器
            LicenseManager licenseManager = new LicenseCustomManager(licenseParam);
            // 4、根据param传入的lic生成的路径创建空文件
            File licenseFile = new File(this.param.getLicensePath());
            // 如果文件存在 删除
            licenseFile.deleteOnExit();
            // 5、通过Lic管理器，将内容写入Lic文件中
            licenseManager.store(licenseContent, licenseFile);
            return LicenseResult.success("证书生成成功！",licenseContent);
        } catch (Exception e) {
            String message = MessageFormat.format("证书生成失败！：{0}", param);
            log.error(message,e);
            return LicenseResult.fail(message, e);
        }
    }

    /**
     * <p>下载License证书</p>
     * @return InputStream 证书文件输入流
     * @throws Exception 证书下载失败
     */
    public InputStream download() throws Exception {
        try {
            LicenseParam licenseParam = ParamInitHelper.initLicenseParam(param);
            LicenseContent licenseContent = ParamInitHelper.initLicenseContent(param);
            LicenseManager licenseManager = new LicenseCustomManager(licenseParam);
            File licenseFile = new File(param.getLicensePath());
            licenseManager.store(licenseContent,licenseFile);
            return Files.newInputStream(licenseFile.toPath());
        }catch (Exception e){
            e.printStackTrace();
            log.error(e.getMessage());
            log.error(MessageFormat.format("证书下载失败：{0}",param),e);
            throw new LicenseCommonException(e.getMessage());
        }
    }
}
