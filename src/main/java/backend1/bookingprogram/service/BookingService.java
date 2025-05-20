package backend1.bookingprogram.service;


import backend1.bookingprogram.dtos.BookingDTO;
import backend1.bookingprogram.exceptions.CantDeleteException;
import backend1.bookingprogram.exceptions.ResourceAlreadyExistsException;

import backend1.bookingprogram.exceptions.ResourceDoesntExistException;
import backend1.bookingprogram.models.Booking;
import backend1.bookingprogram.repositories.BookingRepository;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;

import static backend1.bookingprogram.mappers.BookingMapper.bookingToBookingDTODetailed;

@Service
public class BookingService {
    private static final org.slf4j.Logger log = LoggerFactory.getLogger(BookingService.class);
    private final BookingRepository bookingRepo;


    public List<BookingDTO> fetchAllBookings() {
        return bookingRepo.findAll().stream().map(booking -> bookingToBookingDTODetailed(booking)).toList();
    }

 
    public BookingService(BookingRepository bookingRepo) {

        this.bookingRepo = bookingRepo;
    }

    public ResponseEntity<String> deleteBooking(Long id) {
        if (bookingRepo.findById(id).isEmpty()) {
            throw new ResourceDoesntExistException("Can't find booking");
        }
        bookingRepo.deleteById(id);
        return ResponseEntity.ok("Booking " + id + "cancelled");
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
    }



}
