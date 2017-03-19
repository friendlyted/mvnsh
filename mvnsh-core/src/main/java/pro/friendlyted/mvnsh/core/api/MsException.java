package pro.friendlyted.mvnsh.core.api;

/**
 *
 * @author Fedor
 */
public class MsException extends Exception {

    public MsException() {
    }

    public MsException(String message) {
        super(message);
    }

    public MsException(String message, Throwable cause) {
        super(message, cause);
    }

    public MsException(Throwable cause) {
        super(cause);
    }
    
}
