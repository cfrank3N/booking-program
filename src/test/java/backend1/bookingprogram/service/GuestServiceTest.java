package backend1.bookingprogram.service;

import backend1.bookingprogram.dtos.GuestDTO;
import backend1.bookingprogram.exceptions.ResourceAlreadyExistsException;
import backend1.bookingprogram.repositories.GuestRepository;
import org.junit.jupiter.api.AfterEach;
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

    @AfterEach
    public void exitTest() {

        repo.findByEmail("ove@email.com").ifPresent(g -> repo.deleteById(g.getId()));

    }

    @Test
    void createGuest() {

        assertEquals(ResponseEntity.status(HttpStatus.CREATED).body(g1.getName() + " registered!"), service.createGuest(g1));
        assertThrows(ResourceAlreadyExistsException.class, () -> service.createGuest(g2));

    }
}