package club.someoneice.pineapplepsychic.exception;

/**
 * @deprecated
 * Is that an exception model json? Or the coder call the model in a wrong time?
 * Throw this out, Tell the user "It an Illegal State Model!".
 */
@Deprecated
public class NonModelCanReadException extends IllegalArgumentException {

    public NonModelCanReadException() {
        super();
    }

    public NonModelCanReadException(String s) {
        super(s);
    }

    public NonModelCanReadException(String message, Throwable cause) {
        super(message, cause);
    }

    public NonModelCanReadException(Throwable cause) {
        super(cause);
    }
}
