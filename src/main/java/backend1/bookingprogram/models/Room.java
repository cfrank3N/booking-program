package backend1.bookingprogram.models;

import jakarta.persistence.*;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Pattern;
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
    @Pattern(regexp = "^[1-9][0-9][1-9]$", message = "Room number must be 3 digits starting with 1-9, and the last digit cannot be 0.")
    private String roomNumber;
    private String roomName;

    @Min(value = 1, message ="Room size can't be smaller than 1kvm")

    private int roomSize;
    @OneToMany(mappedBy = "room")
    private List<Booking> bookings;

    public Room(String roomNumber, String roomName, int roomSize, List<Booking> bookings) {
        this.roomNumber = roomNumber;
        this.roomName = roomName;
        this.roomSize = roomSize;
        this.bookings = bookings;
    }
}
