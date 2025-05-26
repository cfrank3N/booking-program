package backend1.bookingprogram.service;


import backend1.bookingprogram.dtos.ActiveBookingDTO;
import backend1.bookingprogram.dtos.BookingDTO;

import backend1.bookingprogram.exceptions.ResourceAlreadyExistsException;
import backend1.bookingprogram.exceptions.ResourceDoesntExistException;
import backend1.bookingprogram.mappers.BookingMapper;
import backend1.bookingprogram.models.Booking;
import backend1.bookingprogram.repositories.BookingRepository;
import backend1.bookingprogram.repositories.GuestRepository;
import backend1.bookingprogram.repositories.RoomRepository;
import jakarta.transaction.Transactional;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;

import static backend1.bookingprogram.mappers.BookingMapper.*;

@Service
public class BookingService {
    private static final org.slf4j.Logger log = LoggerFactory.getLogger(BookingService.class);
    private final BookingRepository bookingRepo;
    private final RoomRepository roomRepo;
    private final GuestRepository guestRepo;

    public BookingService(BookingRepository bookingRepo, RoomRepository roomRepo, GuestRepository guestRepo) {
        this.bookingRepo = bookingRepo;
        this.roomRepo = roomRepo;
        this.guestRepo = guestRepo;
    }

    public List<BookingDTO> fetchAllBookings() {
        return bookingRepo.findAll().stream().map(BookingMapper::bookingToBookingDTODetailed).toList();
    }

    public void deleteBooking(Long id) {
        if (bookingRepo.findById(id).isEmpty()) {
            throw new ResourceDoesntExistException("Can't find booking");
        }
        bookingRepo.deleteById(id);
    }

    public BookingDTO createBooking(ActiveBookingDTO b) {

        Booking booking = activeBookingDTOToBookingDetailed(b);
        roomRepo.findById(b.getRId()).ifPresent(booking::setRoom);
        guestRepo.findById(b.getGId()).ifPresent(booking::setGuest);
        if (hasOverlappingDates(booking)) {
            throw new ResourceAlreadyExistsException("Room is already booked during this time period.");
        }

        bookingRepo.save(booking);

        log.info("Booking process finished: Booking created: {}", booking);

        return bookingToBookingDTODetailed(booking);
    }

    @Transactional
    public void alterBooking(ActiveBookingDTO activeBooking){
        Booking b = activeBookingDTOToBookingDetailed(activeBooking);
        b.setId(activeBooking.getBookingId());

        roomRepo.findById(activeBooking.getRId()).ifPresent(b::setRoom);
        guestRepo.findById(activeBooking.getGId()).ifPresent(b::setGuest);

        bookingRepo.findById(b.getId()).ifPresent(booking -> {
            if (hasOverlappingDates(b)) {
                throw new ResourceAlreadyExistsException("The room is not available on these dates");
            } else {

                booking.setRoom(b.getRoom());
                booking.setGuest(b.getGuest());
                booking.setDateFrom(b.getDateFrom());
                booking.setDateUntil(b.getDateUntil());
                booking.setNumberOfGuests(b.getNumberOfGuests());
            }
        });
    }

    public boolean hasOverlappingDates(Booking booking){
        // check booking dates. the stream removes overlapping dates that are from the same booking when altering booking
        List<Booking> overlapping = bookingRepo.findByRoomIdAndDateUntilAfterAndDateFromBefore(
                booking.getRoom().getId(),
                booking.getDateFrom(),
                booking.getDateUntil())
                .stream()
                .filter(b -> booking.getId() == null || !Objects.equals(b.getId(), booking.getId()))
                .filter(room -> booking.getRoom().getRoomSize()>=RoomService.getMinRoomSize(booking.getNumberOfGuests()))
                .toList();
        // if overlapping dates in Room.getId() = fail.
        return !overlapping.isEmpty();
    }

    public BookingDTO fetchBookingById(Long id) {
        return bookingRepo.findById(id)
                .map(BookingMapper::bookingToBookingDTODetailed)
                .orElseThrow(() -> new ResourceDoesntExistException("Booking with ID: " +id +"not found"));
    }

}
