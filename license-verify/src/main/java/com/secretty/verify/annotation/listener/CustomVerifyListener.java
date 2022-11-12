package com.secretty.verify.annotation.listener;

import com.secretty.core.exception.LicenseCommonException;
import com.secretty.core.model.creator.LicenseExtraParam;

import java.util.ArrayList;
import java.util.List;

/**
 * <p>增加业务系统中自定义证书验证监听器</p>
 *
 * @author maktub
 */
public abstract class CustomVerifyListener {

    // 软件证书参数全局验证监听容器
    private static final List<CustomVerifyListener> CUSTOM_VERIFY_LISTENER_LIST = new ArrayList<>();

    public static List<CustomVerifyListener> getCustomVerifyListenerList() {
        return CUSTOM_VERIFY_LISTENER_LIST;
    }

    /***
     * 默认构造函数，干了一件事情，就是会把所有实现了这个抽象类的子类实例全部添加到全局自定义验证监听器列表中
     * 因为在调用子类的构造函数时，会首先调用父类的构造器
     */
    public CustomVerifyListener() {
        addCustomListener(this);
    }


    private synchronized static void addCustomListener(CustomVerifyListener customVerifyListener) {
        CUSTOM_VERIFY_LISTENER_LIST.add(customVerifyListener);
    }

    public abstract boolean verify(LicenseExtraParam extraParam) throws LicenseCommonException;
}
