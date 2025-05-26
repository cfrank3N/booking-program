package backend1.bookingprogram.service;

import backend1.bookingprogram.dtos.GuestDTO;
import backend1.bookingprogram.exceptions.ResourceAlreadyExistsException;
import backend1.bookingprogram.exceptions.ResourceDoesntExistException;
import backend1.bookingprogram.mappers.GuestMapper;
import backend1.bookingprogram.models.Guest;
import backend1.bookingprogram.repositories.GuestRepository;
import jakarta.transaction.Transactional;
import org.aspectj.lang.annotation.Before;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.ActiveProfiles;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@ActiveProfiles("test")
class GuestServiceTest {

    @Autowired
    private GuestService service;

    @Autowired
    private GuestRepository repo;

    private final GuestDTO g1 = GuestDTO.builder()
            .name("Ove Frank")
            .email("ove@email.com")
            .phonenumber("08 345 34 34")
            .bookings(new ArrayList<>())
            .build();

    private final GuestDTO g2 = GuestDTO.builder()
            .name("Ove C Frank")
            .email("ove@email.com")
            .phonenumber("08 345 34 34")
            .bookings(new ArrayList<>())
            .build();

    private final GuestDTO g3 = GuestDTO.builder()
            .name("Klas Frank")
            .email("ovo@email.com")
            .phonenumber("08 345 34 34")
            .bookings(new ArrayList<>())
            .build();


    @AfterEach
    public void exitTest() {
        repo.findByEmail("ove@email.com").ifPresent(g -> repo.deleteById(g.getId()));
        repo.findByEmail("ovo@email.com").ifPresent(g -> repo.deleteById(g.getId()));
    }

    @Test
    void createGuest() {

        assertEquals(ResponseEntity.status(HttpStatus.CREATED).body(g1.getName() + " registered!"), service.createGuest(g1));
        assertThrows(ResourceAlreadyExistsException.class, () -> service.createGuest(g2));

    }

    @Test
    @Transactional
    void fetchAllGuests() {

        assertTrue(service.fetchAllGuests().size() == 3);
        assertFalse(service.fetchAllGuests().size() == 5);

    }

    @Test
    @Transactional
    void deleteGuest() {

        Guest guest = GuestMapper.guestDTOToGuestDetailed(g3);
        guest.setBookings(new ArrayList<>());

        repo.save(guest);

        repo.findByEmail(g3.getEmail()).ifPresent(g -> assertDoesNotThrow(() -> service.deleteGuest(g.getId())));

        assertThrows(ResourceDoesntExistException.class, () -> service.deleteGuest(999L));

    }

    @Test
    @Transactional
    void guestHasActiveBookings() {

        assertTrue(service.guestHasActiveBookings(1L));
        assertFalse(service.guestHasActiveBookings(2L));

    }

    @Test
    @Transactional
    void showActiveBookings() {

        assertEquals(1, service.showActiveBookings(1L).size());
        assertNotEquals(3, service.showActiveBookings(1L).size());

    }

    @Test
    @Transactional
    void alterGuest() {

        Guest guestBefore = GuestMapper.guestDTOToGuestDetailed(g1);
        guestBefore.setBookings(new ArrayList<>());

        GuestDTO guestAfter = g3;

        repo.save(guestBefore);

        assertThrows(ResourceDoesntExistException.class, () -> service.alterGuest(25L, g3));


    }

    @Test
    @Transactional
    void fetchGuestById() {

        assert(service.fetchGuestById(1L) != null);
        assertThrows(ResourceDoesntExistException.class, () -> service.fetchGuestById(999L));

    }
}