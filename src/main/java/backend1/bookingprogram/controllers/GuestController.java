package backend1.bookingprogram.controllers;

import backend1.bookingprogram.repositories.GuestRepository;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class GuestController {

    private final GuestRepository guestRepository;
    private static final org.slf4j.Logger log = LoggerFactory.getLogger(GuestController.class);

    public GuestController(GuestRepository guestRepository) {
        this.guestRepository = guestRepository;
    }

}
