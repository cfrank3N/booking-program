package backend1.bookingprogram.controllers;

import backend1.bookingprogram.service.BookingService;
import jakarta.transaction.Transactional;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class GuestController {

    private static final org.slf4j.Logger log = LoggerFactory.getLogger(GuestController.class);
    private final BookingService bookingService;

    public GuestController(BookingService bookingService) {
        this.bookingService = bookingService;
    }
    @Transactional
    @DeleteMapping("/guest/{id}/delete")
    public String deleteGuest(@PathVariable Long id){
        return bookingService.deleteGuest(id);
    }
}
