package com.secretty.verify.interceptor;

import com.alibaba.fastjson2.JSON;
import com.alibaba.fastjson2.TypeReference;
import com.secretty.core.exception.LicenseCommonException;
import com.secretty.core.model.creator.LicenseExtraParam;
import com.secretty.core.model.creator.LicenseResult;
import com.secretty.core.model.manager.LicenseVerifyManager;
import com.secretty.core.utils.CommonUtils;
import com.secretty.verify.annotation.LicenseVerify;
import com.secretty.verify.annotation.listener.CustomVerifyListener;
import com.secretty.verify.config.LicenseVerifyProperties;
import de.schlichtherle.license.LicenseContent;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
import java.util.List;

/**
 * <p>License验证拦截器</p>
 *
 * @author maktub
 */
public class LicenseVerifyInterceptor implements HandlerInterceptor {

    @Autowired
    private LicenseVerifyProperties properties;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        if (handler instanceof HandlerMethod) {
            HandlerMethod handlerMethod = (HandlerMethod) handler;
            Method method = handlerMethod.getMethod();
            // 获取方法上的 LicenseVerify注解
            LicenseVerify.Exclude methodExclude = method.getAnnotation(LicenseVerify.Exclude.class);
            // 获取方法所在类上的 LicenseVerify注解
            LicenseVerify classAnno = method.getDeclaringClass().getAnnotation(LicenseVerify.class);
            // Class没有@LicenseVerify注解 或着方法被Exclude，不进行校验
            if (CommonUtils.isEmpty(classAnno) || CommonUtils.isNotEmpty(methodExclude)) {
                return true;
            }
            // 校验证书是否有效
            LicenseVerifyManager licenseVerifyManager = new LicenseVerifyManager();
            LicenseResult verifyResult = licenseVerifyManager.verify(properties.getVerifyParam());
            if (!verifyResult.getResult()) {
                throw new LicenseCommonException(verifyResult.getMessage());
            }
            // 增加业务系统监听，自定义验证
            LicenseContent licenseContent = verifyResult.getContent();
            // 获取额外参数校验
            LicenseExtraParam licenseCheck = (LicenseExtraParam) licenseContent.getExtra();
            List<CustomVerifyListener> customVerifyListenerList = CustomVerifyListener.getCustomVerifyListenerList();
            boolean compare = true;
            for (CustomVerifyListener customVerifyListener : customVerifyListenerList) {
                boolean verify = customVerifyListener.verify(licenseCheck);
                compare = compare && verify;
            }
            return compare;
        }
        return false;
    }
}
