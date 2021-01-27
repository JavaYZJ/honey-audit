package red.honey.audit.dashboard.vo;

import java.util.List;

/**
 * @author yangzhijie
 * @date 2021/1/25 11:03
 */
public class InitVo {

    private HomeInfo homeInfo;

    private LogoInfo logoInfo;

    private List<MenuInfo> menuInfo;

    public HomeInfo getHomeInfo() {
        return homeInfo;
    }

    public void setHomeInfo(HomeInfo homeInfo) {
        this.homeInfo = homeInfo;
    }

    public LogoInfo getLogoInfo() {
        return logoInfo;
    }

    public void setLogoInfo(LogoInfo logoInfo) {
        this.logoInfo = logoInfo;
    }

    public List<MenuInfo> getMenuInfo() {
        return menuInfo;
    }

    public void setMenuInfo(List<MenuInfo> menuInfo) {
        this.menuInfo = menuInfo;
    }
}
