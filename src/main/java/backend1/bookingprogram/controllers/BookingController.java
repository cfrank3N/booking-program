package backend1.bookingprogram.controllers;

import backend1.bookingprogram.service.BookingService;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class BookingController {
    @Autowired
    private BookingService service;

    private static final org.slf4j.Logger log = LoggerFactory.getLogger(BookingController.class);

    @DeleteMapping("booking/{id}")
    public ResponseEntity<String> cancelBooking(@PathVariable long id) {
        return service.deleteBooking(id);
    }

}