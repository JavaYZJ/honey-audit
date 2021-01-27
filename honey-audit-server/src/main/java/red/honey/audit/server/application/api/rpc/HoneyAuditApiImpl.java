package red.honey.audit.server.application.api.rpc;

import com.github.pagehelper.PageInfo;
import org.apache.dubbo.config.annotation.DubboService;
import org.springframework.beans.factory.annotation.Autowired;
import red.honey.audit.api.rpc.HoneyAuditApi;
import red.honey.audit.common.pojo.Operation;
import red.honey.audit.common.pojo.dto.QueryDto;
import red.honey.audit.server.application.service.AuditService;

import java.util.List;

/**
 * @author yangzhijie
 * @date 2021/1/20 16:24
 */
@DubboService
public class HoneyAuditApiImpl implements HoneyAuditApi {

    @Autowired
    private AuditService auditService;

    /**
     * persist the operation logs to database
     *
     * @param operation operation
     * @return the result of the operation persistence
     */
    @Override
    public Boolean addAuditLogs(Operation operation) {
        return auditService.addAuditLogs(operation);
    }

    /**
     * delete the operation logs by ids
     *
     * @param appId the application id
     * @param ids   the list of log uid
     * @return the result of the delete operation
     */
    @Override
    public Boolean deleteAuditLogs(String appId, List<String> ids) {
        return auditService.deleteAuditLogs(appId, ids);
    }

    /**
     * query the operation logs by the query criteria
     *
     * @param queryDto the query criteria
     * @return the result of the query
     */
    @Override
    public PageInfo<Operation> queryAuditLogs(QueryDto queryDto) {
        return auditService.queryAuditLogs(queryDto);
    }

    /**
     * get the apps which are had accessed to the honey audit server
     *
     * @return all the appId
     */
    @Override
    public List<String> getApps() {
        return auditService.getApps();
    }
}
