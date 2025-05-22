package backend1.bookingprogram.controllers;

import backend1.bookingprogram.dtos.BookingDTO;
import backend1.bookingprogram.dtos.RoomDTO;
import backend1.bookingprogram.service.RoomService;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static backend1.bookingprogram.enums.RoutingInfo.SELECT_ROOM;

@Controller
public class RoomController {

    private RoomService roomService;
    private static final org.slf4j.Logger log = LoggerFactory.getLogger(RoomController.class);

    public RoomController(RoomService roomService) {
        this.roomService = roomService;
    }

    @PostMapping("/rooms")
    public String fetchAllAvailableRooms(@ModelAttribute BookingDTO booking,
                                Model model) {
        List<RoomDTO> rooms = roomService.fetchAllAvailableRooms(booking.getDateFrom(),
                booking.getDateUntil(), booking.getNumberOfGuests());
        model.addAttribute("rooms", rooms);
        model.addAttribute("booking", booking);
        return SELECT_ROOM.getViewName();
    }
}