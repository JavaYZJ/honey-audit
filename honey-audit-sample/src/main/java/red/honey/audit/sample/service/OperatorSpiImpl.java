package red.honey.audit.sample.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import red.honey.audit.common.exception.OperatorException;
import red.honey.audit.common.pojo.Operator;
import red.honey.audit.common.utils.CommonUtil;
import red.honey.audit.core.service.OperatorStrategy;

/**
 * @author yangzhijie
 * @date 2021/1/21 15:09
 */
public class OperatorSpiImpl implements OperatorStrategy {

    private Logger logger = LoggerFactory.getLogger(OperatorSpiImpl.class);

    /**
     * the name of OperatorStrategy
     *
     * @return OperatorStrategyName
     */
    @Override
    public String operatorStrategyName() {
        return "OperatorIoc";
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
        Operator operator = new Operator();
        operator.setOperatorName("OperatorSpi");
        operator.setOperatorIp("127.0.0.1");
        operator.setOperatorId(CommonUtil.get32Uid());
        logger.info("走Spi注入Operator策略");
        return operator;
    }
}
