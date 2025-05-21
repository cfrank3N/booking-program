package backend1.bookingprogram.controllers;

import backend1.bookingprogram.dtos.RoomDTO;
import backend1.bookingprogram.dtos.RoomSearchDTO;
import backend1.bookingprogram.service.RoomService;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
public class RoomController {

    private RoomService roomService;
    private static final org.slf4j.Logger log = LoggerFactory.getLogger(RoomController.class);

    public RoomController(RoomService roomService) {
        this.roomService = roomService;
    }

    @PostMapping("/rooms")
    public String fetchAllAvailableRooms(@ModelAttribute RoomSearchDTO r,
                                Model model) {
        List<RoomDTO> rooms = roomService.fetchAllAvailableRooms(r.getStartDate(), r.getEndDate());
        model.addAttribute("rooms", rooms);
        model.addAttribute("startDate", r.getStartDate());
        model.addAttribute("endDate", r.getEndDate());
        model.addAttribute("guests", r.getGuests());
        return "available-rooms";
    }
}