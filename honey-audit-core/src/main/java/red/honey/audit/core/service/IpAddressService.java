package red.honey.audit.core.service;

import javax.servlet.http.HttpServletRequest;

/**
 * @author yangzhijie
 * @date 2021/1/19 14:31
 */
public interface IpAddressService {

    /**
     * handler the ip address for the operator
     *
     * @param httpRequest httpRequest
     * @return ip address
     */
    String handlerIpAddress(HttpServletRequest httpRequest);
}
