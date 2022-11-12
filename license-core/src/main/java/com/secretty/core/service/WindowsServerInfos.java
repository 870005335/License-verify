package com.secretty.core.service;

import lombok.extern.slf4j.Slf4j;

import java.io.*;

/**
 * <p>用于获取客户Windows服务器的基本信息</p>
 *
 * @author maktub
 */
@Slf4j
public class WindowsServerInfos extends OSServerInfos {

    @Override
    protected String getCPUSerial() throws Exception {
        StringBuilder result = new StringBuilder();
        try {
            // 创建临时脚本文件
            File tempFile = File.createTempFile("temp", ".vbs");
            // 存在删除
            tempFile.deleteOnExit();
            FileWriter fileWriter = new FileWriter(tempFile);
            String vbs = "Set objWMIService = GetObject(\"winmgmts:\\\\.\\root\\cimv2\")\n"
                    + "Set colItems = objWMIService.ExecQuery _ \n" + "   (\"Select * from Win32_Processor\") \n"
                    + "For Each objItem in colItems \n" + "    Wscript.Echo objItem.ProcessorId \n"
                    + "    exit for  ' do the first cpu only! \n" + "Next \n";
            fileWriter.write(vbs);
            fileWriter.close();
            Process p = Runtime.getRuntime().exec("cscript //NoLogo " + tempFile.getPath());
            BufferedReader input = new BufferedReader(new InputStreamReader(p.getInputStream()));
            String line;
            while ((line = input.readLine()) != null) {
                result.append(line);
            }
            input.close();
            tempFile.delete();
        } catch (IOException e) {
            log.error("获取cpu信息错误", e);
        }
        return result.toString().trim();
    }

    @Override
    protected String getMainBoardSerial() throws Exception {
        StringBuilder result = new StringBuilder();
        try {
            File file = File.createTempFile("realhowto", ".vbs");
            file.deleteOnExit();
            FileWriter fw = new FileWriter(file);

            String vbs = "Set objWMIService = GetObject(\"winmgmts:\\\\.\\root\\cimv2\")\n"
                    + "Set colItems = objWMIService.ExecQuery _ \n" + "   (\"Select * from Win32_BaseBoard\") \n"
                    + "For Each objItem in colItems \n" + "    Wscript.Echo objItem.SerialNumber \n"
                    + "    exit for  ' do the first cpu only! \n" + "Next \n";

            fw.write(vbs);
            fw.close();
            Process p = Runtime.getRuntime().exec("cscript //NoLogo " + file.getPath());
            BufferedReader input = new BufferedReader(new InputStreamReader(p.getInputStream()));
            String line;
            while ((line = input.readLine()) != null) {
                result.append(line);
            }
            input.close();
        } catch (Exception e) {
            log.error("获取主板信息错误", e);
        }
        return result.toString().trim();
    }
}
