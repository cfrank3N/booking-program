package backend1.bookingprogram.service;

import backend1.bookingprogram.config.TestContainersConfig;
import backend1.bookingprogram.dtos.ActiveBookingDTO;
import backend1.bookingprogram.dtos.BookingDTO;
import backend1.bookingprogram.exceptions.ResourceAlreadyExistsException;
import backend1.bookingprogram.exceptions.ResourceDoesntExistException;
import backend1.bookingprogram.models.Booking;
import backend1.bookingprogram.models.Guest;
import backend1.bookingprogram.models.Room;
import backend1.bookingprogram.repositories.BookingRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;
import org.testcontainers.junit.jupiter.Testcontainers;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Testcontainers
@Import(TestContainersConfig.class)
class BookingServiceTest {

    private final LocalDate start = LocalDate.now().plusDays(7);
    private final LocalDate end = LocalDate.now().plusDays(10);
    private final ActiveBookingDTO newBooking = ActiveBookingDTO.builder()
            .bookingId(2L)
            .gId(1L)
            .rId(1L)
            .dateFrom(start)
            .dateUntil(end)
            .numberOfGuests(1)
            .build();

    @Autowired
    private BookingService service;

    @Autowired
    private BookingRepository repo;

    @Test
    void fetchAllBookings() {
        int result = service.fetchAllBookings().size();

        assertEquals(1, result);
        assertNotEquals(2, result);
    }

    @Test
    void deleteBooking() {

        BookingDTO savedBooking = service.createBooking(newBooking);
        assertEquals(2, service.fetchAllBookings().size());

        service.deleteBooking(savedBooking.getBookingId());

        assertEquals(1, service.fetchAllBookings().size());
        assertThrows(ResourceDoesntExistException.class, () -> service.deleteBooking(9999L));

    }

    @Test
    void createBooking() {
        assertEquals(BookingDTO.class, service.createBooking(newBooking).getClass());
        assertThrows(ResourceAlreadyExistsException.class, () -> service.createBooking(newBooking));
        service.deleteBooking(newBooking.getBookingId());
    }

    @Test
    void hasOverlappingDates() {

        Booking correctBooking = Booking.builder()
                .id(1L)
                .dateFrom(LocalDate.now().plusDays(2))
                .dateUntil(LocalDate.now().plusDays(10))
                .guest(Guest.builder()
                        .id(1L)
                        .name("Andreas")
                        .email("Andreas@Hotmale.com")
                        .phonenumber("+46763060692")
                        .build())
                .numberOfGuests(1)
                .room(Room.builder()
                        .id(1L)
                        .roomNumber("101")
                        .roomName("single room")
                        .roomSize(12)
                        .build())
                .build();

        Booking incorrectBooking = Booking.builder()
                .id(2L)
                .dateFrom(LocalDate.now().plusDays(2))
                .dateUntil(LocalDate.now().plusDays(10))
                .guest(Guest.builder()
                        .id(1L)
                        .name("Andreas")
                        .email("Andreas@Hotmale.com")
                        .phonenumber("+46763060692")
                        .build())
                .numberOfGuests(1)
                .room(Room.builder()
                        .id(1L)
                        .roomNumber("101")
                        .roomName("single room")
                        .roomSize(12)
                        .build())
                .build();

        assertFalse(service.hasOverlappingDates(correctBooking));
        assertTrue(service.hasOverlappingDates(incorrectBooking));
    }

    @Test
    void fetchBookingById() {
        assertEquals(BookingDTO.class, service.fetchBookingById(1L).getClass());
        assertThrows(ResourceDoesntExistException.class, () -> service.fetchBookingById(9999L));
    }
}