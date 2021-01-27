package red.honey.audit.dashboard.vo;

import java.util.List;

/**
 * @author yangzhijie
 * @date 2021/1/25 10:53
 */
public class MenuInfo {

    private String title;

    private String icon;

    private String href;

    private String target;

    private List<ChildInfo> child;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getIcon() {
        return icon;
    }

    public void setIcon(String icon) {
        this.icon = icon;
    }

    public String getHref() {
        return href;
    }

    public void setHref(String href) {
        this.href = href;
    }

    public String getTarget() {
        return target;
    }

    public void setTarget(String target) {
        this.target = target;
    }

    public List<ChildInfo> getChild() {
        return child;
    }

    public void setChild(List<ChildInfo> child) {
        this.child = child;
    }
}
