package backend1.bookingprogram.dtos;

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
    private Long id;
    @NotEmpty
    private String name;
    private String email;
    private String phonenumber;
    private List<BookingDTO> bookings;
}

