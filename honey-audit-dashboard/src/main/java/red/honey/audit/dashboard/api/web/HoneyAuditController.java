package red.honey.audit.dashboard.api.web;

import com.eboy.honey.response.commmon.HoneyResponse;
import com.github.pagehelper.PageInfo;
import org.apache.dubbo.config.annotation.DubboReference;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import red.honey.audit.api.rpc.HoneyAuditApi;
import red.honey.audit.common.constant.OperationType;
import red.honey.audit.common.pojo.Operation;
import red.honey.audit.common.pojo.dto.QueryDto;
import red.honey.audit.dashboard.vo.*;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static red.honey.audit.dashboard.constant.Constant.*;

/**
 * @author yangzhijie
 * @date 2021/1/22 16:12
 */
@Controller
@RequestMapping("/v1/audit")
public class HoneyAuditController {

    static HomeInfo homeInfo;
    static LogoInfo logoInfo;
    static MenuInfo menuInfo;

    static {
        homeInfo = homeInfo();
        logoInfo = logoInfo();
        menuInfo = menuInfo();
    }

    @DubboReference(check = false)
    private HoneyAuditApi honeyAuditApi;

    private static HomeInfo homeInfo() {
        HomeInfo homeInfo = new HomeInfo();
        homeInfo.setTitle(HOME_INFO_TITLE);
        homeInfo.setHref(HOME_INFO_HREF);
        return homeInfo;
    }

    private static LogoInfo logoInfo() {
        LogoInfo logoInfo = new LogoInfo();
        logoInfo.setTitle(LOGO_INFO_TITLE);
        logoInfo.setImage(LOGO_INFO_IMAGE);
        logoInfo.setHref(LOGO_INFO_HREF);
        return logoInfo;
    }

    private static MenuInfo menuInfo() {
        MenuInfo menuInfo = new MenuInfo();
        menuInfo.setTitle(MENU_INFO_TITLE);
        menuInfo.setHref(MENU_INFO_HREF);
        menuInfo.setIcon(MENU_INFO_ICON);
        menuInfo.setTarget(MENU_INFO_TARGET);
        menuInfo.setChild(childInfo());
        return menuInfo;
    }

    private static List<ChildInfo> childInfo() {
        List<ChildInfo> childInfos = new ArrayList<>();
        buildAuditChild(childInfos);
        buildMonitorChild(childInfos);
        return childInfos;
    }

    private static void buildAuditChild(List<ChildInfo> childInfos) {
        ChildInfo childInfo = new ChildInfo();
        childInfo.setTitle(CHILD_INFO_AUDIT_TITLE);
        childInfo.setHref(CHILD_INFO_HREF);
        childInfo.setIcon(CHILD_INFO_ICON);
        childInfo.setTarget(CHILD_INFO_TARGET);
        childInfos.add(childInfo);
    }

    private static void buildMonitorChild(List<ChildInfo> childInfos) {
        ChildInfo childInfo = new ChildInfo();
        childInfo.setTitle(CHILD_INFO_MONITOR_TITLE);
        childInfo.setHref(CHILD_INFO_MONITOR_HREF);
        childInfo.setIcon(CHILD_INFO_ICON);
        childInfo.setTarget(CHILD_INFO_TARGET);
        childInfos.add(childInfo);
    }

    @PostMapping("/query")
    public @ResponseBody
    PageResult<Operation> queryAuditLogs(QueryVo queryVo) {
        PageInfo<Operation> pageInfo = honeyAuditApi.queryAuditLogs(convert2Dto(queryVo));
        PageResult<Operation> pageResult = new PageResult<>();
        pageResult.setCode(0);
        pageResult.setCount(pageInfo.getTotal());
        pageResult.setData(pageInfo.getList());
        return pageResult;
    }

    private QueryDto convert2Dto(QueryVo queryVo) {
        QueryDto queryDto = new QueryDto();
        BeanUtils.copyProperties(queryVo, queryDto);
        queryDto.setPageNo(queryVo.getPage());
        queryDto.setPageSize(queryVo.getLimit());
        if (queryVo.getOperationType() != null){
            queryDto.setOperationType(OperationType.getByCode(queryVo.getOperationType()));
        }
        return queryDto;
    }

    @DeleteMapping
    public HoneyResponse<Boolean> deleteAuditLogs(String appId, String ids) {
        List<String> list = Arrays.asList(ids.split(","));
        return HoneyResponse.success(honeyAuditApi.deleteAuditLogs(appId, list));
    }

    @GetMapping("/init")
    public @ResponseBody
    InitVo initialize() {
        return init();
    }

    @GetMapping("/app")
    public ModelAndView getApps() {
        List<String> apps = honeyAuditApi.getApps();
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.addObject("apps", apps);
        modelAndView.setViewName("page/welcome");
        return modelAndView;
    }

    private InitVo init() {
        InitVo initVo = new InitVo();
        initVo.setHomeInfo(homeInfo);
        initVo.setLogoInfo(logoInfo);
        initVo.setMenuInfo(Collections.singletonList(menuInfo));
        return initVo;
    }
}
