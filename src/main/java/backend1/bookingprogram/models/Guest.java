package backend1.bookingprogram.models;

import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import lombok.*;

import java.util.List;

@Entity
@Data
@AllArgsConstructor(access = AccessLevel.PUBLIC)
@NoArgsConstructor
@Setter
@Getter
public class Guest {
    @Id @GeneratedValue
    private Long id;
    @NotEmpty
    private String name;
    @Email
    @Column(unique = true)
    private String email;
    private String phonenumber;
    @OneToMany
    @JoinColumn(name = "booking_id")
    private List<Booking> bookings;
}
