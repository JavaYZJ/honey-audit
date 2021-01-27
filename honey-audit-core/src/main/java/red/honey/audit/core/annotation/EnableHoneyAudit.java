package red.honey.audit.core.annotation;

import org.springframework.context.annotation.Import;
import red.honey.audit.core.config.HoneyAuditAutoConfiguration;

import java.lang.annotation.*;

/**
 * @author yangzhijie
 * @date 2021/1/21 11:30
 */
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Import(HoneyAuditAutoConfiguration.class)
public @interface EnableHoneyAudit {
}
