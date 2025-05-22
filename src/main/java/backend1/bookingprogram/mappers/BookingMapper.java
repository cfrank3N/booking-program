package backend1.bookingprogram.mappers;

import backend1.bookingprogram.dtos.ActiveBookingDTO;
import backend1.bookingprogram.dtos.BookingDTO;
import backend1.bookingprogram.models.Booking;

import static backend1.bookingprogram.mappers.GuestMapper.guestDTOToGuestMinimal;
import static backend1.bookingprogram.mappers.RoomMapper.roomDTOToRoomMinimal;

public class BookingMapper {

    public static BookingDTO bookingToBookingDTODetailed(Booking b) {
        return BookingDTO.builder()
                .bookingId(b.getId())
                .dateFrom(b.getDateFrom())
                .dateUntil(b.getDateUntil())
                .numberOfGuests(b.getNumberOfGuests())
                .guest(GuestMapper.guestToGuestDTOMinimal(b.getGuest()))
                .room(RoomMapper.roomToRoomDTOMinimal(b.getRoom()))
                .build();
    }

    public static BookingDTO bookingToBookingDTOMinimal(Booking b) {
        return BookingDTO.builder()
                .bookingId(b.getId())
                .build();
    }

    public static Booking bookingDTOToBookingDetailed(BookingDTO b) {
        return Booking.builder()
                .dateFrom(b.getDateFrom())
                .dateUntil(b.getDateUntil())
                .numberOfGuests(b.getNumberOfGuests())
                .guest(guestDTOToGuestMinimal(b.getGuest()))
                .room(roomDTOToRoomMinimal(b.getRoom()))
                .build();
    }

    public static BookingDTO activeBookingDTOToBookingDetailed(ActiveBookingDTO b) {
        return BookingDTO.builder()
                .dateFrom(b.getDateFrom())
                .dateUntil(b.getDateUntil())
                .numberOfGuests(b.getNumberOfGuests())
                .build();
    }
}
