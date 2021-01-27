package red.honey.audit.core.service.wrap;

import org.aspectj.lang.reflect.MethodSignature;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationContext;
import red.honey.audit.common.constant.OperationType;
import red.honey.audit.common.exception.GetBeanException;
import red.honey.audit.common.exception.MethodNameMappingException;
import red.honey.audit.core.service.MethodNameMapping;
import red.honey.audit.core.utils.SpiOrIocUtils;

import static red.honey.audit.common.constant.Operation.*;

/**
 * @author yangzhijie
 * @date 2021/1/19 17:38
 */
public class MethodSignatureWrapper {

    private static final Logger logger = LoggerFactory.getLogger(MethodSignatureWrapper.class);

    private ApplicationContext applicationContext;

    private MethodSignature methodSignature;

    private OperationType operationType;

    public MethodSignatureWrapper(ApplicationContext applicationContext, MethodSignature methodSignature) {
        this.applicationContext = applicationContext;
        this.methodSignature = methodSignature;
        this.operationType = methodSignatureMapping();
    }

    /**
     * mapping the method signature to operation type
     * if get the operationType from spi or spring ioc
     * is null or happens error ,it will demote to the
     * default operationType mapping(doDefaultMapping)
     *
     * @return operationType
     * @see OperationType
     * @see MethodSignatureWrapper#doDefaultMapping(java.lang.String)
     * @since 1.0.0
     */
    private OperationType methodSignatureMapping() {
        String methodName = this.methodSignature.getName();
        try {
            MethodNameMapping methodNameMapping = SpiOrIocUtils.getBean(applicationContext, MethodNameMapping.class);
            assignOperationType(methodNameMapping, methodName);
            return operationType == null ? doDefaultMapping(methodName) : operationType;
        } catch (GetBeanException gbe) {
            if (logger.isErrorEnabled()) {
                logger.error("get the method name mapping from spi or spring ioc happen error. " +
                        "demote the default mapping", gbe);
            }
            return doDefaultMapping(methodName);
        }
    }

    /**
     * assign operationType
     *
     * @param methodNameMapping the implementation of red.honey.audit.core.service.MethodNameMapping
     * @param methodName        the method name
     * @since 1.0.0
     */
    private void assignOperationType(MethodNameMapping methodNameMapping, String methodName) throws GetBeanException {
        if (methodNameMapping == null) {
            throw new GetBeanException("the MethodNameMapping is null");
        }
        try {
            this.operationType = methodNameMapping.mapping(methodName);
        } catch (MethodNameMappingException ope) {
            if (logger.isErrorEnabled()) {
                if (logger.isErrorEnabled()) {
                    logger.error("get the MethodNameMapping happen error by the {},the reason:{}", methodNameMapping, ope);
                }
            }
        }
    }

    /**
     * the default methodName mapping
     *
     * @param methodName the methodName
     * @since 1.0.0
     */
    private OperationType doDefaultMapping(String methodName) {
        OperationType operationType = null;
        if (methodName.startsWith(GET_PREFIX) || methodName.startsWith(FIND_PREFIX)) {
            operationType = OperationType.VIEW;
        } else if (methodName.startsWith(ADD_PREFIX) || methodName.startsWith(INSERT_PREFIX)) {
            operationType = OperationType.ADD;
        } else if (methodName.startsWith(UPDATE_PREFIX)) {
            operationType = OperationType.UPDATE;
        } else if (methodName.startsWith(REMOVE_PREFIX) || methodName.startsWith(DELETE_PREFIX)) {
            operationType = OperationType.REMOVE;
        }
        return operationType;
    }


    public MethodSignature getMethodSignature() {
        return methodSignature;
    }

    public OperationType getOperationType() {
        return operationType;
    }


}
