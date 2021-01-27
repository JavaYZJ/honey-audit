package red.honey.audit.core;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;

/**
 * @author yangzhijie
 * @date 2021/1/19 16:21
 */
@SpringBootApplication
public class HoneyAuditCoreApplication {

    public static void main(String[] args) {
        SpringApplication.run(HoneyAuditCoreApplication.class, args);
    }
}
