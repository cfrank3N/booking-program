package backend1.bookingprogram.controllers;

import backend1.bookingprogram.repositories.GuestRepository;
import backend1.bookingprogram.service.BookingService;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class GuestController {

    private final GuestRepository guestRepository;
    private static final org.slf4j.Logger log = LoggerFactory.getLogger(GuestController.class);
    private final BookingService bookingService = new BookingService();

    public GuestController(GuestRepository guestRepository) {
        this.guestRepository = guestRepository;
    }

    @DeleteMapping("/guest/{id}/delete")
    public String deleteGuest(@PathVariable Long id){
        if (bookingService.guestHasActiveBookings(id))
            return "The guest can not be deleted because he/she has active bookings.";
        else {
            log.info("Guest with ID: {} was deleted.", id);
            return guestRepository.deleteGuestById(id) + " guest(s) deleted.";
        }
    }
}
