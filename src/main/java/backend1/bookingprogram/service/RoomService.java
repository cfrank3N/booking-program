package backend1.bookingprogram.service;

import backend1.bookingprogram.repositories.RoomRepository;
import org.springframework.stereotype.Service;


@Service
public class RoomService {

    private final RoomRepository roomRepository;

    public RoomService(RoomRepository roomRepository) {
        this.roomRepository = roomRepository;
    }

    // room size determines type of room. //TODO return type or something later for business logic in booking.
    public String classifyRoom(Long roomId) {
        return roomRepository.findById(roomId).map(room -> switch (getRoomType(room.getRoomSize())) {
            case 1 -> "Single Room (1 bed)";
            case 2 -> "Double room (1-2 beds)";
            default -> "Suite (up to 3 beds)";
        }).orElse("Room not found");
    }

    private int getRoomType(int size) {
        if (size < 20) return 1;
        else if (size < 40) return 2;
        else return 3;
    }
}
