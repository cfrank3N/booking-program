package backend1.bookingprogram.dtos;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ActiveBookingDTO {
    private Long bookingId;
    private LocalDate dateFrom;
    private LocalDate dateUntil;
    private int numberOfGuests;
    private Long gId;
    private Long rId;
}
