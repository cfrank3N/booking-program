package backend1.bookingprogram.controllers;

import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class BookingController {

    private final BookingRepository bookingRepository;
    private static final org.slf4j.Logger log = LoggerFactory.getLogger(BookingController.class);

    public BookingController(BookingRepository bookingRepository) {
        this.bookingRepository = bookingRepository;
    }

}
