package backend1.bookingprogram.mappers;

import backend1.bookingprogram.dtos.GuestDTO;
import backend1.bookingprogram.models.Guest;

public class GuestMapper {

    public static GuestDTO guestToGuestDTOMinimal(Guest g) {
        return GuestDTO.builder()
                .id(g.getId())
                .name(g.getName())
                .build();
    }

}
