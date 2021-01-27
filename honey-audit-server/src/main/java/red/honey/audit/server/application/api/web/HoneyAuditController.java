package red.honey.audit.server.application.api.web;

import com.eboy.honey.response.commmon.HoneyResponse;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import red.honey.audit.common.pojo.Operation;
import red.honey.audit.common.pojo.dto.QueryDto;
import red.honey.audit.server.application.service.AuditService;

import java.util.Arrays;
import java.util.List;

/**
 * @author yangzhijie
 * @date 2021/1/20 16:28
 */
@RestController
@RequestMapping("/v1/audit")
public class HoneyAuditController {


    @Autowired
    private AuditService auditService;


    @PostMapping("/query")
    public HoneyResponse<PageInfo<Operation>> queryAuditLogs(QueryDto queryDto) {
        return HoneyResponse.success(auditService.queryAuditLogs(queryDto));
    }

    @DeleteMapping
    public HoneyResponse<Boolean> deleteAuditLogs(String appId, String ids) {
        List<String> list = Arrays.asList(ids.split(","));
        return HoneyResponse.success(auditService.deleteAuditLogs(appId, list));
    }

}
