package backend1.bookingprogram.controllers;

import backend1.bookingprogram.dtos.ActiveBookingDTO;
import backend1.bookingprogram.dtos.BookingDTO;
import backend1.bookingprogram.models.Booking;
import backend1.bookingprogram.service.BookingService;
import backend1.bookingprogram.service.RoomService;
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
    private final RoomService roomService;

    public BookingController(BookingService service, RoomService roomService) {
        this.service = service;
        this.roomService = roomService;
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

    @DeleteMapping("booking/delete/{id}")
    public String cancelBooking(@PathVariable long id, Model model) {
        service.deleteBooking(id);
        model.addAttribute("bookings", service.fetchAllBookings());
        model.addAttribute("success", "Booking deleted successfully");
        return "choose-booking-to-alter";
    }

    @GetMapping("booking/alter")
    public String chooseBookingToAlter(Model model) {
        model.addAttribute("bookings", service.fetchAllBookings());
        return "choose-booking-to-alter";
    }

    @GetMapping("/booking/alter/{id}")
    public String showBookingForm(@PathVariable Long id, Model model) {
        BookingDTO booking = service.fetchBookingById(id);
        model.addAttribute("booking", booking);
        model.addAttribute("rooms", roomService.fetchAllRooms());
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
            ra.addFlashAttribute("success", "Booking altered");
            return "redirect:/booking/alter";
        } catch (Exception e) {
            model.addAttribute("error", e.getMessage());
            model.addAttribute("booking", b);
            return "alter-booking";
        }
    }

}