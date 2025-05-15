package backend1.bookingprogram.controllers;

import backend1.bookingprogram.service.BookingService;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class BookingController {
    @Autowired
    private BookingService service;

    private static final org.slf4j.Logger log = LoggerFactory.getLogger(BookingController.class);

}