package backend1.bookingprogram;

import backend1.bookingprogram.models.Room;
import backend1.bookingprogram.repositories.RoomRepository;
import jakarta.annotation.PostConstruct;
import org.springframework.stereotype.Component;

import java.util.List;


@Component
public class DatabaseSeeder {

    private final RoomRepository roomRepository;

    public DatabaseSeeder(RoomRepository roomRepository) {
        this.roomRepository = roomRepository;
    }


    // example
    @PostConstruct
    public void seed() {
        if (roomRepository.count() == 0) {
            List<Room> rooms = List.of(
                    new Room(null, "101", "single room", 12, null));
                    roomRepository.saveAll(rooms);

        }
    }
}
