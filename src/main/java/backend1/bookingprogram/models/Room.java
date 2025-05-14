package backend1.bookingprogram.models;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotEmpty;
import lombok.*;

import java.util.List;

@Entity
@Data
@AllArgsConstructor(access = AccessLevel.PUBLIC)
@NoArgsConstructor
@Setter
@Getter
public class Room {
    @Id
    @GeneratedValue
    private Long id;
    @NotEmpty
    private String roomNumber;
    private String roomName;
    @NotEmpty
    private int size;
    @OneToMany
    @JoinColumn(name = "booking_id")
    private List<Booking> bookings;
}
