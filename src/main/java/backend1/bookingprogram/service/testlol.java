package backend1.bookingprogram.service;

import backend1.bookingprogram.dtos.GuestDTO;
import backend1.bookingprogram.mappers.GuestMapper;
import backend1.bookingprogram.models.Guest;
import backend1.bookingprogram.repositories.GuestRepository;
import backend1.bookingprogram.exceptions.ResourceDoesntExistException;
import org.springframework.stereotype.Service;

@Service
public class GuestService {

    private final GuestRepository guestRepository;

    public GuestService(GuestRepository guestRepository) {
        this.guestRepository = guestRepository;
    }

    public GuestDTO findGuestById(Long id) {
        Guest guest = guestRepository.findById(id)
                .orElseThrow(() -> new ResourceDoesntExistException("Guest not found with ID: " + id));
        return GuestMapper.guestToGuestDTO(guest);
    }

    public GuestDTO createGuest(GuestDTO guestDTO) {
        Guest guest = GuestMapper.guestDTOToGuest(guestDTO);
        guest = guestRepository.save(guest);
        return GuestMapper.guestToGuestDTO(guest);
    }

    public void deleteGuest(Long id) {
        if (!guestRepository.existsById(id)) {
            throw new ResourceDoesntExistException("Guest not found with ID: " + id);
        }
        guestRepository.deleteById(id);
    }
}
