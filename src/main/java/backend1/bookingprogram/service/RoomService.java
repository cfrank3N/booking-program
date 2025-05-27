package backend1.bookingprogram.service;

import backend1.bookingprogram.dtos.RoomDTO;
import backend1.bookingprogram.exceptions.FaultyDateException;
import backend1.bookingprogram.exceptions.ResourceDoesntExistException;
import backend1.bookingprogram.mappers.RoomMapper;
import backend1.bookingprogram.models.Booking;
import backend1.bookingprogram.repositories.BookingRepository;
import backend1.bookingprogram.repositories.RoomRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
public class RoomService {

    private final RoomRepository roomRepository;
    private final BookingRepository bookingRepository;

    public RoomService(RoomRepository roomRepository, BookingRepository bookingRepository) {
        this.roomRepository = roomRepository;
        this.bookingRepository = bookingRepository;
    }

    public static int getMinRoomSize(int numberOfGuests) {
        return switch (numberOfGuests) {
            case 4 -> 40;
            case 3 -> 30;
            case 2 -> 20;
            default -> 10;
        };
    }

    public List<RoomDTO> fetchAllAvailableRooms(LocalDate startDate, LocalDate endDate, int numberOfGuests) {

        validateBookingFormFields(startDate, endDate);
        int minRoomSize = getMinRoomSize(numberOfGuests);

        return  roomRepository.findAll()
                .stream()
                .filter(room -> room.getBookings()
                        .stream()
                        .noneMatch(booking -> booking.getDateFrom().isBefore(endDate) &&
                                booking.getDateUntil().isAfter(startDate)))
                .filter(room -> room.getRoomSize()>=minRoomSize)
                .map(RoomMapper::roomToRoomDTODetailed).toList();
    }

    public List<RoomDTO> fetchAllAvailableRoomsForAlterBooking(LocalDate startDate, LocalDate endDate,
            int numberOfGuests,
            long bookingId) {

        validateBookingFormFields(startDate, endDate);

        int minRoomSize = getMinRoomSize(numberOfGuests);

        //TODO ExceptionHandling for this not implemented
        Booking currentBooking = bookingRepository.findById(bookingId)
                .orElseThrow(() -> new ResourceDoesntExistException("Booking not found"));

        Long currentRoomId = currentBooking.getRoom().getId();

        return roomRepository.findAll()
                .stream()
                .filter(room -> {
                    boolean isAvailable = room.getBookings().stream()
                            .filter(booking -> booking.getId() != bookingId)
                            .noneMatch(booking ->
                                    booking.getDateFrom().isBefore(endDate) &&
                                            booking.getDateUntil().isAfter(startDate));

                    return isAvailable || room.getId().equals(currentRoomId);
                })
                .filter(room -> room.getRoomSize() >= minRoomSize)
                .map(RoomMapper::roomToRoomDTODetailed)
                .toList();
    }


    public List<RoomDTO> fetchAllRooms() {
        return roomRepository.findAll()
                .stream()
                .map(RoomMapper::roomToRoomDTODetailed)
                .toList();
    }

    public void validateBookingFormFields(LocalDate startDate, LocalDate endDate) {
        if (startDate == null || endDate == null) {
            throw new FaultyDateException("Must select both dates!");
        }
        if (startDate.isBefore(LocalDate.now())) {
            throw new FaultyDateException("From date can't be in the past!");
        }
        if (endDate.isBefore(startDate)) {
            throw new FaultyDateException("'To' date can't be before 'From' date");
        }
    }
}
