package red.honey.audit.sample.service;

import org.springframework.stereotype.Service;
import red.honey.audit.core.annotation.HoneyAudit;

/**
 * @author yangzhijie
 * @date 2021/1/21 15:21
 */
@HoneyAudit
@Service
public class SampleServiceImpl implements SampleService {


    @HoneyAudit(appId = "yangzhijie")
    @Override
    public void testService(Integer arg) {
        System.out.println(arg);
    }

    @Override
    public void addService(String arg) {
        System.out.println(arg);
    }
}
