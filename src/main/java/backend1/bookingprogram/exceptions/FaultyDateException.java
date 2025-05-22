package backend1.bookingprogram.exceptions;

public class FaultyDateException extends RuntimeException {
    public FaultyDateException(String message) {
        super(message);
    }
}
