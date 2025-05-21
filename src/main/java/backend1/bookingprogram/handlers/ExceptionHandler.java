package backend1.bookingprogram.handlers;

import backend1.bookingprogram.dtos.GuestDTO;
import backend1.bookingprogram.exceptions.CantDeleteException;
import backend1.bookingprogram.exceptions.ResourceAlreadyExistsException;
import backend1.bookingprogram.exceptions.ResourceDoesntExistException;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.servlet.ModelAndView;

@ControllerAdvice
public class ExceptionHandler {

    @org.springframework.web.bind.annotation.ExceptionHandler(ResourceAlreadyExistsException.class)
    public ModelAndView handleResourceAlreadyExists(HttpServletRequest req , ResourceAlreadyExistsException e) {
        ModelAndView mav = new ModelAndView();
        mav.addObject("error", e.getMessage());
        mav.addObject("url", "A request to " + req.getRequestURL() + " caused an error");
        mav.addObject("guest", new GuestDTO());
        mav.setViewName("register-guest");
        return mav;
    }

    @org.springframework.web.bind.annotation.ExceptionHandler(MethodArgumentNotValidException.class)
    public ModelAndView handleEmptyResource(HttpServletRequest req, MethodArgumentNotValidException e) {
        ModelAndView mav = new ModelAndView();
        String uri = req.getRequestURI();
        mav.addObject("error", e.getBody());
        mav.addObject("url","A request to " + uri + " caused an error");
        mav.addObject("guest", new GuestDTO());

        if (uri.equalsIgnoreCase("/guest/register")) {
            mav.setViewName("register-guest");
        } else {
            mav.setViewName("index");
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
