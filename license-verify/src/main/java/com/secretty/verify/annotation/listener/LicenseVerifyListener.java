package com.secretty.verify.annotation.listener;

import com.secretty.core.model.creator.LicenseResult;
import com.secretty.core.model.manager.LicenseVerifyManager;
import com.secretty.verify.config.LicenseVerifyProperties;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.util.DigestUtils;
import org.springframework.util.ResourceUtils;

import java.io.File;
import java.io.FileInputStream;

/**
 * <p>项目启动时安装证书&定时检测lic变化，自动更替lic</p>
 *
 * @author maktub
 */
@Component
@Slf4j
public class LicenseVerifyListener implements ApplicationListener<ContextRefreshedEvent> {

    @Autowired
    private LicenseVerifyProperties properties;

    // 文件MD5值：唯一标识
    private static String MD5 = "";

    private static boolean isLoad = false;

    @Override
    public void onApplicationEvent(ContextRefreshedEvent event) {
        if (StringUtils.isNotBlank(properties.getLicensePath())) {
            // 安装证书
            this.install();
            try {
                // 记录文件MD5值
                String readMD5 = getFileMD5(properties.getLicensePath());
                isLoad = true;
                if (StringUtils.isBlank(LicenseVerifyListener.MD5)) {
                    MD5 = readMD5;
                }
            } catch (Exception e) {
               log.error("获取证书文件MD5值失败");
            }
        }
    }

    @Scheduled(cron = "0/30 * * * * ?")
    protected void timer() throws Exception {
        log.info("======= 检测证书内容是否发生变化 =======");
        if(!isLoad){
            return;
        }
        String readMd5 = getFileMD5(properties.getLicensePath());
        // 不相等，说明lic变化了
        if(!readMd5.equals(LicenseVerifyListener.MD5)){

            log.info("=============== 开始安装证书 ===============");
            install();
            log.info("=============== 安装证书成功 ===============");
            LicenseVerifyListener.MD5 = readMd5;
        }
    }

    private void install() {
        log.info("=============== 开始安装证书 ===============");
        LicenseVerifyManager licenseVerifyManager = new LicenseVerifyManager();
        LicenseResult licenseResult = licenseVerifyManager.install(properties.getVerifyParam());
        if (licenseResult.getResult()) {
            log.info("=============== 安装证书成功 ===============");
        } else {
            log.info("=============== 安装证书失败 ===============");
        }
    }

    /**
     * <p>获取文件的md5</p>
     */
    public String getFileMD5(String filePath) throws Exception{
        String MD5 = "";
        File file = ResourceUtils.getFile(filePath);
        if (file.exists()) {
            FileInputStream is = new FileInputStream(file);
            byte[] data = new byte[is.available()];
            is.read(data);
            MD5 = DigestUtils.md5DigestAsHex(data);
            is.close();
        }
        return MD5;
    }

}
