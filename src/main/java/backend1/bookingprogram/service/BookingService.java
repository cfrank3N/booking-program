package backend1.bookingprogram.service;


import backend1.bookingprogram.dtos.BookingDTO;

import backend1.bookingprogram.exceptions.ResourceAlreadyExistsException;
import backend1.bookingprogram.exceptions.ResourceDoesntExistException;
import backend1.bookingprogram.models.Booking;
import backend1.bookingprogram.repositories.BookingRepository;
import jakarta.transaction.Transactional;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;

import static backend1.bookingprogram.mappers.BookingMapper.bookingDTOToBookingDetailed;
import static backend1.bookingprogram.mappers.BookingMapper.bookingToBookingDTODetailed;
import static backend1.bookingprogram.mappers.RoomMapper.roomDTOToRoomMinimal;

@Service
public class BookingService {
    private static final org.slf4j.Logger log = LoggerFactory.getLogger(BookingService.class);
    private final BookingRepository bookingRepo;

    public BookingService(BookingRepository bookingRepo) {
        this.bookingRepo = bookingRepo;
    }

    public List<BookingDTO> fetchAllBookings() {
        return bookingRepo.findAll().stream().map(booking -> bookingToBookingDTODetailed(booking)).toList();
    }

    public ResponseEntity<String> deleteBooking(Long id) {
        if (bookingRepo.findById(id).isEmpty()) {
            throw new ResourceDoesntExistException("Can't find booking");
        }
        bookingRepo.deleteById(id);
        return ResponseEntity.ok("Booking " + id + "cancelled");
    }

    public ResponseEntity<String> createBooking(BookingDTO booking) {

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

        Booking b = bookingDTOToBookingDetailed(booking);
        bookingRepo.save(b);
        return ResponseEntity.status(HttpStatus.CREATED).body("Booking created for guest " + booking.getGuest().getName());
    }

    public List<Booking> getBookingsForRoom(Long roomId) {
        return bookingRepo.findAll().stream().filter(b -> b.getRoom().getId().equals(roomId)).toList();
    }

    @Transactional
    public ResponseEntity<String> alterBooking(Long id, BookingDTO b){
        bookingRepo.findById(id).ifPresent(booking -> {
            if (hasOverlappingDates(b)) {
                throw new ResourceAlreadyExistsException("The room is not available on these dates");
            } else {
                booking.setDateFrom(b.getDateFrom());
                booking.setDateUntil(b.getDateUntil());
                booking.setNumberOfGuests(b.getNumberOfGuests());
                booking.setRoom(roomDTOToRoomMinimal(b.getRoom()));
            }
        });
        return ResponseEntity.ok("Booking updated");
    }

    public boolean hasOverlappingDates(BookingDTO booking){
        // check booking dates. the stream removes overlapping dates that are from the same booking when altering booking
        List<Booking> overlapping = bookingRepo.findByRoomIdAndDateUntilAfterAndDateFromBefore(
                booking.getRoom().getId(),
                booking.getDateFrom(),
                booking.getDateUntil())
                .stream().filter(b -> booking.getId() == null || !Objects.equals(b.getId(), booking.getId())).toList();
        // if overlapping dates in Room.getId() = fail.
        return !overlapping.isEmpty();
    }

}
