package backend1.bookingprogram.exceptions;

import backend1.bookingprogram.dtos.GuestDTO;

public class EmptyResourceException extends RuntimeException {

   private GuestDTO guest;

    public EmptyResourceException(GuestDTO guest, String message) {
        super(message);
        this.guest = guest;
    }

  public GuestDTO getGuest() {
    return guest;
  }
}
