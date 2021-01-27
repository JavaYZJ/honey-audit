# Honey Audit 审计服务


---

Honey Audit 是基于springboot + spring cloud Aalibaba Dubbo + Apache Zookeeper开发的一个审计服务。其实它又可以当做一个组件，可以完全脱离Dubbo、zookeeper这些中间件独立工作。

- [x] 基于HoneySPI机制(扩展加强过的SPI)，使HoneyAudit具有高度可插拔、灵活扩展的特性
- [x] 可托管于审计服务中心，也可实现服务自我“自营”完成审计工作
- [x] 代码侵入性低，一个@HoneyAudit注解即可开启审计服务
- [x] 通过方法名自动适配审计操作类型

## Honey Audit，what？
- Honey Audit可作为审计服务集中式完成接入应用的审计功能与管理，也可以作为审计组件独立帮助接入方完成审计工作，由接入方“自营”。
- 审计工作，最令人头疼的就是用户信息在各个接入方的实现是多种多样的，有可能是ThreadLocal存取的，也有可能是spring session存取的等等。Honey Audit提供了标准接口规范，让接入方根据自身特点具体实现，最终由Honey Audit统筹。
- Honey Audit将整个审计流程生命周期化，在每个周期节点，都有相应的接口提供给接入方扩展实现。



## 快速上手
一、引入依赖
```java
 <dependency>
    <groupId>red.honey</groupId>
    <artifactId>honey-audit-core</artifactId>
    <version>1.0.0</version>
 </dependency>
```
二、开启审计功能
启动类打上注解@EnableHoneyAudit即可开启
```java
@SpringBootApplication
@EnableHoneyAudit
public class HoneyAuditSampleApplication {
    public static void main(String[] args) {
        SpringApplication.run(HoneyAuditSampleApplication.class, args);
    }
}
```
三、指定环境
 两种方式：
 
 - 在application.properties或者yml中指定Env属性即可。
 - 指定VM参数：-DEnv
 
环境可选值dev(开发)、test(测试)、pro(生产)、fat(压测)

四、使用
将@EnableHoneyAudit打在类上或者方法上即可
```java
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
```
五、效果
在HoneyAudit控制台(http://honey.red:6699)找到应用（默认为接入方应用的spring.application.name属性值），点击应用，查看该应用的具体审计日志
![HoneyAudit控制台][1]

![应用审计日志][2]


## 进阶

**Honey Audit** 预留了以下四个扩展接口，留给开发者根据自身实际业务进行扩展实现。


 - red.honey.audit.core.service.OperatorStrategy：根据自身用户信息存储方式(ThreadLocal、Spring Session等)扩展实现操作者获取规则。
 - red.honey.audit.core.service.AuditMapper：如果接入方要实现自营，可以扩展该接口，达到审计操作用的Dao层由业务方控制
 - red.honey.audit.core.service.MethodNameMapping：用于接入方有异于常规的开发规范，实现适配自己开发规范的方法映射器。如果接入方遵守了常规的开发规范(比如阿里巴巴开发规范)，无需再配。
 - red.honey.audit.core.service.IpAddressService：用于扩展Ip地址获取规则。比如Http跟Dubbo等RPC调用的IP地址是不一样的。Honey Audit内置了HTTP调用的IP地址获取器。


----------

**Honey Audit** 有两种方式加载接入方的扩展实现，分别是SPI以及Spring Ioc。

 - SPI:改良了Java原生SPI机制，依据实现按需加载，并实例化为一个单例Bean托管于Spring Ioc容器中。所以使用者可以大胆放心在SPI加载的实现类注入任何spring管理的Bean。由于SPI机制的特性，因此具备更好的扩展性以及高度插拔特性
 - Spring Ioc: 这种方式更为简单，就是运用Spring,将当前实现类注册到Ioc中，代码兼容性高。

两者可以同时设置，但在优先级上Spring Ioc的优先级会高于SPI。
 
## 扩展举例

 1. 扩展相应接口，以OperatorStrategy为例
 
 - SPI 方式
 新建实现类
 ```java
public class OperatorSpiImpl implements OperatorStrategy {

    private Logger logger = LoggerFactory.getLogger(OperatorSpiImpl.class);

    /**
     * the name of OperatorStrategy
     *
     * @return OperatorStrategyName
     */
    @Override
    public String operatorStrategyName() {
        // named the operatorStrategyName
    }

    /**
     * get the operator by the customer strategy(via the Dubbo SPI or Spring IOC)
     *
     * @return operator
     * @throws OperatorException the exception of get the operator
     * @see Operator
     */
    @Override
    public Operator getOperator() throws OperatorException {
        // build your Operator if you need Honey Audit the built-in IpAddressService
        // you can use @Autowired DI 
    }
}
```
在resource下新建META_INF/services目录，以接口的权限定名为文件名，里面填写具体实现类的权限定名
如图所示，具体可以参考honey-audit-sample这个案例项目
![SPI扩展实现][3]

- Spring Ioc 方式
 这种方式就简单了，直接在类上打上@Service等spring bean注解即可



  [1]: http://oss.honey.red/public/honey-audit1.png
  [2]: http://oss.honey.red/public/honey-audit2.png
  [3]: http://oss.honey.red/public/SPI.png