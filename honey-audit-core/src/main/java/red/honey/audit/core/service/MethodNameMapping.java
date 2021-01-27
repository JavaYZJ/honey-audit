package red.honey.audit.core.service;

import org.apache.dubbo.common.extension.SPI;
import red.honey.audit.common.constant.OperationType;
import red.honey.audit.common.exception.MethodNameMappingException;

/**
 * @author yangzhijie
 * @date 2021/1/19 17:51
 */
@SPI
public interface MethodNameMapping {

    /**
     * mapping the method name for operation type
     *
     * @param methodName method name
     * @return operation type
     * @throws MethodNameMappingException the exception of mapping the method name to operation type
     * @see OperationType
     */
    OperationType mapping(String methodName) throws MethodNameMappingException;
}
