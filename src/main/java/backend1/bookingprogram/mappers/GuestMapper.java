package backend1.bookingprogram.mappers;

import backend1.bookingprogram.dtos.GuestDTO;
import backend1.bookingprogram.models.Guest;

import static backend1.bookingprogram.mappers.BookingMapper.bookingToBookingDTOMinimal;

public class GuestMapper {

    public static GuestDTO guestToGuestDTODetailed(Guest g) {
        return GuestDTO.builder()
                .id(g.getId())
                .name(g.getName())
                .email(g.getEmail())
                .phonenumber(g.getPhonenumber())
                .bookings(g.getBookings().stream().map(booking -> bookingToBookingDTOMinimal(booking)).toList())
                .build();
    }

    public static GuestDTO guestToGuestDTOMinimal(Guest g) {
        return GuestDTO.builder()
                .id(g.getId())
                .name(g.getName())
                .build();
    }

    public static Guest guestDTOToGuestDetailed(GuestDTO g) {
        return Guest.builder()
                .name(g.getName())
                .email(g.getEmail())
                .phonenumber(g.getPhonenumber())
                .build();
    }

}
