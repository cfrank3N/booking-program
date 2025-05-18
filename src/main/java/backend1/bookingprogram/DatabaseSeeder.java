package backend1.bookingprogram;

import backend1.bookingprogram.models.Booking;
import backend1.bookingprogram.models.Guest;
import backend1.bookingprogram.models.Room;
import backend1.bookingprogram.repositories.BookingRepository;
import backend1.bookingprogram.repositories.GuestRepository;
import backend1.bookingprogram.repositories.RoomRepository;
import jakarta.annotation.PostConstruct;
import org.springframework.stereotype.Component;

import java.util.List;


@Component
public class DatabaseSeeder {

    private final RoomRepository roomRepository;
    private final GuestRepository guestRepository;
    private final BookingRepository bookingRepository;

    public DatabaseSeeder(RoomRepository roomRepository, GuestRepository guestRepository, BookingRepository bookingRepository) {
        this.roomRepository = roomRepository;
        this.guestRepository = guestRepository;
        this.bookingRepository = bookingRepository;
    }

    // seeds db
    @PostConstruct
    public void seed() {
        if (roomRepository.count() == 0) {
            List<Room> rooms = List.of(
                    new Room(null, "101", "single room", 12, null));
            roomRepository.saveAll(rooms);
        }

        if (guestRepository.count() == 0) {
            List<Guest> guests = List.of(
                    new Guest(null, "Andreas", "Andreas@Hotmale.com", "+46763060692", null));
            guestRepository.saveAll(guests);
        }

        if (bookingRepository.count() == 0) {
            List<Booking> bookings = List.of(
                    new Booking());
            bookingRepository.saveAll(bookings);
            // osv.
        }
    }


}
