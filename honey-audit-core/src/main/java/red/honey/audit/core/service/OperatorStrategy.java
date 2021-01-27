package red.honey.audit.core.service;

import org.apache.dubbo.common.extension.SPI;
import red.honey.audit.common.exception.OperatorException;
import red.honey.audit.common.pojo.Operator;

/**
 * @author yangzhijie
 * @date 2021/1/19 11:06
 */
@SPI
public interface OperatorStrategy {

    /**
     * the name of OperatorStrategy
     *
     * @return OperatorStrategyName
     */
    String operatorStrategyName();

    /**
     * get the operator by the customer strategy(via the Dubbo SPI or Spring IOC)
     *
     * @return operator
     * @throws OperatorException the exception of get the operator
     * @see Operator
     */
    Operator getOperator() throws OperatorException;


}
