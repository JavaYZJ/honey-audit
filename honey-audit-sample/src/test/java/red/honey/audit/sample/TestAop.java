package red.honey.audit.sample;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import red.honey.audit.sample.service.SampleService;

/**
 * @author yangzhijie
 * @date 2021/1/21 15:11
 */
@SpringBootTest
@RunWith(SpringRunner.class)
public class TestAop {


    @Autowired
    private SampleService sampleService;


    @Test
    public void test() {
        sampleService.testService(5201314);
    }


    @Test
    public void test1() {
        sampleService.addService("add");
    }


}
