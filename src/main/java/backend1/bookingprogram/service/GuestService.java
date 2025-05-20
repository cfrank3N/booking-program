package backend1.bookingprogram.service;


import backend1.bookingprogram.dtos.BookingDTO;
import backend1.bookingprogram.dtos.GuestDTO;

import backend1.bookingprogram.exceptions.CantDeleteException;
import backend1.bookingprogram.exceptions.ResourceAlreadyExistsException;
import backend1.bookingprogram.exceptions.ResourceDoesntExistException;
import backend1.bookingprogram.models.Guest;

import backend1.bookingprogram.repositories.GuestRepository;
import jakarta.transaction.Transactional;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;


import java.util.List;

import static backend1.bookingprogram.mappers.GuestMapper.guestToGuestDTODetailed;


import java.time.LocalDate;
import java.util.List;


@Service
public class GuestService {
    private static final org.slf4j.Logger log = LoggerFactory.getLogger(BookingService.class);
    private final GuestRepository repo;

    public GuestService(GuestRepository repo) {
        this.repo = repo;
    }


    public List<GuestDTO> fetchAllGuests() {
        return repo.findAll()
                .stream()
                .map(g -> guestToGuestDTODetailed(g))
                .toList();
    }


    public List<Guest> getAllGuests() {
        return repo.findAll();
    }

    public ResponseEntity<String> createGuest(Guest g) {
        if (repo.findByEmail(g.getEmail()).isPresent()) {
            throw new ResourceAlreadyExistsException(g.getEmail() + " already exists");
        }

        repo.save(g);
        return ResponseEntity.status(HttpStatus.CREATED).body(g.getName() + " inserted!");
    }

    public String deleteGuest(Long id) {
        if (repo.findById(id).isEmpty()) {
            throw new ResourceDoesntExistException("Guest doesn't exist");
        }

        if (guestHasActiveBookings(id))
            throw new CantDeleteException("Guest has active bookings!");
        else {
            log.info("Guest with ID: {} was deleted.", id);
            repo.deleteById(id);
            return " guest(s) deleted.";
        }
    }

    public boolean guestHasActiveBookings(Long guestID) {
        return repo.findById(guestID).get().getBookings()
                .stream().anyMatch(booking -> LocalDate.now().isBefore(booking.getDateUntil()));
    }

    @Transactional
    public ResponseEntity<String> alterGuest(Long id, Guest g) {
        if (repo.findByEmail(g.getEmail()).isPresent()) {
            throw new ResourceAlreadyExistsException("Email is taken!");
        }
        repo.findById(id).ifPresent(guest -> {

            guest.setName(g.getName());
            guest.setEmail(g.getEmail());
            guest.setPhonenumber(g.getPhonenumber());

        });

        return ResponseEntity.ok("User updated");
    }



}
