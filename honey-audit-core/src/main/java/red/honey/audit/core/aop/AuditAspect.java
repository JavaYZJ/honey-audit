package red.honey.audit.core.aop;

import com.alibaba.fastjson.JSON;
import org.apache.dubbo.config.annotation.DubboReference;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.util.Assert;
import red.honey.audit.api.rpc.HoneyAuditApi;
import red.honey.audit.common.constant.OperationType;
import red.honey.audit.common.exception.DaoMapperException;
import red.honey.audit.common.exception.GetBeanException;
import red.honey.audit.common.exception.InstanceFieldCheckException;
import red.honey.audit.common.exception.OperatorException;
import red.honey.audit.common.pojo.Operation;
import red.honey.audit.common.pojo.Operator;
import red.honey.audit.common.utils.CommonUtil;
import red.honey.audit.core.annotation.HoneyAudit;
import red.honey.audit.core.service.AuditMapper;
import red.honey.audit.core.service.OperatorStrategy;
import red.honey.audit.core.service.wrap.MethodSignatureWrapper;
import red.honey.audit.core.utils.AnnotationUtils;
import red.honey.audit.core.utils.SpiOrIocUtils;

import javax.validation.constraints.NotNull;
import java.util.Objects;

import static red.honey.audit.common.constant.Operation.OPERATION_FAILED;
import static red.honey.audit.common.constant.Operation.OPERATION_SUCCESSFUL;

/**
 * @author yangzhijie
 * @date 2021/1/19 10:59
 */
@Aspect
public class AuditAspect implements ApplicationContextAware {

    private static final Logger logger = LoggerFactory.getLogger(AuditAspect.class);

    @Value("${spring.application.name}")
    private String applicationName;

    @DubboReference(check = false)
    private HoneyAuditApi honeyAuditApi;

    private ApplicationContext applicationContext;

    /**
     * the expression of pointcut
     *
     * @since 1.0.0
     */
    @Pointcut("@within(red.honey.audit.core.annotation.HoneyAudit) ")
    public void auditPointcut() {
    }

    /**
     * handle the @HoneyAudit annotation tagging on the class or methods
     *
     * @param jointPoint the joint point of HoneyAudit AOP
     * @see HoneyAudit
     * @since 1.0.0
     */
    @Around("auditPointcut()")
    public void auditPointcutProcess(ProceedingJoinPoint jointPoint) throws Throwable {

        HoneyAudit annotation = (HoneyAudit) AnnotationUtils.getAnnotation(jointPoint, HoneyAudit.class);
        if (annotation == null) {
            handleByClass(jointPoint);
        } else {
            handleByMethod(jointPoint, annotation);
        }
    }

    /**
     * handle the @HoneyAudit annotation tagging on the method
     *
     * @param jointPoint the joint point
     * @param annotation the annotation of @HoneyAudit
     * @throws GetBeanException the exception thrown by the get the AuditMapper bean from spi or spring ioc
     * @see HoneyAudit
     * @see AuditMapper
     * @since 1.0.0
     */
    private void handleByMethod(ProceedingJoinPoint jointPoint, HoneyAudit annotation) throws Throwable {
        Operation operation = new Operation();
        AuditMapper daoMapper = null;
        boolean flag = false;
        try {
            flag = assignOperation(annotation, operation);
            daoMapper = SpiOrIocUtils.getBean(applicationContext, AuditMapper.class);
        } catch (GetBeanException | InstanceFieldCheckException e) {
            if (logger.isErrorEnabled()) {
                logger.error("audit aop happens error,so lose the audit function", e);
            }
        }
        jointProceed(jointPoint, daoMapper, operation, flag);
    }

    /**
     * handle the @HoneyAudit annotation tagging on the class.
     *
     * @param jointPoint the joint point
     * @since 1.0.0
     */
    private void handleByClass(ProceedingJoinPoint jointPoint) throws Throwable {
        AuditMapper daoMapper = null;
        Operation operation = null;
        boolean flag = false;
        try {
            OperatorStrategy operatorStrategy = SpiOrIocUtils.getBean(applicationContext, OperatorStrategy.class);
            Operator operator = Objects.requireNonNull(operatorStrategy).getOperator();
            daoMapper = SpiOrIocUtils.getBean(applicationContext, AuditMapper.class);
            operation = buildOperation(jointPoint);
            flag = mergeAndCheckOperator(operator, operation);
        } catch (GetBeanException | OperatorException | IllegalStateException | InstanceFieldCheckException e) {
            if (logger.isErrorEnabled()) {
                logger.error("audit aop happens error,so lose the audit function", e);
            }
        }
        jointProceed(jointPoint, daoMapper, operation, flag);
    }

    /**
     * process the joint
     * if the jointPoint processing happens error,
     * Honey Audit will record the error reason
     *
     * @param jointPoint the joint point
     * @param daoMapper  the implementation of red.honey.audit.core.service.AuditMapper
     * @param operation  the instance of red.honey.audit.common.pojo.Operation
     * @param flag       the boolean flag for persisting the audit logs
     * @since 1.0.0
     */
    private void jointProceed(ProceedingJoinPoint jointPoint, AuditMapper daoMapper, Operation operation, boolean flag) throws Throwable {
        try {
            jointPoint.proceed();
        } catch (Throwable throwable) {
            Objects.requireNonNull(operation).setOperationDetails(OPERATION_FAILED + " : " + throwable);
            throw throwable;
        } finally {
            auditPersistence(daoMapper, operation, flag);
        }
    }

    /**
     * persist the audit logs by spi or spring ioc AuditMapper
     *
     * @param daoMapper the implementation of red.honey.audit.core.service.AuditMapper
     * @param operation the instance of red.honey.audit.common.pojo.Operation
     * @param flag      the boolean flag for persisting the audit logs
     * @since 1.0.0
     */
    private void auditPersistence(AuditMapper daoMapper, Operation operation, boolean flag) {
        if (flag) {
            if (daoMapper != null) {
                try {
                    daoMapper.operationPersistence(operation);
                } catch (DaoMapperException dme) {
                    if (logger.isErrorEnabled()) {
                        logger.error("custom daoMapper : persist the operation logs happen error ", dme);
                    }
                }
            } else if (honeyAuditApi != null) {
                try {
                    honeyAuditApi.addAuditLogs(operation);
                } catch (Exception e) {
                    if (logger.isErrorEnabled()) {
                        logger.error("audit server dubbo api happen error :", e);
                    }
                }
            } else {
                logger.info("Dubbo honeyAuditApi and custom daoMapper are not found in the application." +
                        "There must be at least one of them,otherwise it will lose honey audit function. ");
            }
        }
    }

    /**
     * assign the operation by the @HoneyAudit
     *
     * @param annotation the annotation of HoneyAudit
     * @param operation  the pojo instance of red.honey.audit.common.pojo.Operation
     * @since 1.0.0
     */
    private boolean assignOperation(HoneyAudit annotation, Operation operation) {
        Assert.notNull(annotation, "HoneyAudit must not be null.");
        Assert.notNull(operation, "operation must not be null.");
        operation.setAppId(annotation.appId());
        operation.setUid(CommonUtil.get32Uid());
        operation.setOperatorIp(annotation.operationIp());
        operation.setOperatorId(annotation.operatorId());
        operation.setOperatorName(annotation.operatorName());
        operation.setOperationType(annotation.operationType());
        operation.setOperationMethod(annotation.operationMethod());
        operation.setOperationParameters(JSON.toJSONString(annotation.operationParameters()));
        operation.setOperationDetails(OPERATION_SUCCESSFUL);
        return CommonUtil.checkInstanceFieldIsNull(operation);
    }

    /**
     * merges the operation from the given operator
     * and check the operation every field is not null
     *
     * @param operator  he pojo instance of red.honey.audit.common.pojo.Operator
     * @param operation the pojo instance of red.honey.audit.common.pojo.Operation
     * @since 1.0.0
     */
    private boolean mergeAndCheckOperator(@NotNull Operator operator, @NotNull Operation operation) {
        Assert.notNull(operation, "operation must not be null,please check the spi or ioc config ");
        Assert.notNull(operator, "operator must not be null,please check the spi or ioc config ");
        operation.setOperatorName(operator.getOperatorName());
        operation.setOperatorIp(operator.getOperatorIp());
        operation.setOperatorId(operator.getOperatorId());
        return CommonUtil.checkInstanceFieldIsNull(operation);
    }


    /**
     * build the operation by jointPoint
     *
     * @param jointPoint the jointPoint
     * @since 1.0.0
     */
    private Operation buildOperation(ProceedingJoinPoint jointPoint) {
        Operation operation = new Operation();
        MethodSignature methodSignature = (MethodSignature) jointPoint.getSignature();
        Object[] arguments = jointPoint.getArgs();
        OperationType operationType = methodSignatureMapping(methodSignature);
        operation.setAppId(applicationName);
        operation.setUid(CommonUtil.get32Uid());
        operation.setOperationMethod(methodSignature.toString());
        operation.setOperationParameters(JSON.toJSONString(arguments));
        operation.setOperationType(operationType);
        operation.setOperationDetails(OPERATION_SUCCESSFUL);
        return operation;
    }

    /**
     * mapping the operation type by the wrapper of MethodSignature
     *
     * @param methodSignature methodSignature
     * @since 1.0.0
     */
    private OperationType methodSignatureMapping(MethodSignature methodSignature) {
        MethodSignatureWrapper wrapper = new MethodSignatureWrapper(applicationContext, methodSignature);
        return wrapper.getOperationType();
    }


    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        this.applicationContext = applicationContext;
    }
}
