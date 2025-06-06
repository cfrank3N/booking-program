package backend1.bookingprogram.controllers;

import backend1.bookingprogram.dtos.ActiveBookingDTO;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import static backend1.bookingprogram.enums.RoutingInfo.HOMEPAGE;


@Controller
public class HomeController {

    @GetMapping("/home")
    public String fetchHomePage(Model model) {
        if (!model.containsAttribute("booking")) {
            model.addAttribute("booking", new ActiveBookingDTO());
        }
        return HOMEPAGE.getViewName();
    }

}
