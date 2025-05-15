package backend1.bookingprogram.service;


import backend1.bookingprogram.repositories.BookingRepository;
import backend1.bookingprogram.repositories.GuestRepository;
import backend1.bookingprogram.repositories.RoomRepository;
import org.slf4j.LoggerFactory;

import backend1.bookingprogram.exceptions.ResourceAlreadyExistsException;
import backend1.bookingprogram.models.Guest;
import backend1.bookingprogram.repositories.GuestRepository;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import org.springframework.stereotype.Service;

import java.time.LocalDate;

@Service
public class BookingService {
    private static final org.slf4j.Logger log = LoggerFactory.getLogger(BookingService.class);
    private final BookingRepository bookingRepo;
    private final GuestRepository guestRepo;
    private final RoomRepository roomRepo;

    public BookingService(BookingRepository bookingRepo, GuestRepository guestRepo, RoomRepository roomRepo) {
        this.bookingRepo = bookingRepo;
        this.guestRepo = guestRepo;
        this.roomRepo = roomRepo;
    }

    public String deleteGuest(Long id) {
        if (guestHasActiveBookings(id))
            return "The guest can not be deleted because he/she has active bookings.";
        else {
            log.info("Guest with ID: {} was deleted.", id);
            guestRepo.deleteById(id);
            return " guest(s) deleted.";
        }
    }

    public boolean guestHasActiveBookings(Long guestID){
        return bookingRepo.findAllByGuestId(guestID)
                .stream().anyMatch(booking -> LocalDate.now().isBefore(booking.getDateUntil()));
    }

    public ResponseEntity<String> createGuest(Guest g) {
        if (guestRepo.findByEmail(g.getEmail()).isPresent()) {
            throw new ResourceAlreadyExistsException(g.getEmail() + " already exists");
        }

        guestRepo.save(g);
        return ResponseEntity.status(HttpStatus.CREATED).body(g.getName() + " inserted!");
    }
}
