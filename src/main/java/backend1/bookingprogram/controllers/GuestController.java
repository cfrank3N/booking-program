package backend1.bookingprogram.controllers;



import backend1.bookingprogram.dtos.*;

import backend1.bookingprogram.exceptions.CantDeleteException;
import backend1.bookingprogram.exceptions.EmailValidationException;
import backend1.bookingprogram.exceptions.EmptyResourceException;
import backend1.bookingprogram.exceptions.ResourceAlreadyExistsException;
import backend1.bookingprogram.service.GuestService;
import jakarta.transaction.Transactional;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import jakarta.validation.Valid;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;

import static backend1.bookingprogram.enums.RoutingInfo.*;

@Controller
public class GuestController {

    private static final org.slf4j.Logger log = LoggerFactory.getLogger(GuestController.class);

    private final GuestService service;

    public GuestController(GuestService service) {
        this.service = service;
    }

    @GetMapping("/guest/register")
    public String registerGuest(Model model) {
        model.addAttribute("guest", new GuestDTO());
        return REGISTER_GUEST.getViewName();
    }

    @PostMapping("/rooms/select/guest")
    public String fetchAllGuests(@ModelAttribute ActiveBookingDTO booking,
                                 @RequestParam Long rId,
                                 Model model) {
        booking.setRId(rId);

        log.info("Booking status [2/3]: room selected: {}", booking);

        List<GuestDTO> guests = service.fetchAllGuests();
        model.addAttribute("booking", booking);
        model.addAttribute("guests", guests);
        return SELECT_GUEST.getViewName();
    }

    @PostMapping("/guest/register")
    public String registerGuest(@Valid @ModelAttribute GuestDTO g, Model model) {
        String message = service.createGuest(g).getBody();
        model.addAttribute("success", message);
        model.addAttribute("guest", new GuestDTO());
        return REGISTER_GUEST.getViewName();
    }

    @GetMapping("/guest/alter/{id}")
    public String showAlterForm(@PathVariable("id") Long id, Model model) {
        GuestDTO dto = service.fetchGuestById(id);
        model.addAttribute("guest", dto);
        return ALTER_GUEST.getViewName();
    }

    @PostMapping("/guest/alter/{id}")
    public String alterGuestSubmit(@PathVariable("id") Long id,
                                   @ModelAttribute("guest")
                                   @Valid GuestDTO guest,
                                   BindingResult bindingResult,
                                   Model model,
                                   RedirectAttributes ra) {
        if (bindingResult.hasErrors()) {
            System.out.println(bindingResult.getFieldErrors());
            if (!bindingResult.getFieldErrors("email").isEmpty()) {
                throw new EmailValidationException(guest, "Faulty email adress");
            }

        }
        if (guest.getName().isEmpty()) {
            throw new EmptyResourceException(guest, "Name can't be empty");
        }
        try {
            service.alterGuest(id, guest);
            ra.addFlashAttribute("success", "Guest updated");
            return "redirect:/guest/alter/" + id;
        } catch (ResourceAlreadyExistsException e) {
            model.addAttribute("error", e.getMessage());
            model.addAttribute("guest", guest);
            return ALTER_GUEST.getViewName();
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
        try {
            service.deleteGuest(id);
            model.addAttribute("guests", service.fetchAllGuests());
            model.addAttribute("success", "Guest deleted successfully");
            return CHOOSE_GUEST_TO_ALTER.getViewName();
        } catch (CantDeleteException e) {
            model.addAttribute("guests", service.fetchAllGuests());
            model.addAttribute("error", e.getMessage());
            return CHOOSE_GUEST_TO_ALTER.getViewName();
        }
    }

    @GetMapping("guest/bookings/{id}")
    public String showActiveBookingsOfGuest(@PathVariable Long id, Model model) {
        model.addAttribute("bookings", service.showActiveBookings(id));
        model.addAttribute("guest", service.fetchGuestById(id));
        return ACTIVE_BOOKINGS.getViewName();
    }
}