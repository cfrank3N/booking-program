package backend1.bookingprogram.repositories;

import backend1.bookingprogram.models.Booking;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;
import java.util.List;

public interface BookingRepository extends JpaRepository <Booking, Long> {
    List<Booking> findAllByGuestId(Long guest_id);
    List<Booking> findByRoomIdAndDateUntilAfterAndDateFromBefore(Long roomId, LocalDate from, LocalDate until);
}
