package red.honey.audit.common.exception;

/**
 * @author yangzhijie
 * @date 2021/1/19 15:00
 */
public class OperatorException extends Exception {


    public OperatorException() {
        super();
    }

    public OperatorException(String message) {
        super(message);
    }

    public OperatorException(String message, Throwable cause) {
        super(message, cause);
    }

    public OperatorException(Throwable cause) {
        super(cause);
    }
}
