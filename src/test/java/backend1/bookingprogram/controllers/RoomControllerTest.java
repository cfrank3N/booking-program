package backend1.bookingprogram.controllers;

import backend1.bookingprogram.config.TestContainersConfig;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;
import org.springframework.test.web.servlet.MockMvc;
import org.testcontainers.junit.jupiter.Testcontainers;

import java.time.LocalDate;

import static backend1.bookingprogram.enums.RoutingInfo.SELECT_ROOM;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasSize;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@Testcontainers
@Import(TestContainersConfig.class)
@AutoConfigureMockMvc
class RoomControllerTest {

    @Autowired
    private MockMvc mvc;

    @Autowired
    private RoomController controller;

    @Test
    void controllerLoads(){
        assertThat(controller).isNotNull();
    }

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
                .andExpect(model().attributeExists("booking"))
                .andExpect(model().attribute("rooms", hasSize(2)));
    }

    @Test
    void fetchAvailableRoomsForAlterBooking() throws Exception {
        //TODO
        LocalDate startDate = LocalDate.now();
        LocalDate endDate = LocalDate.now().plusDays(2);
        int numberOfGuests = 2;

        //Make sure you have an initialized mock db with data and that it has a booking with id 1.
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