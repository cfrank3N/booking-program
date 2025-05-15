package backend1.bookingprogram.service;

import backend1.bookingprogram.exceptions.ResourceAlreadyExistsException;
import backend1.bookingprogram.models.Guest;
import backend1.bookingprogram.repositories.GuestRepository;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
public class BookingService {

    private final GuestRepository guestRepo;

    public BookingService(GuestRepository guestRepo) {
        this.guestRepo = guestRepo;
    }

    public ResponseEntity<String> createGuest(Guest g) {
        if (guestRepo.findByEmail(g.getEmail()).isPresent()) {
            throw new ResourceAlreadyExistsException(g.getEmail() + " already exists");
        }

        guestRepo.save(g);
        return ResponseEntity.status(HttpStatus.CREATED).body(g.getName() + " inserted!");
    }
}
