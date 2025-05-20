package backend1.bookingprogram.exceptions;

public class CantDeleteException extends RuntimeException {
    public CantDeleteException(String message) {
        super(message);
    }
}
