package backend1.bookingprogram.mappers;

import backend1.bookingprogram.dtos.RoomDTO;
import backend1.bookingprogram.models.Room;

public class RoomMapper {

    public static RoomDTO roomToRoomDTOMinimal(Room r) {
        return RoomDTO.builder()
                .id(r.getId())
                .roomNumber(r.getRoomNumber())
                .roomName(r.getRoomName())
                .roomSize(r.getRoomSize())
                .build();
    }

}
