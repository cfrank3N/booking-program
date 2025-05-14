package backend1.bookingprogram.service;

import backend1.bookingprogram.repositories.BookingRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDate;

@Service
public class BookingService {
    BookingRepository bookingRepository;

    public boolean guestHasActiveBookings(Long guestID){
        return bookingRepository.findAllByGuestId(guestID)
                .stream().anyMatch(booking -> LocalDate.now().isBefore(booking.getDateUntil()));
    }
}
