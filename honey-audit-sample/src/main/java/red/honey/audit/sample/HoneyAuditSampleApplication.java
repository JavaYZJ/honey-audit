package red.honey.audit.sample;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import red.honey.audit.core.annotation.EnableHoneyAudit;

/**
 * @author yangzhijie
 */
@SpringBootApplication
@EnableHoneyAudit
public class HoneyAuditSampleApplication {

    public static void main(String[] args) {
        SpringApplication.run(HoneyAuditSampleApplication.class, args);
    }

}
