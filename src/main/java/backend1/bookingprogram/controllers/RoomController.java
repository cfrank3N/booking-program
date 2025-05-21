package backend1.bookingprogram.controllers;

import backend1.bookingprogram.dtos.RoomDTO;
import backend1.bookingprogram.repositories.RoomRepository;
import backend1.bookingprogram.service.RoomService;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

import static backend1.bookingprogram.mappers.RoomMapper.roomToRoomDTODetailed;

@Controller
public class RoomController {

    private RoomService roomService;
    private static final org.slf4j.Logger log = LoggerFactory.getLogger(RoomController.class);

    public RoomController(RoomService roomService) {
        this.roomService = roomService;
    }

    @GetMapping("/rooms")
    public String fetchAllRooms(Model model) {
        //List<RoomDTO> rooms = roomService.fetchAllRooms();
        //model.addAttribute("rooms", rooms);
        return "available-rooms";
    }
}