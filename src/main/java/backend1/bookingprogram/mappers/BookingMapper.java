package backend1.bookingprogram.mappers;

import backend1.bookingprogram.dtos.BookingDTO;
import backend1.bookingprogram.models.Booking;

public class BookingMapper {

    public static BookingDTO bookingToBookingDTODetailed(Booking b) {
        return BookingDTO.builder()
                .id(b.getId())
                .dateFrom(b.getDateFrom())
                .dateUntil(b.getDateUntil())
                .numberOfGuests(b.getNumberOfGuests())
                .guest(GuestMapper.guestToGuestDTOMinimal(b.getGuest()))
                .room(RoomMapper.roomToRoomDTOMinimal(b.getRoom()))
                .build();
    }
}
