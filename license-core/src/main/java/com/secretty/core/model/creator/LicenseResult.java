package com.secretty.core.model.creator;

import de.schlichtherle.license.LicenseContent;
import lombok.Data;

/**
 * <p>License证书验证结果对象</p>
 *
 * @author appleyk
 * @version V.0.2.1
 * @blob https://blog.csdn.net/appleyk
 * @date created on  10:42 下午 2020/8/21
 */
@Data
public class LicenseResult {

    /** 检验结果 */
    private Boolean result;
    /** 附加信息 */
    private String message;
    /** 证书内容 */
    private LicenseContent content;
    /** 检验失败错误 */
    private Exception exception;

    public LicenseResult(LicenseContent content) {
        this.result = true;
        this.content = content;
    }

    public LicenseResult(String message, LicenseContent content) {
        this.result = true;
        this.message = message;
        this.content = content;
    }

    public LicenseResult(Exception exception) {
        this.result = false;
        this.exception = exception;
    }

    public LicenseResult(String message, Exception exception) {
        this.result = false;
        this.message = message;
        this.exception = exception;
    }

    public LicenseResult(boolean result , String message, Exception exception) {
        this.result = result;
        this.message = message;
        this.exception = exception;
    }

    public static LicenseResult success(String message, LicenseContent content) {
        return new LicenseResult(message, content);
    }

    public static LicenseResult fail(String message, Exception exception) {
        return new LicenseResult(message, exception);
    }

}
