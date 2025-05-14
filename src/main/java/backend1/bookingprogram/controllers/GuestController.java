package backend1.bookingprogram.controllers;

import backend1.bookingprogram.models.Guest;
import backend1.bookingprogram.service.BookingService;
import jakarta.validation.Valid;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class GuestController {

    private static final org.slf4j.Logger log = LoggerFactory.getLogger(GuestController.class);

    @Autowired
    private BookingService bookingService;

    @PostMapping("/guest")
    public ResponseEntity<String> createGuest(@Valid @RequestBody Guest g) {
        return bookingService.createGuest(g);
    }
}
