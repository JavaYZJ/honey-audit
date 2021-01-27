package red.honey.audit.common.exception;

/**
 * @author yangzhijie
 * @date 2021/1/20 11:47
 */
public class DaoMapperException extends Exception {

    public DaoMapperException() {
        super();
    }

    public DaoMapperException(String message) {
        super(message);
    }

    public DaoMapperException(String message, Throwable cause) {
        super(message, cause);
    }

    public DaoMapperException(Throwable cause) {
        super(cause);
    }
}
