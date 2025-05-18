package backend1.bookingprogram.models;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotEmpty;
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
    @NotEmpty
    private LocalDate dateFrom;
    @NotEmpty
    private LocalDate dateUntil;
    @NotEmpty
    private int numberOfGuests;
    @ManyToOne
    @JoinColumn(name = "guest_id")
    private Guest guest;
    @ManyToOne
    @JoinColumn(name = "room_id")
    private Room room;

    public Booking(LocalDate dateFrom, LocalDate dateUntil, int numberOfGuests, Guest guest, Room room) {
        this.dateFrom = dateFrom;
        this.dateUntil = dateUntil;
        this.numberOfGuests = numberOfGuests;
        this.guest = guest;
        this.room = room;
    }
}
