package backend1.bookingprogram.dtos;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class formDTO {
    private Long bookingId;
    private Long guestId;
    private Long roomId;
    private String dateFrom;
    private String dateUntil;
}
