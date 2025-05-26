package backend1.bookingprogram.service;


import backend1.bookingprogram.dtos.BookingDTO;
import backend1.bookingprogram.dtos.GuestDTO;
import backend1.bookingprogram.exceptions.CantDeleteException;
import backend1.bookingprogram.exceptions.ResourceAlreadyExistsException;
import backend1.bookingprogram.exceptions.ResourceDoesntExistException;
import backend1.bookingprogram.mappers.BookingMapper;
import backend1.bookingprogram.mappers.GuestMapper;
import backend1.bookingprogram.models.Booking;
import backend1.bookingprogram.models.Guest;
import backend1.bookingprogram.repositories.GuestRepository;
import jakarta.transaction.Transactional;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

import static backend1.bookingprogram.mappers.GuestMapper.guestDTOToGuestDetailed;


@Service
public class GuestService {
    private static final org.slf4j.Logger log = LoggerFactory.getLogger(GuestService.class);
    private final GuestRepository repo;

    public GuestService(GuestRepository repo) {
        this.repo = repo;
    }

    public List<GuestDTO> fetchAllGuests() {
        return repo.findAll()
                .stream()
                .map(GuestMapper::guestToGuestDTODetailed)
                .toList();
    }

    public ResponseEntity<String> createGuest(GuestDTO g) {

        if (repo.findByEmail(g.getEmail()).isPresent()) {
            throw new ResourceAlreadyExistsException(g.getEmail() + " already exists");
        }

        repo.save(guestDTOToGuestDetailed(g));
        return ResponseEntity.status(HttpStatus.CREATED).body(g.getName() + " registered!");
    }

    public void deleteGuest(Long id) {
        if (repo.findById(id).isEmpty()) {
            throw new ResourceDoesntExistException("Guest doesn't exist");
        }

        if (guestHasActiveBookings(id))
            throw new CantDeleteException("Guest could not be deleted due to active bookings.");
        else {
            log.info("Guest with ID: {} was deleted.", id);
            repo.deleteById(id);
        }
    }

    public boolean guestHasActiveBookings(Long guestID) {
        return repo.findById(guestID).get().getBookings()
                .stream().anyMatch(booking -> LocalDate.now().isBefore(booking.getDateUntil()));
    }

    public List<BookingDTO> showActiveBookings(Long guestID) {
        Guest guest = repo.findById(guestID).orElseThrow(() -> new ResourceDoesntExistException("Guest not found"));

        return guest.getBookings().stream().filter(booking -> LocalDate.now().isBefore(booking.getDateUntil())).toList().stream()
                .map(booking -> BookingMapper.bookingToBookingDTODetailed(booking)).toList();
    }

    @Transactional
    public ResponseEntity<String> alterGuest(Long id, GuestDTO g) {
        repo.findByEmail(g.getEmail()).ifPresent(existing -> {
            if (!existing.getId().equals(id)) {
                throw new ResourceAlreadyExistsException("Email address is used by another guest");
            }
        });

        Guest guest = repo.findById(id).orElseThrow(() -> new ResourceDoesntExistException("Guest not found"));
        log.info("Altering {}", guest );

        guest.setName(g.getName());
        guest.setEmail(g.getEmail());
        guest.setPhonenumber(g.getPhonenumber());

        log.info("Altered {}", guest);

        return ResponseEntity.ok("User updated");
    }


    public GuestDTO fetchGuestById(Long id) {
        return repo.findById(id)
                .map(GuestMapper::guestToGuestDTODetailed)
                .orElseThrow(() -> new ResourceDoesntExistException("No Guest " + id));
    }
}