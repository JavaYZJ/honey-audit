package red.honey.audit.common.pojo;

import com.fasterxml.jackson.annotation.JsonFormat;
import red.honey.audit.common.constant.OperationType;

import java.io.Serializable;
import java.util.Date;

/**
 * @author yangzhijie
 * @date 2021/1/19 14:19
 */
public class Operation implements Serializable {


    private String appId;

    private String uid;

    private String operatorId;

    private String operatorName;

    private String operatorIp;

    private OperationType operationType;

    private String operationMethod;

    private String operationParameters;

    private String operationDetails;

    @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss")
    private Date createTime;

    public String getAppId() {
        return appId;
    }

    public void setAppId(String appId) {
        this.appId = appId;
    }

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

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

    public OperationType getOperationType() {
        return operationType;
    }

    public void setOperationType(OperationType operationType) {
        this.operationType = operationType;
    }

    public String getOperationMethod() {
        return operationMethod;
    }

    public void setOperationMethod(String operationMethod) {
        this.operationMethod = operationMethod;
    }

    public String getOperationParameters() {
        return operationParameters;
    }

    public void setOperationParameters(String operationParameters) {
        this.operationParameters = operationParameters;
    }

    public String getOperationDetails() {
        return operationDetails;
    }

    public void setOperationDetails(String operationDetails) {
        this.operationDetails = operationDetails;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }
}
