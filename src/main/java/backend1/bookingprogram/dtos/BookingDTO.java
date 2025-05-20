package backend1.bookingprogram.dtos;


import lombok.Builder;
import lombok.Data;

import java.time.LocalDate;

@Data
@Builder
public class BookingDTO {
    private Long id;
    private LocalDate dateFrom;
    private LocalDate dateUntil;
    private int numberOfGuests;
    private GuestDTO guest;
    private RoomDTO room;
}
