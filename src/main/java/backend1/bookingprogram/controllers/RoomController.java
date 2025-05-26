package backend1.bookingprogram.controllers;

import backend1.bookingprogram.dtos.ActiveBookingDTO;
import backend1.bookingprogram.dtos.BookingDTO;
import backend1.bookingprogram.dtos.RoomDTO;
import backend1.bookingprogram.service.RoomService;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static backend1.bookingprogram.enums.RoutingInfo.SELECT_ROOM;
import static backend1.bookingprogram.enums.RoutingInfo.SHOW_ROOMS;

@Controller
public class RoomController {

    private final RoomService roomService;
    private static final org.slf4j.Logger log = LoggerFactory.getLogger(RoomController.class);

    public RoomController(RoomService roomService) {
        this.roomService = roomService;
    }

    @PostMapping("/rooms")
    public String fetchAllAvailableRooms(@ModelAttribute BookingDTO booking,
                                Model model) {
        List<RoomDTO> rooms = roomService.fetchAllAvailableRooms(booking.getDateFrom(),
                booking.getDateUntil(), booking.getNumberOfGuests());

        log.info("Booking status [1/3]: date's selected: {}", booking);

        model.addAttribute("rooms", rooms);
        model.addAttribute("booking", booking);
        return SELECT_ROOM.getViewName();
    }

    @GetMapping("/rooms/all")
    public String fetchAllRooms(Model model){
        model.addAttribute("rooms", roomService.fetchAllRooms());
        return SHOW_ROOMS.getViewName();
    }

    @PostMapping("/booking/alter/{id}/rooms")
    public String fetchAvailableRoomsForAlterBooking(@PathVariable Long id, @ModelAttribute ActiveBookingDTO booking,
                                Model model) {

        log.info("Altering booking: {}", booking);

        booking.setBookingId(id);
        List<RoomDTO> rooms = roomService.fetchAllAvailableRooms(booking.getDateFrom(),
                booking.getDateUntil(), booking.getNumberOfGuests());
        model.addAttribute("rooms", rooms);
        model.addAttribute("booking", booking);
        return "alter-booking";
    }
}