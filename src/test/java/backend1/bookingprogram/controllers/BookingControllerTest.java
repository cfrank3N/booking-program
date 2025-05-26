package backend1.bookingprogram.controllers;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@ActiveProfiles("test")
@AutoConfigureMockMvc
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

}
