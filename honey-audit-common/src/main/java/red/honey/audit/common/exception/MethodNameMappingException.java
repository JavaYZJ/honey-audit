package red.honey.audit.common.exception;

/**
 * @author yangzhijie
 * @date 2021/1/20 9:54
 */
public class MethodNameMappingException extends Exception {

    public MethodNameMappingException() {
        super();
    }

    public MethodNameMappingException(String message) {
        super(message);
    }

    public MethodNameMappingException(String message, Throwable cause) {
        super(message, cause);
    }

    public MethodNameMappingException(Throwable cause) {
        super(cause);
    }
}
