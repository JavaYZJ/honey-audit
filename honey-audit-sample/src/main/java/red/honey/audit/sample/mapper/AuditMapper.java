package red.honey.audit.sample.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import red.honey.audit.common.pojo.Operation;
import red.honey.audit.common.pojo.dto.QueryDto;

import java.util.List;

/**
 * @author yangzhijie
 * @date 2021/1/20 14:36
 */
@Mapper
public interface AuditMapper {

    /**
     * persist the operation logs to database
     *
     * @param operation operation
     * @return the result of the operation persistence
     */
    Boolean addAuditLogs(@Param("operation") Operation operation);


    /**
     * delete the operation logs by ids
     *
     * @param appId the application id
     * @param ids   the list of log uid
     * @return the result of the delete operation
     */
    Boolean deleteAuditLogs(@Param("appId") String appId, @Param("ids") List<String> ids);

    /**
     * query the operation logs by the query criteria
     *
     * @param queryDto the query criteria
     * @return the result of the query
     */
    List<Operation> queryAuditLogs(@Param("queryDto") QueryDto queryDto);
}
