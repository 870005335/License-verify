package com.secretty.core.model.manager;

import com.secretty.core.helper.ParamInitHelper;
import com.secretty.core.model.creator.LicenseResult;
import com.secretty.core.model.manager.LicenseCustomManager;
import com.secretty.core.model.verify.LicenseVerifyParam;
import com.secretty.core.utils.DateUtils;
import de.schlichtherle.license.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.util.ResourceUtils;

import java.io.File;
import java.text.DateFormat;
import java.text.MessageFormat;
import java.text.SimpleDateFormat;

/**
 * <p>License校验类</p>
 *
 * @author maktub
 */
@Slf4j
public class LicenseVerifyManager {

    /**
     * <p>安装License证书</p>
     * @param param License校验类需要的参数
     */
    public synchronized LicenseResult install(LicenseVerifyParam param) {
        try {
            // 1、初始化License证书参数
            LicenseParam licenseParam = ParamInitHelper.initLicenseParam(param);
            // 2、创建自定义License证书管理器对象
            LicenseCustomManager licenseManager = new LicenseCustomManager(licenseParam);
            // 3、获取要安装的证书文件
            File licenseFile = ResourceUtils.getFile(param.getLicensePath());
            // 4、如果之前安装过证书，先卸载之前的证书 == 给null
            licenseManager.uninstall();
            // 5、开始安装
            LicenseContent content = licenseManager.install(licenseFile);
            String message = MessageFormat.format("证书安装成功，证书有效期：{0} - {1}",
                    DateUtils.date2Str(content.getNotBefore()), DateUtils.date2Str(content.getNotAfter()));
            log.info(message);
            return LicenseResult.success(message, content);
        } catch (LicenseContentException contentExc){
            String message = contentExc.getMessage();
            log.error(message);
            return LicenseResult.fail(message, contentExc);
        } catch (Exception e){
            log.error(e.getMessage(),e);
            return LicenseResult.fail(e.getMessage(), e);
        }
    }

    /**
     * <p>校验License证书</p>
     * @param param License校验类需要的参数
     */
    public LicenseResult verify(LicenseVerifyParam param) {
        // 1、初始化License证书参数
        LicenseParam licenseParam = ParamInitHelper.initLicenseParam(param);
        // 2、创建License证书管理器对象
        LicenseManager licenseManager = new LicenseCustomManager(licenseParam);
        DateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        // 3、开始校验证书
        try {
            LicenseContent licenseContent = licenseManager.verify();
            String message = MessageFormat.format("证书校验通过，证书有效期：{0} - {1}",
                    format.format(licenseContent.getNotBefore()),format.format(licenseContent.getNotAfter()));
            log.info(message);
            return LicenseResult.success(message, licenseContent);
        } catch (NoLicenseInstalledException ex){
            String message = "证书未安装！";
            log.error(message,ex);
            return LicenseResult.fail(message, ex);
        } catch (LicenseContentException cex){
            log.error(cex.getMessage(),cex);
            return LicenseResult.fail(cex.getMessage(), cex);
        } catch (Exception e){
            String message = "证书校验失败！";
            log.error(e.getMessage(), e);
            return LicenseResult.fail(message, e);
        }
    }
}
