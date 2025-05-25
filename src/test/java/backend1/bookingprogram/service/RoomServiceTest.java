package backend1.bookingprogram.service;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("test")
class RoomServiceTest {

    @BeforeEach
    public void setup() {
        //TODO
    }

    @AfterEach
    public void afterTest() {
        //TODO
    }


    @Test
    void getMinRoomSize() {
        //TODO
    }

    @Test
    void fetchAllAvailableRooms() {
        //TODO
    }
}