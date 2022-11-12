package com.secretty.core.service;

import com.secretty.core.model.creator.LicenseExtraParam;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.util.CollectionUtils;

import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.SocketException;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;
import java.util.stream.Collectors;

@Slf4j
public abstract class OSServerInfos {

    @Data
    private static class GxServerInfosContainer {

        private static List<String> ipAddress = null;

        private static List<String> macAddress = null;

        private static String cpuSerial = null;

        private static String mainBoardSerial = null;
    }

    /**
     * <p>组装需要额外校验的License参数</p>
     *
     * @return LicenseExtraParam 自定义校验参数
     */
    public LicenseExtraParam getServerInfos() {
        LicenseExtraParam result = new LicenseExtraParam();
        try {
            initServerInfos();
            result.setIpAddress(GxServerInfosContainer.ipAddress);
            result.setMacAddress(GxServerInfosContainer.macAddress);
            result.setCpuSerial(GxServerInfosContainer.cpuSerial);
            result.setMainBoardSerial(GxServerInfosContainer.mainBoardSerial);
        } catch (Exception e) {
            log.error("获取服务器硬件信息失败", e);
        }
        return result;
    }


    /**
     * <p>初始化服务器硬件信息，并将信息缓存到内存</p>
     *
     * @throws Exception 默认异常
     */
    private void initServerInfos() throws Exception {
        if (GxServerInfosContainer.ipAddress == null) {
            GxServerInfosContainer.ipAddress = this.getIpAddress();
        }
        if (GxServerInfosContainer.macAddress == null) {
            GxServerInfosContainer.macAddress = this.getMacAddress();
        }
        if (GxServerInfosContainer.cpuSerial == null) {
            GxServerInfosContainer.cpuSerial = this.getCPUSerial();
        }
        if (GxServerInfosContainer.mainBoardSerial == null) {
            GxServerInfosContainer.mainBoardSerial = this.getMainBoardSerial();
        }
    }



    /**
     * <p>获取IP地址</p>
     *
     * @return List<String> IP地址
     * @throws Exception 默认异常
     */
    public List<String> getIpAddress() throws Exception {
        /** 获取所有网络接口 */
        List<InetAddress> inetAddresses = getLocalAllInetAddress();
        if (!CollectionUtils.isEmpty(inetAddresses)) {
            return inetAddresses.stream().map(InetAddress::getHostAddress).distinct().map(String::toLowerCase).collect(Collectors.toList());
        }
        return null;
    }

    /**
     * <p>获取Mac地址</p>
     *
     * @return List<String> Mac地址
     * @throws Exception 默认异常
     */
    public List<String> getMacAddress() throws Exception {
        /** 获取所有网络接口 */
        List<InetAddress> inetAddresses = getLocalAllInetAddress();
        if (!CollectionUtils.isEmpty(inetAddresses)) {
            return inetAddresses.stream().map(this::getMacByInetAddress).distinct().collect(Collectors.toList());
        }
        return null;
    }


    /**
     * @Author liubin
     * @Description <p>获取服务器信息</p>
     * @Date 13:49 2022/11/10
     * @param osName 系统类型
     * @return com.secretty.core.service.ServerInfos
     **/
    public static OSServerInfos getServer(String osName) {
        OSServerInfos abstractOSServerInfos;
        if (StringUtils.isBlank(osName)){
            osName = System.getProperty("os.name").toLowerCase();
        }
        //根据不同操作系统类型选择不同的数据获取方法
        if (osName.startsWith("windows")) {
            abstractOSServerInfos = new WindowsServerInfos();
        } else if (osName.startsWith("linux")) {
            abstractOSServerInfos = new LinuxServerInfos();
        } else {
            //其他服务器类型
            abstractOSServerInfos = new LinuxServerInfos();
        }
        return abstractOSServerInfos;
    }

    /**
     * <p>获取服务器临时磁盘位置</p>
     */
    public static String getServerTempPath() {
        return System.getProperty("user.dir");
    }


    /**
     * <p>获取CPU序列号</p>
     *
     * @return String 主板序列号
     * @throws Exception 默认异常
     */
    protected abstract String getCPUSerial() throws Exception;

    /**
     * <p>获取主板序列号</p>
     *
     * @return String 主板序列号
     * @throws Exception 默认异常
     */
    protected abstract String getMainBoardSerial() throws Exception;


    /**
     * <p>获取当前服务器所有符合条件的网络地址</p>
     *
     * @return List<InetAddress> 网络地址列表
     * @throws Exception 默认异常
     */
    private static List<InetAddress> getLocalAllInetAddress() throws Exception {
        List<InetAddress> result = new ArrayList<>(4);
        // 遍历所有的网络接口
        for (Enumeration<NetworkInterface> networkInterfaces = NetworkInterface.getNetworkInterfaces(); networkInterfaces.hasMoreElements(); ) {
            NetworkInterface ni = networkInterfaces.nextElement();
            // 在所有的接口下再遍历IP
            for (Enumeration<InetAddress> addresses = ni.getInetAddresses(); addresses.hasMoreElements(); ) {
                InetAddress address = addresses.nextElement();
                //排除LoopbackAddress、SiteLocalAddress、LinkLocalAddress、MulticastAddress类型的IP地址
                if (!address.isLoopbackAddress() /*&& !inetAddr.isSiteLocalAddress()*/
                        && !address.isLinkLocalAddress() && !address.isMulticastAddress()) {
                    result.add(address);
                }
            }
        }
        return result;
    }

    public static void main(String[] args) {

    }

    /**
     * <p>获取某个网络地址对应的Mac地址</p>
     *
     * @param inetAddr 网络地址
     * @return String Mac地址
     */
    private String getMacByInetAddress(InetAddress inetAddr) {
        try {
            byte[] mac = NetworkInterface.getByInetAddress(inetAddr).getHardwareAddress();
            StringBuilder stringBuilder = new StringBuilder();
            for (int i = 0; i < mac.length; i++) {
                if (i != 0) {
                    stringBuilder.append("-");
                }
                /** 将十六进制byte转化为字符串 */
                String temp = Integer.toHexString(mac[i] & 0xff);
                if (temp.length() == 1) {
                    stringBuilder.append("0").append(temp);
                } else {
                    stringBuilder.append(temp);
                }
            }
            return stringBuilder.toString().toUpperCase();
        } catch (SocketException e) {
            e.printStackTrace();
        }
        return null;
    }
}
