package backend1.bookingprogram.models;

import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import lombok.*;
import jakarta.validation.constraints.Pattern;

import java.util.List;

@Entity
@Data
@AllArgsConstructor(access = AccessLevel.PUBLIC)
@NoArgsConstructor
@Setter
@Getter
@Builder
public class Guest {
    @Id @GeneratedValue
    private Long id;
    @NotEmpty
    private String name;
    @Email
    @Column(unique = true)
    private String email;

    private String phonenumber;
    @OneToMany(mappedBy = "guest")
    private List<Booking> bookings;

    public Guest(String name, String email, String phonenumber, List<Booking> bookings) {
        this.name = name;
        this.email = email;
        this.phonenumber = phonenumber;
        this.bookings = bookings;
    }

    @Override
    public String toString() {
        return "Guest{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", email='" + email + '\'' +
                ", phonenumber='" + phonenumber + '\'' +
                '}';
    }
}
