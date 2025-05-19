package backend1.bookingprogram.models;

import jakarta.persistence.*;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.time.LocalDate;

@Entity
@Data
@AllArgsConstructor(access = AccessLevel.PUBLIC)
@NoArgsConstructor

public class Booking {
    @Id
    @GeneratedValue
    private Long id;
    @NotNull
    private LocalDate dateFrom;
    @NotNull
    private LocalDate dateUntil;
    @Min(value = 1)
    private int numberOfGuests;
    @ManyToOne
    @JoinColumn(name = "guest_id")
    private Guest guest;
    @ManyToOne
    @JoinColumn(name = "room_id")
    private Room room;
}
