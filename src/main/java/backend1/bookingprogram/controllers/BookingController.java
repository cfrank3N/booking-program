package backend1.bookingprogram.controllers;

import backend1.bookingprogram.dtos.BookingDTO;
import backend1.bookingprogram.models.Booking;
import backend1.bookingprogram.service.BookingService;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
public class BookingController {

    private static final org.slf4j.Logger log = LoggerFactory.getLogger(BookingController.class);
    private final BookingService service;

    public BookingController(BookingService service) {
        this.service = service;
    }

    @PostMapping("/booking")
    public ResponseEntity<String> createBooking(@RequestBody Booking booking) {
        return service.createBooking(booking);
    }

    @GetMapping("/booking/{roomId}/bookings")
    public List<Booking> getBookingsForRoom(@PathVariable Long roomId) {
        return service.getBookingsForRoom(roomId);

    }

    @GetMapping("/bookings")
    public List<BookingDTO> getAllBookings() {
        return service.fetchAllBookings();
    }

    @DeleteMapping("booking/{id}")
    public ResponseEntity<String> cancelBooking(@PathVariable long id) {
        return service.deleteBooking(id);
    }
}