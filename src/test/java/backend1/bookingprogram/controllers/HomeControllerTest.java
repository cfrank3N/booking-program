package backend1.bookingprogram.controllers;

import backend1.bookingprogram.config.TestContainersConfig;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;
import org.springframework.test.web.servlet.MockMvc;
import org.testcontainers.junit.jupiter.Testcontainers;

import static backend1.bookingprogram.enums.RoutingInfo.HOMEPAGE;
import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
@Testcontainers
@Import(TestContainersConfig.class)
class HomeControllerTest {

    @Autowired
    private MockMvc mvc;

    @Autowired
    private HomeController controller;

    @Test
    void controllerLoads() {
        assertNotNull(controller);
    }

    @Test
    void fetchHomePage() throws Exception {
        mvc.perform(get("/home"))
                .andExpect(status().isOk())
                .andExpect(view().name(HOMEPAGE.getViewName()));
    }
}