package red.honey.audit.server.application.service;

import com.github.pagehelper.PageInfo;
import org.apache.ibatis.annotations.Param;
import red.honey.audit.common.pojo.Operation;
import red.honey.audit.common.pojo.dto.QueryDto;

import java.util.List;

/**
 * @author yangzhijie
 * @date 2021/1/20 16:11
 */
public interface AuditService {


    /**
     * persist the operation logs to database
     *
     * @param operation operation
     * @return the result of the operation persistence
     */
    Boolean addAuditLogs(Operation operation);


    /**
     * delete the operation logs by ids
     *
     * @param appId the application id
     * @param ids   the list of log uid
     * @return the result of the delete operation
     */
    Boolean deleteAuditLogs(String appId, @Param("ids") List<String> ids);

    /**
     * query the operation logs by the query criteria
     *
     * @param queryDto the query criteria
     * @return the result of the query
     */
    PageInfo<Operation> queryAuditLogs(QueryDto queryDto);



    /**
     * get the apps which are had accessed to the honey audit server
     *
     * @return all the appId
     */
    List<String> getApps();
}
