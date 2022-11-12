package com.secretty.core.exception;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = true)
public class LicenseCommonException extends RuntimeException{

    private static final long serialVersionUID = 9137996029348236383L;

    /** 结果状态码*/
    private Integer resultCode;

    /** 结果消息*/
    private String message;

    public LicenseCommonException(String message) {
        super(message);
        this.resultCode = -1;
        this.message = message;
    }
}
