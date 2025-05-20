package backend1.bookingprogram.service;

import backend1.bookingprogram.exceptions.ResourceDoesntExistException;

import backend1.bookingprogram.models.Booking;
import backend1.bookingprogram.repositories.BookingRepository;
import backend1.bookingprogram.repositories.GuestRepository;
import backend1.bookingprogram.repositories.RoomRepository;
import jakarta.transaction.Transactional;
import org.slf4j.LoggerFactory;

import backend1.bookingprogram.exceptions.ResourceAlreadyExistsException;
import backend1.bookingprogram.models.Guest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

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

    public ResponseEntity<String> deleteBooking(Long id) {
        if (bookingRepo.findById(id).isEmpty()) {
            throw new ResourceDoesntExistException("Can't find booking");
        }
        bookingRepo.deleteById(id);
        return ResponseEntity.ok("Booking " + id + "cancelled");
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


    public ResponseEntity<String> createBooking(Booking booking) {

        // check booking dates
        List<Booking> overlapping = bookingRepo.findByRoomIdAndDateUntilAfterAndDateFromBefore(
                booking.getRoom().getId(),
                booking.getDateFrom(),
                booking.getDateUntil()
        );

        // if overlapping dates in Room.getId() = fail.
        if (!overlapping.isEmpty()) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body("Room is already booked during this period.");
        }

        bookingRepo.save(booking);
        return ResponseEntity.status(HttpStatus.CREATED).body("Booking created for guest " + booking.getGuest().getName());
    }

        public List<Booking> getBookingsForRoom(Long roomId) {
        return bookingRepo.findAll().stream().filter(b -> b.getRoom().getId().equals(roomId)).toList();

    @Transactional
    public ResponseEntity<String> alterGuest(Long id, Guest g) {
        if (guestRepo.findByEmail(g.getEmail()).isPresent()) {
            throw new ResourceAlreadyExistsException("Email is taken!");
        }
        guestRepo.findById(id).ifPresent(guest -> {

            guest.setName(g.getName());
            guest.setEmail(g.getEmail());
            guest.setPhonenumber(g.getPhonenumber());

        });

        return ResponseEntity.ok("User updated");
    }

    public List<Guest> getAllGuests() {
        return guestRepo.findAll();

    }

}
