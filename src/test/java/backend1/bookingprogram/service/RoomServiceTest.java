package backend1.bookingprogram.service;

import backend1.bookingprogram.dtos.RoomDTO;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@Transactional
@SpringBootTest
@ActiveProfiles("test")
class RoomServiceTest {

    @Autowired
    private RoomService roomService;

    private final LocalDate start = LocalDate.now().plusDays(2);
    private final LocalDate start2 = LocalDate.now().plusDays(10);
    private final LocalDate end = LocalDate.now().plusDays(7);
    private final LocalDate end2 = LocalDate.now().plusDays(20);

    @Test
    void getMinRoomSize() {
        //TODO
        int result = RoomService.getMinRoomSize(3);
        int result2 = RoomService.getMinRoomSize(1);

        assertEquals(30, result);
        assertNotEquals(40, result2);
    }

    @Test
    void fetchAllAvailableRooms() {

        List<RoomDTO> result = roomService.fetchAllAvailableRooms(start, end, 1);
        List<RoomDTO> result2 = roomService.fetchAllAvailableRooms(start2, end2, 1);
        List<RoomDTO> result3 = roomService.fetchAllAvailableRooms(start2, end2, 3);

        System.out.println(result);
        System.out.println(result.size());

        System.out.println(result2);
        System.out.println(result2.size());

        System.out.println(result3);
        System.out.println(result3.size());

        assertEquals(2, result.size());
        assertEquals(3, result2.size());
        assertEquals(1, result3.size());

    }
}