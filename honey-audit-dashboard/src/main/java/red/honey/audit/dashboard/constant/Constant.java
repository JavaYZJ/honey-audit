package red.honey.audit.dashboard.constant;

import java.util.Calendar;

/**
 * @author yangzhijie
 * @date 2021/1/25 11:06
 */
public class Constant {

    public final static String HOME_INFO_TITLE = "应用列表";

    public final static String HOME_INFO_HREF = "v1/audit/app?t=" + Calendar.getInstance().getTimeInMillis();

    public final static String LOGO_INFO_TITLE = "Honey Audit";

    public final static String LOGO_INFO_HREF = "";

    public final static String LOGO_INFO_IMAGE = "http://oss.honey.red/public/honey.png";

    public final static String MENU_INFO_TITLE = "审计模块";

    public final static String MENU_INFO_HREF = "";

    public final static String MENU_INFO_ICON = "fa fa-address-book";

    public final static String MENU_INFO_TARGET = "_self";

    public final static String CHILD_INFO_AUDIT_TITLE = "审计查询";

    public final static String CHILD_INFO_MONITOR_TITLE = "监控查询";

    public final static String CHILD_INFO_HREF = "v1/audit/app?t=" + Calendar.getInstance().getTimeInMillis();

    public final static String CHILD_INFO_MONITOR_HREF = "templates/page/404.html";

    public final static String CHILD_INFO_ICON = "fa fa-snowflake-o";

    public final static String CHILD_INFO_TARGET = "_self";

}
