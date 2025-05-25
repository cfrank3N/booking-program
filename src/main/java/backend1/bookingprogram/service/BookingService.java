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
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;

import static backend1.bookingprogram.mappers.BookingMapper.activeBookingDTOToBookingDetailed;
import static backend1.bookingprogram.mappers.BookingMapper.bookingDTOToBookingDetailed;
import static backend1.bookingprogram.mappers.GuestMapper.guestToGuestDTODetailed;
import static backend1.bookingprogram.mappers.RoomMapper.roomToRoomDTODetailed;

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

    public ResponseEntity<String> createBooking(ActiveBookingDTO b) {

        BookingDTO booking = activeBookingDTOToBookingDetailed(b);
        booking.setRoom(roomToRoomDTODetailed(roomRepo.findById(b.getRId()).get()));
        booking.setGuest(guestToGuestDTODetailed(guestRepo.findById(b.getGId()).get()));

        //todo:I think this check is unnecessary, its already been performed in RoomService.fetchAllAvailableRooms
        if (hasOverlappingDates(booking)) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body("Room is already booked during this period.");
        }

        Booking bookToSave = bookingDTOToBookingDetailed(booking);
        bookingRepo.save(bookToSave);

        log.info("Booking process finished: Booking created: {}", bookToSave);

        return ResponseEntity.status(HttpStatus.CREATED).body("Booking created for guest " + booking.getGuest().getName());
    }

    public List<Booking> getBookingsForRoom(Long roomId) {
        return bookingRepo.findAll().stream().filter(b -> b.getRoom().getId().equals(roomId)).toList();
    }

    @Transactional
    public void alterBooking(ActiveBookingDTO activeBooking){
        BookingDTO b = activeBookingDTOToBookingDetailed(activeBooking);
        b.setBookingId(activeBooking.getBookingId());
        b.setRoom(roomToRoomDTODetailed(roomRepo.findById(activeBooking.getRId()).get()));
        b.setGuest(guestToGuestDTODetailed(guestRepo.findById(activeBooking.getGId()).get()));
        bookingRepo.findById(b.getBookingId()).ifPresent(booking -> {
            if (hasOverlappingDates(b)) {
                throw new ResourceAlreadyExistsException("The room is not available on these dates");
            } else {
                booking.setRoom(roomRepo.findById(activeBooking.getRId()).get());
                booking.setGuest(guestRepo.findById(activeBooking.getGId()).get());
                booking.setDateFrom(b.getDateFrom());
                booking.setDateUntil(b.getDateUntil());
                booking.setNumberOfGuests(b.getNumberOfGuests());
            }
        });
    }

    public boolean hasOverlappingDates(BookingDTO booking){
        // check booking dates. the stream removes overlapping dates that are from the same booking when altering booking
        List<Booking> overlapping = bookingRepo.findByRoomIdAndDateUntilAfterAndDateFromBefore(
                booking.getRoom().getRoomId(),
                booking.getDateFrom(),
                booking.getDateUntil())
                .stream()
                .filter(b -> booking.getBookingId() == null || !Objects.equals(b.getId(), booking.getBookingId()))
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
