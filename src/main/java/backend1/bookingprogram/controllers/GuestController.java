package backend1.bookingprogram.controllers;



import backend1.bookingprogram.dtos.*;

import backend1.bookingprogram.enums.RoutingInfo;
import backend1.bookingprogram.exceptions.ResourceAlreadyExistsException;
import backend1.bookingprogram.service.GuestService;
import jakarta.transaction.Transactional;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;

import static backend1.bookingprogram.enums.RoutingInfo.REGISTER_GUEST;

@Controller
public class GuestController {

    private static final org.slf4j.Logger log = LoggerFactory.getLogger(GuestController.class);

    private final GuestService service;

    public GuestController(GuestService service) {
        this.service = service;
    }

    @GetMapping("/guest/register")
    public String viewHomepage(Model model) {
        model.addAttribute("guest", new GuestDTO());
        return REGISTER_GUEST.getViewName();
    }

    @PostMapping("/rooms/select/guest")
    public String fetchAllGuests(@ModelAttribute ActiveBookingDTO booking,
                                 @RequestParam Long rId,
                                 Model model) {
        booking.setRId(rId);

        log.info("Booking status [2/3]: room selected: {}", booking);

//        System.out.println("This is when we fetch the room");
//        System.out.println(booking);

        List<GuestDTO> guests = service.fetchAllGuests();
        model.addAttribute("booking", booking);
        model.addAttribute("guests", guests);
        return "select-guest";
    }

    @PostMapping("/guest/register")
    public String registerGuest(@Valid @ModelAttribute GuestDTO g, Model model) {
        String message = service.createGuest(g).getBody();
        model.addAttribute("success", message);
        model.addAttribute("guest", new GuestDTO());
        return REGISTER_GUEST.getViewName();
    }

    @PostMapping("/guest")
    public ResponseEntity<String> createGuest(@Valid @RequestBody GuestDTO g) {
        return service.createGuest(g);

    }

    @PutMapping("/guest/{id}")
    public ResponseEntity<String> changeGuest(@PathVariable long id, @RequestBody GuestDTO g) {
        return service.alterGuest(id, g);
    }

    @GetMapping("/guest")
    public List<GuestDTO> getGuests() {
        return service.fetchAllGuests();
    }

    @GetMapping("/guest/alter/{id}")
    public String showAlterForm(@PathVariable("id") Long id, Model model) {
        GuestDTO dto = service.fetchGuestById(id);
        model.addAttribute("guest", dto);
        return "alter-guest";
    }

    @PostMapping("/guest/alter/{id}")
    public String alterGuestSubmit(@PathVariable("id") Long id,
                                   @ModelAttribute("guest")
                                   @Valid GuestDTO guest,
                                   Model model,
                                   RedirectAttributes ra) {
        try {
            service.alterGuest(id, guest);
            ra.addFlashAttribute("success", "Guest updated");
            return "redirect:/guest/alter/" + id;
        } catch (ResourceAlreadyExistsException e) {
            model.addAttribute("error", e.getMessage());
            model.addAttribute("guest", guest);
            return "alter-guest";
        }
    }

    @GetMapping("guest/alter")
    public String chooseGuestToAlter(Model model) {
        model.addAttribute("guests", service.fetchAllGuests());
        return "choose-guest-to-alter";
    }

    @Transactional
    @DeleteMapping("/guest/delete/{id}")
    public String deleteGuest(@PathVariable Long id, Model model) {
        service.deleteGuest(id);
        model.addAttribute("guests", service.fetchAllGuests());
        model.addAttribute("success", "Guest deleted successfully");
        return "choose-guest-to-alter";
    }

    @GetMapping("guest/bookings/{id}")
    public String showActiveBookingsOfGuest(@PathVariable Long id, Model model) {
        model.addAttribute("bookings", service.showActiveBookings(id));
        model.addAttribute("guest", service.fetchGuestById(id));
        return "active-bookings";
    }
}