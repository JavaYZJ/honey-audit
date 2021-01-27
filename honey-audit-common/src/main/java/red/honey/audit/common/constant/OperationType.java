package red.honey.audit.common.constant;

import java.io.Serializable;

/**
 * @author yangzhijie
 * @date 2021/1/19 10:13
 */
public enum OperationType implements Serializable {

    /**
     * add operation
     */
    ADD(0, "add operation"),

    /**
     * update operation
     */
    UPDATE(1, "update operation"),

    /**
     * view operation
     */
    VIEW(2, "view operation"),

    /**
     * remove operation
     */
    REMOVE(3, "remove operation");

    public int code;

    public String description;


    OperationType(int code, String description) {
        this.code = code;
        this.description = description;
    }


    public static OperationType getByCode(int code) {
        OperationType[] values = values();
        for (OperationType value : values) {
            if (code == value.code) {
                return value;
            }
        }
        return null;
    }

}
