package backend1.bookingprogram.handlers;

import backend1.bookingprogram.dtos.ActiveBookingDTO;
import backend1.bookingprogram.dtos.GuestDTO;
import backend1.bookingprogram.exceptions.*;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.web.servlet.view.RedirectView;

import static backend1.bookingprogram.enums.RoutingInfo.*;

@ControllerAdvice
public class ExceptionHandler {

    @org.springframework.web.bind.annotation.ExceptionHandler(ResourceAlreadyExistsException.class)
    public ModelAndView handleResourceAlreadyExists(HttpServletRequest req , ResourceAlreadyExistsException e) {
        ModelAndView mav = new ModelAndView();
        String uri = req.getRequestURI();
        mav.addObject("error", e.getMessage());

        if (uri.contains(REGISTER_GUEST.getUri())) {
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

        if (uri.contains(REGISTER_GUEST.getUri())) {
            mav.setViewName(REGISTER_GUEST.getViewName());
        } else {
            mav.setViewName(HOMEPAGE.getViewName());
        }
        return mav;
    }

    @org.springframework.web.bind.annotation.ExceptionHandler(EmailValidationException.class)
    public ModelAndView handleEmailValidation(EmailValidationException e) {

        ModelAndView mav = new ModelAndView();

        mav.addObject("error", e.getMessage());
        mav.addObject("guest", e.getGuest());

        mav.setViewName(ALTER_GUEST.getViewName());

        return mav;

    }

    @org.springframework.web.bind.annotation.ExceptionHandler(EmptyResourceException.class)
    public ModelAndView handleEmailValidation(EmptyResourceException e) {

        ModelAndView mav = new ModelAndView();

        mav.addObject("errorTwo", e.getMessage());
        mav.addObject("guest", e.getGuest());

        mav.setViewName(ALTER_GUEST.getViewName());

        return mav;

    }

    @org.springframework.web.bind.annotation.ExceptionHandler(FaultyDateException.class)
    public ModelAndView handleFaultyDate(RedirectAttributes redirectAttributes, FaultyDateException e) {
        redirectAttributes.addFlashAttribute("error", e.getMessage());
        redirectAttributes.addFlashAttribute("booking", new ActiveBookingDTO());
        RedirectView redirectView = new RedirectView(HOMEPAGE.getUri(), true); // `true` for context-relative
        return new ModelAndView(redirectView);
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
