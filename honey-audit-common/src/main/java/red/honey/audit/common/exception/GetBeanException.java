package red.honey.audit.common.exception;

/**
 * @author yangzhijie
 * @date 2021/1/20 10:52
 */
public class GetBeanException extends Exception {

    public GetBeanException() {
        super();
    }

    public GetBeanException(String message) {
        super(message);
    }

    public GetBeanException(String message, Throwable cause) {
        super(message, cause);
    }

    public GetBeanException(Throwable cause) {
        super(cause);
    }
}
