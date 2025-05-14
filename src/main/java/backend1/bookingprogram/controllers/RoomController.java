package backend1.bookingprogram.controllers;

import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class RoomController {

    private final RoomRepository roomRepository;
    private static final org.slf4j.Logger log = LoggerFactory.getLogger(RoomController.class);

    public RoomController(RoomRepository roomRepository) {
        this.roomRepository = roomRepository;
    }

}