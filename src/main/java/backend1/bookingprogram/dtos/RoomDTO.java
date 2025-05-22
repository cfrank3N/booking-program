package backend1.bookingprogram.dtos;

import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder
public class RoomDTO {
    private Long roomId;
    private String roomNumber;
    private String roomName;
    private int roomSize;
    private List<BookingDTO> bookings;
}
