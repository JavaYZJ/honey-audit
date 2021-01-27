package red.honey.audit.core.service;

import org.apache.dubbo.common.extension.Adaptive;
import org.apache.dubbo.common.extension.SPI;
import red.honey.audit.common.exception.DaoMapperException;
import red.honey.audit.common.pojo.Operation;

/**
 * @author yangzhijie
 * @date 2021/1/19 14:17
 */
@SPI
public interface AuditMapper {


    /**
     * persist the operation
     *
     * @param operation operation
     * @return persist successful
     * @throws DaoMapperException the exception of operate the database for record the audit logs
     * @see Operation
     * @see DaoMapperException
     */
    boolean operationPersistence(Operation operation) throws DaoMapperException;
}
