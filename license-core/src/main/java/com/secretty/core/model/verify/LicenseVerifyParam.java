package com.secretty.core.model.verify;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class LicenseVerifyParam {

    /**
     * 证书主题
     * */
    private String subject;

    /**
     * 公钥别名
     * */
    private String publicAlias;

    /**
     * 访问公钥库的密码
     * */
    private String storePass;

    /**
     * 证书生成路径
     * */
    private String licensePath;

    /**
     * 公钥库存储路径
     * */
    private String publicKeysStorePath;


}
