package backend1.bookingprogram.controllers;

import backend1.bookingprogram.dtos.BookingDTO;
import backend1.bookingprogram.dtos.RoomDTO;
import backend1.bookingprogram.service.RoomService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import static backend1.bookingprogram.enums.RoutingInfo.SELECT_ROOM;
import static org.junit.jupiter.api.Assertions.*;

import static org.hamcrest.Matchers.containsInAnyOrder;
import static org.hamcrest.Matchers.containsString;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@ActiveProfiles("test")
@AutoConfigureMockMvc
class RoomControllerTest {

    @Autowired
    private MockMvc mvc;

    @Autowired
    private RoomService roomService;

    @Test
    void fetchAllAvailableRooms() throws Exception {

        LocalDate startDate = LocalDate.now();
        LocalDate endDate = LocalDate.now().plusDays(2);
        int numberOfGuests = 2;

        mvc.perform(post("/rooms")
                .param("dateFrom", startDate.toString())
                .param("dateUntil", endDate.toString())
                .param("numberOfGuests", String.valueOf(numberOfGuests)))
                .andExpect(status().isOk())
                .andExpect(view().name(SELECT_ROOM.getViewName()))
                .andExpect(model().attributeExists("rooms"))
                .andExpect(model().attributeExists("booking"));
    }

    @Test
    void fetchAvailableRoomsForAlterBooking() throws Exception {
        //TODO
        LocalDate startDate = LocalDate.now();
        LocalDate endDate = LocalDate.now().plusDays(2);
        int numberOfGuests = 2;

        //Make sure you have an initialized mockdb with data and that it has a booking with id 1.
        //If it doesn't you can just look up the id and exchange it below in the uri in the post() method.
        mvc.perform(post("/booking/alter/1/rooms")
                        .param("dateFrom", startDate.toString())
                        .param("dateUntil", endDate.toString())
                        .param("numberOfGuests", String.valueOf(numberOfGuests)))
                .andExpect(status().isOk())
                .andExpect(view().name("alter-booking"))
                .andExpect(model().attributeExists("rooms"))
                .andExpect(model().attributeExists("booking"));
    }
}