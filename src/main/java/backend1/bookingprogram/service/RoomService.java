package backend1.bookingprogram.service;

import backend1.bookingprogram.models.Room;
import backend1.bookingprogram.repositories.RoomRepository;
import org.springframework.stereotype.Service;

import java.util.NoSuchElementException;


@Service
public class RoomService {

    private final RoomRepository roomRepository;

    public RoomService(RoomRepository roomRepository) {
        this.roomRepository = roomRepository;
    }

    // room size determines type of room. //TODO return type or something later for business logic in booking.
    public Room classifyRoom(Long roomId) {
        return roomRepository.findById(roomId)
                .map(room -> {
                    room.setRoomName(
                            switch (getRoomType(room.getRoomSize())) {
                                case 1 -> "single room (up to 1 bed)";
                                case 2 -> "double room (up to 2 beds)";
                                case 3 -> "small suite (up to 3 beds)";
                                case 4 -> "big suite (up to 4 beds)";
                                default -> throw new IllegalStateException("Unexpected room type");
                            }
                    );

                    return roomRepository.save(room);

                })
                .orElseThrow(() ->
                        new NoSuchElementException("No such room with id: " + roomId)
                );
    }

    private int getRoomType(int size) {
        if (size < 20) return 1;
        else if (size < 40) return 2;
        else if (size < 80) return 3;
        else return 4;
    }
}