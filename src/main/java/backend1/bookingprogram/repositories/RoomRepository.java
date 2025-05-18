package backend1.bookingprogram.repositories;

import backend1.bookingprogram.models.Room;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RoomRepository extends JpaRepository<Room, Long> {


}
