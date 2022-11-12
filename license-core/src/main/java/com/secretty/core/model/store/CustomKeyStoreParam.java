package com.secretty.core.model.store;

import de.schlichtherle.license.AbstractKeyStoreParam;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.*;
import java.nio.file.Files;

public class CustomKeyStoreParam extends AbstractKeyStoreParam {

    private final String storePath;

    private final String alias;

    private final String storePwd;

    private final String keyPwd;


    public CustomKeyStoreParam(Class clazz, String resource,String alias,String storePwd,String keyPwd) {
        super(clazz, resource);
        this.storePath = resource;
        this.alias = alias;
        this.storePwd = storePwd;
        this.keyPwd = keyPwd;
    }


    @Override
    public String getAlias() {
        return this.alias;
    }

    @Override
    public String getStorePwd() {
        return this.storePwd;
    }

    @Override
    public String getKeyPwd() {
        return this.keyPwd;
    }

    /**
     * @Author liubin
     * @Description 复写de.schlichtherle.license.AbstractKeyStoreParam的getStream()方法<br />
     * 用于将公私钥存储文件存放到其他磁盘位置而不是项目中
     * @Date 10:11 2022/11/11
     * @return java.io.InputStream
     **/
    @Override
    public InputStream getStream() throws IOException {
        final InputStream in = new FileInputStream(new File(storePath));
        if (null == in){
            throw new FileNotFoundException(storePath);
        }

        return in;
    }
}
