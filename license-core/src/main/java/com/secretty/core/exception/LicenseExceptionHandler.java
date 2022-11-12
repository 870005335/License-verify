package com.secretty.core.exception;

import com.secretty.core.response.Response;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@Slf4j
@RestControllerAdvice(basePackages = {"com.secretty"})
public class LicenseExceptionHandler {

    @ExceptionHandler(value = LicenseCommonException.class)
    public Response<Void> handleLicenseException(LicenseCommonException exception) {
        log.error("错误：",exception);
        return Response.fail(exception.getResultCode(), exception.getMessage());
    }

    @ExceptionHandler(value = Throwable.class)
    public Response<Void> handleException(Throwable throwable) {
        log.error("错误：",throwable);
        return Response.fail(10000, "系统未知异常");
    }
}
