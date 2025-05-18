package backend1.bookingprogram.service;

import backend1.bookingprogram.models.Room;
import backend1.bookingprogram.repositories.RoomRepository;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class RoomTypeService {

    private final RoomRepository roomRepository;

    public RoomTypeService(RoomRepository roomRepository) {
        this.roomRepository = roomRepository;
    }

    public String classifyRoom(Long roomId) {
        return roomRepository.findById(roomId).map(room -> switch (getRoomTypeCode(room.getSize())) {
            case 1 ->
        }
    }
}
