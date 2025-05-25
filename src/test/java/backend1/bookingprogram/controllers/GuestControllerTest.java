package backend1.bookingprogram.controllers;

import backend1.bookingprogram.models.Guest;
import backend1.bookingprogram.repositories.GuestRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;

import static backend1.bookingprogram.enums.RoutingInfo.REGISTER_GUEST;

import java.time.LocalDate;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@ActiveProfiles("test")
@AutoConfigureMockMvc
class GuestControllerTest {

    @Autowired
    private MockMvc mvc;

    @Autowired
    private GuestRepository repo;

    @Test
    void registerGuest() throws Exception {

        mvc.perform(post("/guest/register")
                .param("name", "Caroline")
                .param("email", "test@email.com")
                .param("phonenumber", "073 074 74 74"))
                .andExpect(status().isOk())
                .andExpect(view().name(REGISTER_GUEST.getViewName()))
                .andExpect(model().attributeExists("guest"))
                .andExpect(model().attributeExists("success"));

        Guest g = repo.findByEmail("test@email.com").orElse(new Guest());
        repo.deleteById(g.getId());

        //Test to see if an error is thrown
        mvc.perform(post("/guest/register")
                        .param("name", "Caroline")
                        .param("email", "Andreas@Hotmale.com")
                        .param("phonenumber", "073 074 74 74"))
                .andExpect(status().isOk())
                .andExpect(view().name(REGISTER_GUEST.getViewName()))
                .andExpect(model().attributeExists("guest"))
                .andExpect(model().attributeExists("error"));
    }

    @Test
    void fetchAllGuests() throws Exception {

        mvc.perform(post("/rooms/select/guest")
                    .param("rId", "1")
                    .param("dateFrom", LocalDate.now().plusDays(20).toString())
                    .param("dateUntil", LocalDate.now().plusDays(30).toString())
                    .param("numberOfGuests", "1"))
                .andExpect(status().isOk())
                .andExpect(view().name("select-guest"))
                .andExpect(model().attributeExists("booking"))
                .andExpect(model().attributeExists("guests"));

    }
}