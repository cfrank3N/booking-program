package backend1.bookingprogram.mappers;

import backend1.bookingprogram.dtos.RoomDTO;
import backend1.bookingprogram.models.Room;

import static backend1.bookingprogram.mappers.BookingMapper.bookingToBookingDTOMinimal;

public class RoomMapper {

    public static RoomDTO roomToRoomDTODetailed(Room r) {
        return RoomDTO.builder()
                .roomId(r.getId())
                .roomNumber(r.getRoomNumber())
                .roomName(r.getRoomName())
                .roomSize(r.getRoomSize())
                .bookings(r.getBookings()
                        .stream()
                        .map(booking -> bookingToBookingDTOMinimal(booking))
                        .toList())
                .build();
    }

    public static RoomDTO roomToRoomDTOMinimal(Room r) {
        return RoomDTO.builder()
                .roomId(r.getId())
                .roomNumber(r.getRoomNumber())
                .roomName(r.getRoomName())
                .roomSize(r.getRoomSize())
                .build();
    }

    public static Room roomDTOToRoomMinimal(RoomDTO r) {
        return Room.builder()
                .id(r.getRoomId())
                .roomNumber(r.getRoomNumber())
                .build();
    }



}
