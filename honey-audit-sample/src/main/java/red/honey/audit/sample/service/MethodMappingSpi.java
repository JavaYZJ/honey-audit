package red.honey.audit.sample.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import red.honey.audit.common.constant.OperationType;
import red.honey.audit.common.exception.MethodNameMappingException;
import red.honey.audit.core.service.MethodNameMapping;

/**
 * @author yangzhijie
 * @date 2021/1/21 15:56
 */

public class MethodMappingSpi implements MethodNameMapping {

    private Logger logger = LoggerFactory.getLogger(MethodMappingSpi.class);

    /**
     * mapping the method name for operation type
     *
     * @param methodName method name
     * @return operation type
     * @throws MethodNameMappingException the exception of mapping the method name to operation type
     * @see OperationType
     */
    @Override
    public OperationType mapping(String methodName) throws MethodNameMappingException {
        logger.info("走spi映射方法名");
        if (methodName.startsWith("test")) {
            return OperationType.VIEW;
        }
        return null;
    }
}
