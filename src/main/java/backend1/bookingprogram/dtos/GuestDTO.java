package backend1.bookingprogram.dtos;

import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder
public class GuestDTO {
    private Long id;
    private String name;
    private String email;
    private String phonenumber;
    private List<BookingDTO> bookings;
}
