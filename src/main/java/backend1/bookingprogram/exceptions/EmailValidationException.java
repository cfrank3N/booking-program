package backend1.bookingprogram.exceptions;

import backend1.bookingprogram.dtos.GuestDTO;

public class EmailValidationException extends RuntimeException {

    private GuestDTO guest;

    public EmailValidationException(GuestDTO guest, String message) {
        super(message);
        this.guest = guest;
    }

    public GuestDTO getGuest() {
        return guest;
    }
}
