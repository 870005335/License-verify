package com.secretty.core.model.creator;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import lombok.ToString;

import java.io.Serializable;
import java.util.Date;

/**
 * <p>License创建（生成）需要的参数</p>
 *
 * @author maktub
 */
@Data
@ToString
public class LicenseCreatorParam implements Serializable {

    @ToString.Exclude
    private static final long serialVersionUID = 5617747929246455318L;

    /**证书主题*/
    private String subject;

    /**私钥别名*/
    private String privateAlias;

    /**私钥密码（需要妥善保管，不能让使用者知道*/
    private String keyPass;

    /**私钥库存储路径*/
    private String privateKeysStorePath;

    /**访问私钥库的密码*/
    private String storePass;

    /**证书生成路径*/
    private String licensePath;

    /** 证书生效时间*/
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date issuedTime = new Date();

    /** 证书失效时间*/
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date expiryTime;

    /**用户类型*/
    private String consumerType = "user";

    /**用户数量*/
    private Integer consumerAmount = 1;

    /**描述信息*/
    private String description = "";

    /**额外的服务器硬件校验信息*/
    private LicenseExtraParam licenseCheck;

    /**证书下载地址 == 一旦证书create成功，这个值就会填充上*/
    private String licUrl;

}
