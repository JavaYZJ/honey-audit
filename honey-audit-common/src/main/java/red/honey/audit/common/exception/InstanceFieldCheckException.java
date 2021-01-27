package red.honey.audit.common.exception;

/**
 * @author yangzhijie
 * @date 2021/1/21 10:37
 */
public class InstanceFieldCheckException extends RuntimeException {


    public InstanceFieldCheckException() {
        super();
    }

    public InstanceFieldCheckException(String message) {
        super(message);
    }

    public InstanceFieldCheckException(String message, Throwable cause) {
        super(message, cause);
    }

    public InstanceFieldCheckException(Throwable cause) {
        super(cause);
    }
}
