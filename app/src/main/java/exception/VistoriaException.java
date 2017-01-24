package exception;

/**
 * Created by Miqueias on 1/23/17.
 */

public class VistoriaException extends Exception {

    public VistoriaException() {
    }

    public VistoriaException(String detailMessage) {
        super(detailMessage);
    }

    public VistoriaException(String detailMessage, Throwable throwable) {
        super(detailMessage, throwable);
    }

    public VistoriaException(Throwable throwable) {
        super(throwable);
    }
}
