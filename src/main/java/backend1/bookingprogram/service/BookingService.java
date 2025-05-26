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

    public Booking createBooking(ActiveBookingDTO b) {

        BookingDTO booking = activeBookingDTOToBookingDetailed(b);
        roomRepo.findById(b.getRId()).ifPresent(r -> booking.setRoom(roomToRoomDTODetailed(r)));
        guestRepo.findById(b.getRId()).ifPresent(g -> booking.setGuest(guestToGuestDTODetailed(g)));
        if (hasOverlappingDates(booking)) {
            throw new ResourceAlreadyExistsException("Room is already booked during this time period.");
        }

        Booking bookToSave = bookingDTOToBookingDetailed(booking);
        Booking savedBooking = bookingRepo.save(bookToSave);

        log.info("Booking process finished: Booking created: {}", savedBooking);

        return savedBooking;
    }

    @Transactional
    public void alterBooking(ActiveBookingDTO activeBooking){
        BookingDTO b = activeBookingDTOToBookingDetailed(activeBooking);
        b.setBookingId(activeBooking.getBookingId());

        roomRepo.findById(activeBooking.getRId()).ifPresent(r -> b.setRoom(roomToRoomDTODetailed(r)));
        guestRepo.findById(activeBooking.getRId()).ifPresent(g -> b.setGuest(guestToGuestDTODetailed(g)));

        bookingRepo.findById(b.getBookingId()).ifPresent(booking -> {
            if (hasOverlappingDates(b)) {
                throw new ResourceAlreadyExistsException("The room is not available on these dates");
            } else {

                guestRepo.findById(activeBooking.getGId()).ifPresent(booking::setGuest);
                roomRepo.findById(activeBooking.getRId()).ifPresent(booking::setRoom);

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
