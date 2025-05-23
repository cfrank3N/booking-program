package backend1.bookingprogram.controllers;

import backend1.bookingprogram.dtos.ActiveBookingDTO;
import backend1.bookingprogram.dtos.BookingDTO;
import backend1.bookingprogram.models.Booking;
import backend1.bookingprogram.service.BookingService;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;


@Controller
public class BookingController {

    private static final org.slf4j.Logger log = LoggerFactory.getLogger(BookingController.class);
    private final BookingService service;

    public BookingController(BookingService service) {
        this.service = service;
    }


    // fixa här
    @PostMapping("/rooms/select/guest/confirmation")
    public String createBooking(@ModelAttribute ActiveBookingDTO booking,
                                @RequestParam Long gId,
                                Model model) {
        System.out.println(gId);
        System.out.println(booking);
        booking.setGId(gId);
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

    @GetMapping("booking/alter")
    public String chooseBookingToAlter(Model model) {
        List<BookingDTO> bookings = service.fetchAllBookings();
        model.addAttribute("bookings", service.fetchAllBookings());
        return "choose-booking-to-alter";
    }

    @GetMapping("/booking/alter/{id}")
    public String showBookingForm(@PathVariable Long id, Model model) {
        BookingDTO booking = service.fetchBookingById(id);
        model.addAttribute("booking", booking);
        return "alter-booking";
    }

    @PostMapping("/booking/alter/{id}")
    public String submitAlterBooking(@PathVariable Long id,
                                     @ModelAttribute("booking")
                                     BookingDTO b,
                                     RedirectAttributes ra,
                                     Model model) {
        try {
            service.alterBooking(id, b);
            ra.addFlashAttribute("successs", "Booking altered");
            return "redirect:/booking/alter";
        } catch (Exception e) {
            model.addAttribute("error", e.getMessage());
            model.addAttribute("booking", b);
            return "alter-booking";
        }
    }

}