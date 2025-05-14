package backend1.bookingprogram.repositories;

import backend1.bookingprogram.models.Guest;
import org.springframework.data.jpa.repository.JpaRepository;

public interface GuestRepository extends JpaRepository<Guest, Long> {
}
