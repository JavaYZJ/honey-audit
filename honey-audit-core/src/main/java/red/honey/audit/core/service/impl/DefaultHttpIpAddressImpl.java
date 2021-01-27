package red.honey.audit.core.service.impl;

import red.honey.audit.core.service.IpAddressService;

import javax.servlet.http.HttpServletRequest;

/**
 * @author yangzhijie
 * @date 2021/1/21 11:20
 */
public class DefaultHttpIpAddressImpl implements IpAddressService {

    /**
     * handler the ip address for the operator
     *
     * @param httpRequest httpRequest
     * @return ip address
     */
    @Override
    public String handlerIpAddress(HttpServletRequest httpRequest) {
        String ip = null;
        String ipAddresses = httpRequest.getHeader("X-Forwarded-For");

        if (ipAddresses == null || ipAddresses.length() == 0 || "unknown".equalsIgnoreCase(ipAddresses)) {
            //Proxy-Client-IP：apache
            ipAddresses = httpRequest.getHeader("Proxy-Client-IP");
        }

        if (ipAddresses == null || ipAddresses.length() == 0 || "unknown".equalsIgnoreCase(ipAddresses)) {
            //WL-Proxy-Client-IP：weblogic
            ipAddresses = httpRequest.getHeader("WL-Proxy-Client-IP");
        }

        if (ipAddresses == null || ipAddresses.length() == 0 || "unknown".equalsIgnoreCase(ipAddresses)) {
            //HTTP_CLIENT_IP：
            ipAddresses = httpRequest.getHeader("HTTP_CLIENT_IP");
        }

        if (ipAddresses == null || ipAddresses.length() == 0 || "unknown".equalsIgnoreCase(ipAddresses)) {
            //X-Real-IP：nginx
            ipAddresses = httpRequest.getHeader("X-Real-IP");
        }

        if (ipAddresses != null && ipAddresses.length() != 0) {
            ip = ipAddresses.split(",")[0];
        }

        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ipAddresses)) {
            ip = httpRequest.getRemoteAddr();
        }
        return ip;
    }
}
