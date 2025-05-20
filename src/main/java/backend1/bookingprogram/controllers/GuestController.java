package backend1.bookingprogram.controllers;


import backend1.bookingprogram.service.GuestService;
import jakarta.transaction.Transactional;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.*;

import backend1.bookingprogram.models.Guest;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;

import java.util.List;

@RestController
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

    @PostMapping("/guest")
    public ResponseEntity<String> createGuest(@Valid @RequestBody Guest g) {
        return service.createGuest(g);

    }

    @PutMapping("/guest/{id}")
    public ResponseEntity<String> changeGuest(@PathVariable long id, @RequestBody Guest g) {
        return service.alterGuest(id, g);
    }

    @GetMapping("/guest")
    public List<Guest> getGuests() {
        return service.getAllGuests();
    }

}
