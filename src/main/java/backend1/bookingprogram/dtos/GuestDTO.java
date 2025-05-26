package backend1.bookingprogram.dtos;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class GuestDTO {
    private Long guestId;
    @NotEmpty
    private String name;
    @Email
    private String email;
    private String phonenumber;
    private List<BookingDTO> bookings;
}

