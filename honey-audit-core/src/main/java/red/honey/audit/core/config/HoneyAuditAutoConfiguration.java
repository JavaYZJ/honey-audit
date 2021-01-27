package red.honey.audit.core.config;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import red.honey.audit.core.aop.AuditAspect;
import red.honey.audit.core.service.impl.DefaultHttpIpAddressImpl;

/**
 * @author yangzhijie
 * @date 2021/1/21 11:13
 */
@Configuration
public class HoneyAuditAutoConfiguration {

    private Logger logger = LoggerFactory.getLogger(HoneyAuditAutoConfiguration.class);

    @Bean
    @ConditionalOnClass(AuditAspect.class)
    public AuditAspect newAuditAspect() {
        logger.info("Honey Audit had started.");
        return new AuditAspect();
    }


    @Bean
    public DefaultHttpIpAddressImpl ipAddress() {
        return new DefaultHttpIpAddressImpl();
    }

}
