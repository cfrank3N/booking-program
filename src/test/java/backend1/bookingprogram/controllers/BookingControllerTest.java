package backend1.bookingprogram.controllers;

import backend1.bookingprogram.config.TestContainersConfig;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;
import org.springframework.test.web.servlet.MockMvc;
import org.testcontainers.junit.jupiter.Testcontainers;

import static backend1.bookingprogram.enums.RoutingInfo.ALTER_BOOKING;
import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
@Testcontainers
@Import(TestContainersConfig.class)
public class BookingControllerTest {
    @Autowired
    private MockMvc mvc;

    @Autowired
    private BookingController controller;

    @Test
    void controllerLoads(){
        assertThat(controller).isNotNull();
    }

    @Test
    void fetchAllBookings() throws Exception {
        mvc.perform(get("/booking/alter"))
                .andExpect(status().isOk())
                .andExpect(view().name("choose-booking-to-alter"))
                .andExpect(model().attributeExists("bookings"));
    }

    @Test
    void showBookingForm() throws Exception {
        mvc.perform(get("/booking/alter/" + 1))
                .andExpect(status().isOk())
                .andExpect(view().name(ALTER_BOOKING.getViewName()))
                .andExpect(model().attributeExists("booking"));
    }

}
