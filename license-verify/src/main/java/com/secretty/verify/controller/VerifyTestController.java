package com.secretty.verify.controller;

import com.secretty.verify.annotation.LicenseVerify;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("license/verify")
@LicenseVerify
public class VerifyTestController {

    @GetMapping("/hello")
    public String hello() {
        return "hello license";
    }


    @GetMapping("/exclude")
    @LicenseVerify.Exclude
    public String exclude() {
        return "exclude verify";
    }
}
