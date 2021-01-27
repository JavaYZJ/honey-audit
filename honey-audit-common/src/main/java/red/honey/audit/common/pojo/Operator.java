package red.honey.audit.common.pojo;

import java.io.Serializable;

/**
 * @author yangzhijie
 * @date 2021/1/19 11:08
 */
public class Operator implements Serializable {

    /**
     * operatorId
     */
    private String operatorId;

    /**
     * operatorName
     */
    private String operatorName;

    /**
     * operatorIp
     */
    private String operatorIp;

    public String getOperatorId() {
        return operatorId;
    }

    public void setOperatorId(String operatorId) {
        this.operatorId = operatorId;
    }

    public String getOperatorName() {
        return operatorName;
    }

    public void setOperatorName(String operatorName) {
        this.operatorName = operatorName;
    }

    public String getOperatorIp() {
        return operatorIp;
    }

    public void setOperatorIp(String operatorIp) {
        this.operatorIp = operatorIp;
    }
}
