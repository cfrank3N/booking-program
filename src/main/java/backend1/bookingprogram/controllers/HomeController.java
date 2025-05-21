package backend1.bookingprogram.controllers;

import backend1.bookingprogram.dtos.RoomSearchDTO;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class HomeController {

    @GetMapping("/home")
    public String fetchHomePage(Model model) {
        model.addAttribute("availableRooms", new RoomSearchDTO());
        return "index";
    }

}
