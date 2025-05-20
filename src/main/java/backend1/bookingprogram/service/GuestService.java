package backend1.bookingprogram.service;

import backend1.bookingprogram.dtos.BookingDTO;
import backend1.bookingprogram.dtos.GuestDTO;
import backend1.bookingprogram.repositories.GuestRepository;
import org.springframework.stereotype.Service;

import java.util.List;

import static backend1.bookingprogram.mappers.GuestMapper.guestToGuestDTODetailed;

@Service
public class GuestService {
    private final GuestRepository repo;

    public GuestService(GuestRepository repo) {
        this.repo = repo;
    }

    public List<GuestDTO> fetchAllGuests() {
        return repo.findAll()
                .stream()
                .map(g -> guestToGuestDTODetailed(g))
                .toList();
    }

}
