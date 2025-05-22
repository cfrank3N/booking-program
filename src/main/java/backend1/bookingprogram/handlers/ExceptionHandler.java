package backend1.bookingprogram.handlers;

import backend1.bookingprogram.dtos.GuestDTO;
import backend1.bookingprogram.dtos.RoomSearchDTO;
import backend1.bookingprogram.exceptions.CantDeleteException;
import backend1.bookingprogram.exceptions.FaultyDateException;
import backend1.bookingprogram.exceptions.ResourceAlreadyExistsException;
import backend1.bookingprogram.exceptions.ResourceDoesntExistException;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.servlet.ModelAndView;

import static backend1.bookingprogram.enums.RoutingInfo.*;

@ControllerAdvice
public class ExceptionHandler {

    @org.springframework.web.bind.annotation.ExceptionHandler(ResourceAlreadyExistsException.class)
    public ModelAndView handleResourceAlreadyExists(HttpServletRequest req , ResourceAlreadyExistsException e) {
        ModelAndView mav = new ModelAndView();
        String uri = req.getRequestURI();
        mav.addObject("error", e.getMessage());

        if (uri.equalsIgnoreCase(REGISTER_GUEST.getUri())) {
            mav.addObject("guest", new GuestDTO());
            mav.setViewName(REGISTER_GUEST.getViewName());
        } else {
            mav.setViewName(HOMEPAGE.getViewName());
        }

        return mav;
    }

    @org.springframework.web.bind.annotation.ExceptionHandler(MethodArgumentNotValidException.class)
    public ModelAndView handleEmptyResource(HttpServletRequest req, MethodArgumentNotValidException e) {
        ModelAndView mav = new ModelAndView();
        String uri = req.getRequestURI();
        mav.addObject("error", e.getBody());
        mav.addObject("guest", new GuestDTO());

        if (uri.equalsIgnoreCase(REGISTER_GUEST.getUri())) {
            mav.setViewName(REGISTER_GUEST.getViewName());
        } else {
            mav.setViewName(HOMEPAGE.getViewName());
        }
        return mav;
    }

    @org.springframework.web.bind.annotation.ExceptionHandler(FaultyDateException.class)
    public ModelAndView handleFaultyDate(HttpServletRequest req, FaultyDateException e) {
        ModelAndView mav = new ModelAndView();
        String uri = req.getRequestURI();
        mav.addObject("error", e.getMessage());

        if (uri.equalsIgnoreCase(REGISTER_GUEST.getUri())) {
            mav.setViewName(REGISTER_GUEST.getViewName());
        } else if (uri.equalsIgnoreCase(SELECT_ROOM.getUri())) {
            mav.addObject("availableRooms", new RoomSearchDTO());
            mav.setViewName(HOMEPAGE.getViewName());
        } else {
            mav.setViewName(HOMEPAGE.getViewName());
        }
        return mav;
    }

    @org.springframework.web.bind.annotation.ExceptionHandler(ResourceDoesntExistException.class)
    public ResponseEntity<String> handleResourceDoesntExist(ResourceDoesntExistException e) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
    }

    @org.springframework.web.bind.annotation.ExceptionHandler(CantDeleteException.class)
    public ResponseEntity<String> handleCantDelete(CantDeleteException e) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
    }
}
