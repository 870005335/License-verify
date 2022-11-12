package com.secretty.application.listener;

import com.secretty.core.exception.LicenseCommonException;
import com.secretty.core.model.creator.LicenseExtraParam;
import com.secretty.verify.annotation.listener.CustomVerifyListener;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class RegisterVerifyListener extends CustomVerifyListener {

    // 单例Bean,这个静态变量会缓存证书中最大用户注册数
    private static Long registerAmount = 0L;

    @Override
    public boolean verify(LicenseExtraParam extraParam) throws LicenseCommonException {
        registerAmount = extraParam.getRegisterAmount();
        if (extraParam.isRegisterCheck()) {
            log.info("======= 自定义证书验证监听器A 实现verify方法  =======");
            log.info("======= 证书最大验证人数是：{} =======", registerAmount);
            // 这里，可以做一些和业务系统有关的参数验证
            // 比如在XX接口（Controller）上加@VLicense注解时，会触发这个方法的调用
            // 在这个业务系统这个监听方法中，我们可以先去user表查询下，当前系统的注册用户数量
            // 然后拿着这个数量和lic参数中约束的最大注册用户量进行比较，如果>，则抛出异常
            // 这样做的目的是防止业务系统部署到客户端本地服务器上时，数据库是公开的，防止对方通过手动改表来添加用户
            long count = 0;
            if (count >= registerAmount) {
                throw new LicenseCommonException("系统当前用户数超过最大用户注册限制数【"+registerAmount+"】");
            }
        }
        return true;
    }

    /**
     * 这个单独的重载的验证方法，主要是用于业务系统中进行调用，带参数的verify方法用于注解
     * 而这个不带参数的verify，更加灵活些,比如在系统中注册接口（Controller）上，除了@LicenseVerify注解外，
     * 可以在调用这个方法，来额外的验证注册人数是否已满
     * @throws LicenseCommonException
     */
    public boolean verify() throws LicenseCommonException {
        long count = 1001;
        if (count >= registerAmount) {
            throw new LicenseCommonException("系统当前用户数超过最大用户注册限制数【"+registerAmount+"】");
        }
        return true;
    }
}
