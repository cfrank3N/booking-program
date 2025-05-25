package backend1.bookingprogram;

import backend1.bookingprogram.models.Booking;
import backend1.bookingprogram.models.Guest;
import backend1.bookingprogram.models.Room;
import backend1.bookingprogram.repositories.BookingRepository;
import backend1.bookingprogram.repositories.GuestRepository;
import backend1.bookingprogram.repositories.RoomRepository;
import jakarta.annotation.PostConstruct;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
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
        if (guestRepository.count() == 0 && roomRepository.count() == 0 && bookingRepository.count() == 0) {
            Room room1 = new Room("101", "single room", 12, null);
            Room room2 = new Room("202", "double room", 24, null);
            Room room3 = new Room("909", "suite", 90, null);
            roomRepository.saveAll(List.of(room1, room2, room3));

            Guest guest1 = new Guest("Andreas", "Andreas@Hotmale.com", "+46763060692", null);
            Guest guest2 = new Guest("Arvid", "Arvid@Gmail.com", "+46763060693", null);
            Guest guest3 = new Guest("Adam", "Adam@Gmail.com", "+46763060694", null);
            guestRepository.saveAll(List.of(guest1, guest2, guest3));


            Booking booking1 = new Booking(LocalDate.now(), LocalDate.now().plusDays(5), 2, guest1, room1);
            bookingRepository.saveAll(List.of(booking1));
            // osv.
        }
    }


}
