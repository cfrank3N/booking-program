package backend1.bookingprogram.exceptions;

public class ResourceDoesntExistException extends RuntimeException {
    public ResourceDoesntExistException(String message) {
        super(message);
    }
}
