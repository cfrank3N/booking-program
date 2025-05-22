package backend1.bookingprogram.controllers;



import backend1.bookingprogram.dtos.GuestDTO;

import backend1.bookingprogram.service.GuestService;
import jakarta.transaction.Transactional;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;

import java.util.List;

import static backend1.bookingprogram.enums.RoutingInfo.REGISTER_GUEST;

@Controller
public class GuestController {

    private static final org.slf4j.Logger log = LoggerFactory.getLogger(GuestController.class);

    private final GuestService service;

    public GuestController(GuestService service) {
        this.service = service;
    }
    @Transactional
    @DeleteMapping("/guest/{id}/delete")
    public String deleteGuest(@PathVariable Long id) {
        return service.deleteGuest(id);
    }

    @GetMapping("/guest/register")
    public String viewHomepage(Model model) {
        model.addAttribute("guest", new GuestDTO());
        return REGISTER_GUEST.getViewName();
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

}
