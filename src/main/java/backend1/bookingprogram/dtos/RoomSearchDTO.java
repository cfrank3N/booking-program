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
public class RoomSearchDTO {

    private LocalDate startDate;
    private LocalDate endDate;
    private int guests;

}
