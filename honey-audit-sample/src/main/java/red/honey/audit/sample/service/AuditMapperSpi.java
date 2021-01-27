package red.honey.audit.sample.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import red.honey.audit.common.exception.DaoMapperException;
import red.honey.audit.common.pojo.Operation;
import red.honey.audit.core.service.AuditMapper;

/**
 * @author yangzhijie
 * @date 2021/1/21 15:53
 */
public class AuditMapperSpi implements AuditMapper {

    private Logger logger = LoggerFactory.getLogger(AuditMapperSpi.class);

    @Autowired
    private red.honey.audit.sample.mapper.AuditMapper auditMapper;
    /**
     * persist the operation
     *
     * @param operation operation
     * @return persist successful
     * @throws DaoMapperException the exception of operate the database for record the audit logs
     * @see Operation
     * @see DaoMapperException
     */
    @Override
    public boolean operationPersistence(Operation operation) throws DaoMapperException {
        logger.info("走spi注入AuditMapper");
        return auditMapper.addAuditLogs(operation);
    }
}
