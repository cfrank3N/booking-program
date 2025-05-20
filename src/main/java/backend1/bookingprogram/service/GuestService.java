package backend1.bookingprogram.service;

import backend1.bookingprogram.repositories.GuestRepository;
import org.springframework.stereotype.Service;

@Service
public class GuestService {
    private final GuestRepository repo;

    public GuestService(GuestRepository repo) {
        this.repo = repo;
    }
}
