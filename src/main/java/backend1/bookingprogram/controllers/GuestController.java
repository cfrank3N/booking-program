package backend1.bookingprogram.controllers;


import backend1.bookingprogram.dtos.GuestDTO;
import backend1.bookingprogram.service.BookingService;
import backend1.bookingprogram.service.GuestService;
import jakarta.transaction.Transactional;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.*;

import backend1.bookingprogram.models.Guest;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;

import java.util.List;

@RestController
public class GuestController {

    private static final org.slf4j.Logger log = LoggerFactory.getLogger(GuestController.class);
    private final BookingService bookingService;
    private final GuestService guestService;

    public GuestController(BookingService bookingService, GuestService guestService) {
        this.bookingService = bookingService;
        this.guestService = guestService;
    }
    @Transactional
    @DeleteMapping("/guest/{id}/delete")
    public String deleteGuest(@PathVariable Long id) {
        return bookingService.deleteGuest(id);

    }

    @PostMapping("/guest")
    public ResponseEntity<String> createGuest(@Valid @RequestBody Guest g) {
        return bookingService.createGuest(g);

    }

    @PutMapping("/guest/{id}")
    public ResponseEntity<String> changeGuest(@PathVariable long id, @RequestBody Guest g) {
        return bookingService.alterGuest(id, g);
    }

    @GetMapping("/guest")
    public List<GuestDTO> getGuests() {
        return guestService.fetchAllGuests();
    }

}
