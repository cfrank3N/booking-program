package backend1.bookingprogram.controllers;

import backend1.bookingprogram.dtos.ActiveBookingDTO;
import backend1.bookingprogram.models.Booking;
import backend1.bookingprogram.service.BookingService;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import static backend1.bookingprogram.enums.RoutingInfo.*;
import static backend1.bookingprogram.mappers.BookingMapper.bookingDetailedToActiveBookingDTO;


@Controller
public class BookingController {

    private static final org.slf4j.Logger log = LoggerFactory.getLogger(BookingController.class);
    private final BookingService service;

    public BookingController(BookingService service) {
        this.service = service;
    }

    @PostMapping("/rooms/select/guest/confirmation")
    public String createBooking(@ModelAttribute ActiveBookingDTO booking,
                                @RequestParam Long gId, Model model) {

        booking.setGId(gId);
        log.info("Booking status [3/3]: booking created: {}", booking);

        Booking savedBooking = service.createBooking(booking);
        booking.setBookingId(savedBooking.getId());

        model.addAttribute("booking", booking);
        return BOOKING_CONFIRMATION.getViewName();
    }

    @DeleteMapping("booking/delete/{id}")
    public String cancelBooking(@PathVariable long id, Model model) {


        service.deleteBooking(id);
        model.addAttribute("bookings", service.fetchAllBookings());
        model.addAttribute("success", "Booking deleted successfully");

        log.info("Booking deleted, id: {}", id);

        return CHOOSE_BOOKING_TO_ALTER.getViewName();
    }

    @GetMapping("booking/alter")
    public String chooseBookingToAlter(Model model) {

        model.addAttribute("bookings", service.fetchAllBookings());
        return CHOOSE_BOOKING_TO_ALTER.getViewName();
    }

    @GetMapping("/booking/alter/{id}")
    public String showBookingForm(@PathVariable Long id, Model model) {
        ActiveBookingDTO booking = bookingDetailedToActiveBookingDTO(service.fetchBookingById(id));
        booking.setBookingId(id);
        model.addAttribute("booking", booking);
        //model.addAttribute("rooms", roomService.fetchAllRooms());
        return ALTER_BOOKING.getViewName();
    }

    @PostMapping("/booking/alter/submit")
    public String submitAlterBooking(@ModelAttribute("booking") ActiveBookingDTO b,
                                     RedirectAttributes ra,
                                     Model model) {
        try {

            log.info("Booking successfully altered: {}", b);

            service.alterBooking(b);
            ra.addFlashAttribute("success", "Booking altered");
            return "redirect:/booking/alter";
        } catch (Exception e) {

            log.warn("Booking unsuccessfully altered: {}", b);

            model.addAttribute("error", e.getMessage());
            model.addAttribute("booking", b);
            return ALTER_BOOKING.getViewName();
        }
    }
}