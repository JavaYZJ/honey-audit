package red.honey.audit.server.application.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;
import org.springframework.util.CollectionUtils;
import red.honey.audit.common.pojo.Operation;
import red.honey.audit.common.pojo.dto.QueryDto;
import red.honey.audit.server.application.mapper.AuditMapper;
import red.honey.audit.server.application.service.AuditService;

import java.util.List;

/**
 * @author yangzhijie
 * @date 2021/1/20 16:12
 */
@Service
public class AuditServiceImpl implements AuditService {

    @Autowired
    private AuditMapper auditMapper;

    /**
     * persist the operation logs to database
     *
     * @param operation operation
     * @return the result of the operation persistence
     */
    @Override
    public Boolean addAuditLogs(Operation operation) {
        Assert.notNull(operation, "operation must not be null");
        return auditMapper.addAuditLogs(operation);
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
        if (CollectionUtils.isEmpty(ids)) {
            return true;
        }
        Assert.notNull(appId, "appId must not be null");
        return auditMapper.deleteAuditLogs(appId, ids);
    }

    /**
     * query the operation logs by the query criteria
     *
     * @param queryDto the query criteria
     * @return the result of the query
     */
    @Override
    public PageInfo<Operation> queryAuditLogs(QueryDto queryDto) {
        Assert.notNull(queryDto, "queryDto must not be null");
        PageHelper.startPage(queryDto.getPageNo(), queryDto.getPageSize(), queryDto.getOrderBy());
        List<Operation> operationList = auditMapper.queryAuditLogs(queryDto);
        return new PageInfo<>(operationList);
    }

    /**
     * get the apps which are had accessed to the honey audit server
     *
     * @return all the appId
     */
    @Override
    public List<String> getApps() {
        return auditMapper.getApps();
    }
}
