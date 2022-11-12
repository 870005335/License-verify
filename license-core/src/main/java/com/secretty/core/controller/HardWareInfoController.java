package com.secretty.core.controller;

import com.secretty.core.model.creator.LicenseExtraParam;
import com.secretty.core.response.Response;
import com.secretty.core.service.OSServerInfos;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RequestMapping("/license/core")
@RestController
public class HardWareInfoController {

    /**
     * @Author liubin
     * @Description <p>获取服务器硬件信息</p>
     * @Date 11:50 2022/11/10
     * @param osName 操作系统类型，如果为空则自动判断
     * @return com.secretty.core.response.Response<com.secretty.core.model.creator.LicenseExtraParam>
     **/
    @GetMapping("getServerInfos")
    public Response<LicenseExtraParam> getServerInfos(@RequestParam(value = "osName",required = false) String osName) {
        LicenseExtraParam extraParam = OSServerInfos.getServer(osName).getServerInfos();
        return Response.success(extraParam);
    }
}
