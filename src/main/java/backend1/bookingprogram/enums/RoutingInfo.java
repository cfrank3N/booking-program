package backend1.bookingprogram.enums;

public enum RoutingInfo {

    REGISTER_GUEST("/guest/register", "register-guest"),
    HOMEPAGE("/home", "index"),
    SELECT_ROOM("/rooms", "available-rooms"),
    ALTER_GUEST("/guest/alter/{id}", "alter-guest"),
    SHOW_ROOMS("/rooms/all", "show-rooms"),
    BOOKING_CONFIRMATION("/rooms/select/guest/confirmation", "booking-confirmation"),
    CHOOSE_BOOKING_TO_ALTER("booking/delete/{id}","choose-booking-to-alter"),
    CHOOSE_GUEST_TO_ALTER("/guest/delete/{id}", "choose-guest-to-alter"),
    ALTER_BOOKING("/booking/alter/{id}", "alter-booking"),
    SELECT_GUEST("/rooms/select/guest", "select-guest"),
    ACTIVE_BOOKINGS("guest/bookings/{id}", "active-bookings");

    private final String uri;
    private final String viewName;

    RoutingInfo(String uri, String viewName) {
        this.uri = uri;
        this.viewName = viewName;
    }

    public String getUri() {
        return uri;
    }

    public String getViewName() {
        return viewName;
    }
}
