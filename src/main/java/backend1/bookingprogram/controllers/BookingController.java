package backend1.bookingprogram.controllers;

import backend1.bookingprogram.dtos.BookingDTO;
import backend1.bookingprogram.dtos.GuestDTO;
import backend1.bookingprogram.dtos.RoomDTO;
import backend1.bookingprogram.dtos.RoomSearchDTO;
import backend1.bookingprogram.models.Booking;
import backend1.bookingprogram.service.BookingService;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@Controller
public class BookingController {

    private static final org.slf4j.Logger log = LoggerFactory.getLogger(BookingController.class);
    private final BookingService service;

    public BookingController(BookingService service) {
        this.service = service;
    }


    @PostMapping("/rooms/select/guest/confirmation")
    public String createBooking(@ModelAttribute BookingDTO booking,
                                                @ModelAttribute GuestDTO guest,
                                                Model model) {
        System.out.println(guest);
        System.out.println(booking);
        booking.setGuest(guest);
        System.out.println(booking);
        service.createBooking(booking);
        return "success";
    }

    @GetMapping("/booking/{roomId}/bookings")
    public List<Booking> getBookingsForRoom(@PathVariable Long roomId) {
        return service.getBookingsForRoom(roomId);

    }

    @GetMapping("/bookings")
    public List<BookingDTO> getAllBookings() {
        return service.fetchAllBookings();
    }

    @PutMapping("/booking/{id}")
    public ResponseEntity<String> changeBooking(@PathVariable long id, @RequestBody BookingDTO b) {
        return service.alterBooking(id, b);
    }

    @DeleteMapping("booking/{id}")
    public ResponseEntity<String> cancelBooking(@PathVariable long id) {
        return service.deleteBooking(id);
    }
}