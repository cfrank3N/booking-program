package backend1.bookingprogram.service;

import backend1.bookingprogram.dtos.RoomDTO;
import backend1.bookingprogram.exceptions.FaultyDateException;
import backend1.bookingprogram.mappers.RoomMapper;
import backend1.bookingprogram.repositories.RoomRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
public class RoomService {

    private final RoomRepository roomRepository;

    public RoomService(RoomRepository roomRepository) {
        this.roomRepository = roomRepository;
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

        if (startDate == null || endDate == null) {
            throw new FaultyDateException("Both dates must be filled out!");
        }
        if (startDate.isBefore(LocalDate.now())) {
            throw new FaultyDateException("From date can't be in the past!");
        }
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

    public List<RoomDTO> fetchAllRooms() {
        return roomRepository.findAll()
                .stream()
                .map(RoomMapper::roomToRoomDTODetailed)
                .toList();
    }
}
